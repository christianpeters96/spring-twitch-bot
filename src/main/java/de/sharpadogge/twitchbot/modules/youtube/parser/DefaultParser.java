package de.sharpadogge.twitchbot.modules.youtube.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import de.sharpadogge.twitchbot.modules.utils.JsonUtils;
import de.sharpadogge.twitchbot.modules.youtube.YoutubeException;
import de.sharpadogge.twitchbot.modules.youtube.cipher.CachedCipherFactory;
import de.sharpadogge.twitchbot.modules.youtube.cipher.Cipher;
import de.sharpadogge.twitchbot.modules.youtube.cipher.CipherFactory;
import de.sharpadogge.twitchbot.modules.youtube.extractor.DefaultExtractor;
import de.sharpadogge.twitchbot.modules.youtube.extractor.Extractor;
import de.sharpadogge.twitchbot.modules.youtube.model.Itag;
import de.sharpadogge.twitchbot.modules.youtube.model.Utils;
import de.sharpadogge.twitchbot.modules.youtube.model.VideoDetails;
import de.sharpadogge.twitchbot.modules.youtube.model.formats.AudioFormat;
import de.sharpadogge.twitchbot.modules.youtube.model.formats.AudioVideoFormat;
import de.sharpadogge.twitchbot.modules.youtube.model.formats.Format;
import de.sharpadogge.twitchbot.modules.youtube.model.formats.VideoFormat;
import de.sharpadogge.twitchbot.modules.youtube.model.playlist.PlaylistDetails;
import de.sharpadogge.twitchbot.modules.youtube.model.playlist.PlaylistVideoDetails;
import de.sharpadogge.twitchbot.modules.youtube.model.subtitles.SubtitlesInfo;

public class DefaultParser implements Parser {
    
    private static final Pattern subtitleLangCodeRegex = Pattern.compile("lang_code=\"(.{2,3})\"");
    private static final Pattern textNumberRegex = Pattern.compile("[0-9, ']+");
    private static final Pattern assetsJsRegex = Pattern.compile("\"assets\":.+?\"js\":\\s*\"([^\"]+)\"");
    private static final Pattern embJsRegex = Pattern.compile("\"jsUrl\":\\s*\"([^\"]+)\"");

    private Extractor extractor;
    private CipherFactory cipherFactory;

    public DefaultParser() {
        this.extractor = new DefaultExtractor();
        this.cipherFactory = new CachedCipherFactory(extractor);
    }

    @Override
    public Extractor getExtractor() {
        return extractor;
    }

    @Override
    public CipherFactory getCipherFactory() {
        return cipherFactory;
    }

    @Override
    public JsonNode getPlayerConfig(String htmlUrl) throws YoutubeException {
        String html = extractor.loadUrl(htmlUrl);

        String ytPlayerConfig = extractor.extractYtPlayerConfig(html);
        try {
            return new ObjectMapper().readTree(ytPlayerConfig);
        } catch (Exception e) {
            throw new YoutubeException.BadPageException("Could not parse player config json");
        }
    }

    @Override
    public String getClientVersion(JsonNode config) {
        return getClientVersionFromContext(config.get("responseContext"));
    }

    @Override
    public String getJsUrl(JsonNode config) throws YoutubeException {
        String js = null;
        if (config.has("assets")) {
            js = config.get("assets").get("js").textValue();
        } else {
            // if assets not found - download embed webpage and search there
            String videoId = config.get("yt-downloader-videoId").textValue();
            String html = extractor.loadUrl("https://www.youtube.com/embed/" + videoId);
            Matcher matcher = assetsJsRegex.matcher(html);
            if (matcher.find()) {
                js = matcher.group(1).replace("\\", "");
            } else {
                matcher = embJsRegex.matcher(html);
                if (matcher.find()) {
                    js = matcher.group(1).replace("\\", "");
                }
            }
        }
        if (js == null) {
            throw new YoutubeException.BadPageException("Could not extract js url: assets not found");
        }
        return "https://youtube.com" + js;
    }

