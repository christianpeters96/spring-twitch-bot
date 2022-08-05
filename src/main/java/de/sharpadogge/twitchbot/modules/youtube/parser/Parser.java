package de.sharpadogge.twitchbot.modules.youtube.parser;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import de.sharpadogge.twitchbot.modules.youtube.YoutubeException;
import de.sharpadogge.twitchbot.modules.youtube.cipher.CipherFactory;
import de.sharpadogge.twitchbot.modules.youtube.extractor.Extractor;
import de.sharpadogge.twitchbot.modules.youtube.model.VideoDetails;
import de.sharpadogge.twitchbot.modules.youtube.model.formats.Format;
import de.sharpadogge.twitchbot.modules.youtube.model.playlist.PlaylistDetails;
import de.sharpadogge.twitchbot.modules.youtube.model.playlist.PlaylistVideoDetails;
import de.sharpadogge.twitchbot.modules.youtube.model.subtitles.SubtitlesInfo;

public interface Parser {
    
    Extractor getExtractor();

    CipherFactory getCipherFactory();

    /* Video */

    JsonNode getPlayerConfig(String htmlUrl) throws YoutubeException;

    String getClientVersion(JsonNode config);

    VideoDetails getVideoDetails(JsonNode config);

    String getJsUrl(JsonNode config) throws YoutubeException;

    List<SubtitlesInfo> getSubtitlesInfoFromCaptions(JsonNode config);

    List<SubtitlesInfo> getSubtitlesInfo(String videoId) throws YoutubeException;

    List<Format> parseFormats(JsonNode json) throws YoutubeException;

    /* Playlist */

    JsonNode getInitialData(String htmlUrl) throws YoutubeException;

    PlaylistDetails getPlaylistDetails(String playlistId, JsonNode initialData);

    List<PlaylistVideoDetails> getPlaylistVideos(JsonNode initialData, int videoCount) throws YoutubeException;

    String getChannelUploadsPlaylistId(String channelId) throws YoutubeException;
}
