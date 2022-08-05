package de.sharpadogge.twitchbot.modules.youtube;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import de.sharpadogge.twitchbot.modules.youtube.cipher.CipherFunction;
import de.sharpadogge.twitchbot.modules.youtube.model.VideoDetails;
import de.sharpadogge.twitchbot.modules.youtube.model.YoutubeVideo;
import de.sharpadogge.twitchbot.modules.youtube.model.formats.Format;
import de.sharpadogge.twitchbot.modules.youtube.model.playlist.PlaylistDetails;
import de.sharpadogge.twitchbot.modules.youtube.model.playlist.PlaylistVideoDetails;
import de.sharpadogge.twitchbot.modules.youtube.model.playlist.YoutubePlaylist;
import de.sharpadogge.twitchbot.modules.youtube.model.subtitles.SubtitlesInfo;
import de.sharpadogge.twitchbot.modules.youtube.parser.DefaultParser;
import de.sharpadogge.twitchbot.modules.youtube.parser.Parser;

public class YoutubeDownloader {
    
    //private final String jsUrl = "https://www.youtube.com/s/player/d29f3109/player_ias.vflset/en_US/base.js";

    private Parser parser;

    public YoutubeDownloader() {
        this.parser = new DefaultParser();
    }

    public YoutubeDownloader(Parser parser) {
        this.parser = parser;
    }

    public void setParserRequestProperty(String key, String value) {
        parser.getExtractor().setRequestProperty(key, value);
    }

    public void setParserRetryOnFailure(int retryOnFailure) {
        parser.getExtractor().setRetryOnFailure(retryOnFailure);
    }

    public void addCipherFunctionPattern(int priority, String regex) {
        parser.getCipherFactory().addInitialFunctionPattern(priority, regex);
    }

    public void addCipherFunctionEquivalent(String regex, CipherFunction function) {
        parser.getCipherFactory().addFunctionEquivalent(regex, function);
    }

    public YoutubeVideo getVideo(String videoId) throws YoutubeException {
        String htmlUrl = "https://www.youtube.com/watch?v=" + videoId;

        JsonNode ytPlayerConfig = parser.getPlayerConfig(htmlUrl);
        ((ObjectNode) ytPlayerConfig).put("yt-downloader-videoId", videoId);

        VideoDetails videoDetails = parser.getVideoDetails(ytPlayerConfig);

        List<Format> formats = parser.parseFormats(ytPlayerConfig);

        List<SubtitlesInfo> subtitlesInfo = parser.getSubtitlesInfoFromCaptions(ytPlayerConfig);

        String clientVersion = parser.getClientVersion(ytPlayerConfig);

        return new YoutubeVideo(videoDetails, formats, subtitlesInfo, clientVersion);
    }

    public YoutubePlaylist getPlaylist(String playlistId) throws YoutubeException {
        String htmlUrl = "https://www.youtube.com/playlist?list=" + playlistId;

        JsonNode ytInitialData = parser.getInitialData(htmlUrl);
        if (!ytInitialData.has("metadata")) {
            throw new YoutubeException.BadPageException("Invalid initial data json");
        }

        PlaylistDetails playlistDetails = parser.getPlaylistDetails(playlistId, ytInitialData);

        List<PlaylistVideoDetails> videos = parser.getPlaylistVideos(ytInitialData, playlistDetails.videoCount());

        return new YoutubePlaylist(playlistDetails, videos);
    }

    public YoutubePlaylist getChannelUploads(String channelId) throws YoutubeException {
        String playlistId = parser.getChannelUploadsPlaylistId(channelId);
        return getPlaylist(playlistId);
    }

    public List<SubtitlesInfo> getVideoSubtitles(String videoId) throws YoutubeException {
        return parser.getSubtitlesInfo(videoId);
    }
}