    @Override
    public VideoDetails getVideoDetails(JsonNode config) {
        if (config.has("videoDetails")) {
            JsonNode videoDetails = config.get("videoDetails");
            String liveHLSUrl = null;
            JsonNode isLive = JsonUtils.get(videoDetails, "isLive");
            if (isLive != null && isLive.booleanValue()) {
            //if (videoDetails.get("isLive").booleanValue()) { //TODO
                if (config.has("streamingData")) {
                    liveHLSUrl = config.get("streamingData").get("hlsManifestUrl").textValue();
                }
            }
            return new VideoDetails(videoDetails, liveHLSUrl);
        }

        return new VideoDetails();
    }

    @Override
    public List<SubtitlesInfo> getSubtitlesInfoFromCaptions(JsonNode config) {
        if (!config.has("captions")) {
            return Collections.emptyList();
        }
        JsonNode captions = config.get("captions");

        JsonNode playerCaptionsTracklistRenderer = captions.get("playerCaptionsTracklistRenderer");
        if (playerCaptionsTracklistRenderer == null || playerCaptionsTracklistRenderer.isEmpty()) {
            return Collections.emptyList();
        }

        JsonNode captionsArray = playerCaptionsTracklistRenderer.get("captionTracks");
        if (captionsArray == null || captionsArray.isEmpty()) {
            return Collections.emptyList();
        }

        List<SubtitlesInfo> subtitlesInfo = new ArrayList<>();
        for (int i = 0; i < captionsArray.size(); i++) {
            JsonNode subtitleInfo = captionsArray.get(i);
            String language = subtitleInfo.get("languageCode").textValue();
            String url = subtitleInfo.get("baseUrl").textValue();
            String vssId = subtitleInfo.get("vssId").textValue();

            if (language != null && url != null && vssId != null) {
                boolean isAutoGenerated = vssId.startsWith("a.");
                subtitlesInfo.add(new SubtitlesInfo(url, language, isAutoGenerated));
            }
        }
        return subtitlesInfo;
    }

    @Override
    public List<SubtitlesInfo> getSubtitlesInfo(String videoId) throws YoutubeException {
        String xmlUrl = "https://video.google.com/timedtext?hl=en&type=list&v=" + videoId;

        String subtitlesXml = extractor.loadUrl(xmlUrl);

        Matcher matcher = subtitleLangCodeRegex.matcher(subtitlesXml);

        if (!matcher.find()) {
            return Collections.emptyList();
        }

        List<SubtitlesInfo> subtitlesInfo = new ArrayList<>();
        do {
            String language = matcher.group(1);
            String url = String.format("https://www.youtube.com/api/timedtext?lang=%s&v=%s",
                    language, videoId);
            subtitlesInfo.add(new SubtitlesInfo(url, language, false));
        } while (matcher.find());

        return subtitlesInfo;
    }

    @Override
    public List<Format> parseFormats(JsonNode config) throws YoutubeException {
        if (!config.has("streamingData")) {
            throw new YoutubeException.BadPageException("Streaming data not found");
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode streamingData = config.get("streamingData");
        ArrayNode jsonFormats = mapper.createArrayNode();
        if (streamingData.has("formats")) {
            jsonFormats.addAll((ArrayNode) streamingData.get("formats"));
        }
        ArrayNode jsonAdaptiveFormats = mapper.createArrayNode();
        if (streamingData.has("adaptiveFormats")) {
            jsonAdaptiveFormats.addAll((ArrayNode) streamingData.get("adaptiveFormats"));
        }
        String jsUrl = getJsUrl(config);

        List<Format> formats = new ArrayList<>(jsonFormats.size() + jsonAdaptiveFormats.size());
        populateFormats(formats, jsonFormats, jsUrl, false);
        populateFormats(formats, jsonAdaptiveFormats, jsUrl, true);
        return formats;
    }

    @Override
    public JsonNode getInitialData(String htmlUrl) throws YoutubeException {
        String html = extractor.loadUrl(htmlUrl);

        String ytInitialData = extractor.extractYtInitialData(html);
        try {
            return new ObjectMapper().readTree(ytInitialData);
        } catch (Exception e) {
            throw new YoutubeException.BadPageException("Could not parse initial data json");
        }
    }

    @Override
    public PlaylistDetails getPlaylistDetails(String playlistId, JsonNode initialData) {
        String title = initialData.get("metadata")
            .get("playlistMetadataRenderer")
            .get("title").textValue();
        
        JsonNode sideBarItems = initialData.get("sidebar").get("playlistSidebarRenderer").get("items");

        String author = null;
        try {
            // try to retrieve author, some playlists may have no author
            author = sideBarItems.get(1)
                    .get("playlistSidebarSecondaryInfoRenderer")
                    .get("videoOwner")
                    .get("videoOwnerRenderer")
                    .get("title")
                    .get("runs")
                    .get(0)
                    .get("text").textValue();

        } catch (Exception ignored) { }

        JsonNode stats = sideBarItems.get(0)
                .get("playlistSidebarPrimaryInfoRenderer")
                .get("stats");

        int videoCount = extractNumber(stats.get(0).get("runs").get(0).get("text").textValue());
        int viewCount = extractNumber(stats.get(1).get("simpleText").textValue());

        return new PlaylistDetails(playlistId, title, author, videoCount, viewCount);
    }

    @Override
    public List<PlaylistVideoDetails> getPlaylistVideos(JsonNode initialData, int videoCount) throws YoutubeException {
        JsonNode content;

        try {
            content = initialData.get("contents")
                    .get("twoColumnBrowseResultsRenderer")
                    .get("tabs").get(0)
                    .get("tabRenderer")
                    .get("content")
                    .get("sectionListRenderer")
                    .get("contents").get(0)
                    .get("itemSectionRenderer")
                    .get("contents").get(0)
                    .get("playlistVideoListRenderer");
        } catch (NullPointerException e) {
            throw new YoutubeException.BadPageException("Playlist initial data not found");
        }

        List<PlaylistVideoDetails> videos;
        if (videoCount > 0) {
            videos = new ArrayList<>(videoCount);
        } else {
            videos = new LinkedList<>();
        }
        populatePlaylist(content, videos, getClientVersionFromContext(initialData.get("responseContext")));
        return videos;
    }

    @Override
    public String getChannelUploadsPlaylistId(String channelId) throws YoutubeException {
        URL channelLink;
        try {
            if (channelId.length() == 24 && channelId.startsWith("UC")) {
                channelLink = new URL("https://www.youtube.com/channel/" + channelId + "/videos?view=57");
            } else {
                channelLink = new URL("https://www.youtube.com/c/" + channelId + "/videos?view=57");
            }
        } catch (MalformedURLException e) {
            throw new YoutubeException.BadPageException("Upload Playlist not found");
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(channelLink.openStream()));
            String line;
            while ((line = br.readLine()) != null) {
                Scanner scan = new Scanner(line);
                scan.useDelimiter("list=");
                while (scan.hasNext()) {
                    String pId = scan.next();
                    if (pId.startsWith("UU")) {
                        scan.close();
                        return pId.substring(0, 24);
                    }
                }
                scan.close();
            }
        } catch (IOException e) {
            throw new YoutubeException.BadPageException(String.format("Could not load url: %s, exception: %s", channelLink, e.getMessage()));
        } finally {
            Utils.closeSilently(br);
        }
        throw new YoutubeException.BadPageException("Upload Playlist not found");
    }

    private void populateFormats(List<Format> formats, JsonNode jsonFormats, String jsUrl, boolean isAdaptive) throws YoutubeException.CipherException {
        for (int i = 0; i < jsonFormats.size(); i++) {
            JsonNode json = jsonFormats.get(i);

            String formatType = JsonUtils.getString(json, "type").orElse(null);
            if (formatType != null && "FORMAT_STREAM_TYPE_OTF".equals(formatType))
                continue; // unsupported otf formats which cause 404 not found
            try {
                Format format = parseFormat(json, jsUrl, isAdaptive);
                formats.add(format);
            } catch (YoutubeException.CipherException e) {
                throw e;
            } catch (YoutubeException e) {
                System.err.println("Error parsing format: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Format parseFormat(JsonNode json, String jsUrl, boolean isAdaptive) throws YoutubeException {
        if (json.has("signatureCipher")) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode jsonCipher = mapper.createObjectNode();
            String[] cipherData = json.get("signatureCipher").textValue().replace("\\u0026", "&").split("&");
            for (String s : cipherData) {
                String[] keyValue = s.split("=");
                jsonCipher.put(keyValue[0], keyValue[1]);
            }
            if (!jsonCipher.has("url")) {
                throw new YoutubeException.BadPageException("Could not found url in cipher data");
            }
            String urlWithSig = jsonCipher.get("url").textValue();
            try {
                urlWithSig = URLDecoder.decode(urlWithSig, StandardCharsets.UTF_8.name());
            }
            catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            if (urlWithSig.contains("signature")
                    || (!jsonCipher.has("s") && (urlWithSig.contains("&sig=") || urlWithSig.contains("&lsig=")))) {
                // do nothing, this is pre-signed videos with signature
            } else {
                String s = jsonCipher.get("s").textValue();
                try {
                    s = URLDecoder.decode(s, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Cipher cipher = cipherFactory.createCipher(jsUrl);

                String signature = cipher.getSignature(s);
                String decipheredUrl = urlWithSig + "&sig=" + signature;
                ((ObjectNode) json).put("url", decipheredUrl);
            }
        }

        Itag itag;
        try {
            itag = Itag.valueOf("i" + json.get("itag").intValue());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            itag = Itag.unknown;
            itag.setId(json.get("itag").intValue());
        }

        boolean hasVideo = itag.isVideo() || json.has("size") || json.has("width");
        boolean hasAudio = itag.isAudio() || json.has("audioQuality");

        if (hasVideo && hasAudio)
            return new AudioVideoFormat(json, isAdaptive);
        else if (hasVideo)
            return new VideoFormat(json, isAdaptive);
        else if (hasAudio)
            return new AudioFormat(json, isAdaptive);

        throw new YoutubeException.UnknownFormatException("unknown format with itag " + itag.id());
    }

    private void populatePlaylist(JsonNode content, List<PlaylistVideoDetails> videos, String clientVersion) throws YoutubeException {
        JsonNode contents = content.get("contents");
        for (int i = 0; i < contents.size(); i++) {
            videos.add(new PlaylistVideoDetails(contents.get(i).get("playlistVideoRenderer")));
        }
        if (content.has("continuations")) {
            String continuation = content.get("continuations")
                    .get(0)
                    .get("nextContinuationData")
                    .get("continuation").textValue();
            loadPlaylistContinuation(continuation, videos, clientVersion);
        }
    }

    private void loadPlaylistContinuation(String continuation, List<PlaylistVideoDetails> videos, String clientVersion) throws YoutubeException {
        JsonNode content;

        String url = "https://www.youtube.com/browse_ajax?ctoken=" + continuation
                + "&continuation=" + continuation;

        getExtractor().setRequestProperty("X-YouTube-Client-Name", "1");
        getExtractor().setRequestProperty("X-YouTube-Client-Version", clientVersion);
        String html = getExtractor().loadUrl(url);

        try {
            JsonNode response = new ObjectMapper().readTree(html);
            content = response.get(1)
                    .get("response")
                    .get("continuationContents")
                    .get("playlistVideoListContinuation");
            populatePlaylist(content, videos, clientVersion);
        } catch (YoutubeException e) {
            throw e;
        } catch (Exception e) {
            throw new YoutubeException.BadPageException("Could not parse playlist continuation json");
        }
    }

    private String getClientVersionFromContext(JsonNode context) {
        JsonNode trackingParams = context.get("serviceTrackingParams");
        if (trackingParams == null) {
            return "2.20200720.00.02";
        }
        for (int ti = 0; ti < trackingParams.size(); ti++) {
            JsonNode params = trackingParams.get(ti).get("params");
            for (int pi = 0; pi < params.size(); pi++) {
                if (params.get(pi).get("key").textValue().equals("cver")) {
                    return params.get(pi).get("value").textValue();
                }
            }
        }
        return null;
    }

    private static int extractNumber(String text) {
        Matcher matcher = textNumberRegex.matcher(text);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(0).replaceAll("[, ']", ""));
            }
            catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }
}
