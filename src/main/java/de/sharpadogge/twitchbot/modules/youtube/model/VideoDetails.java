package de.sharpadogge.twitchbot.modules.youtube.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import de.sharpadogge.twitchbot.modules.utils.JsonUtils;
import de.sharpadogge.twitchbot.modules.youtube.YoutubeException;

public class VideoDetails extends AbstractVideoDetails {
    
    private List<String> keywords;
    private String shortDescription;
    private long viewCount;
    private int averageRating;
    private boolean isLiveContent;
    private String liveUrl;

    public VideoDetails() {
    }

    public VideoDetails(JsonNode json, String liveHLSUrl) {
        super(json);
        title = json.get("title").textValue();
        author = json.get("author").textValue();
        isLive = JsonUtils.getBoolean(json, "isLive").orElse(false);

        keywords = new ArrayList<>();
        if (json.has("keywords")) {
            for (JsonNode v : json.get("keywords")) {
                keywords.add(v.textValue());
            }
        }

        shortDescription = json.get("shortDescription").textValue();
        averageRating = json.get("averageRating").intValue();
        viewCount = json.get("viewCount").longValue();
        isLiveContent = json.get("isLiveContent").booleanValue();
        liveUrl = liveHLSUrl;
    }

    @Override
    protected void checkDownload() throws YoutubeException.LiveVideoException {
        if (isLive || (isLiveContent && lengthSeconds() == 0))
            throw new YoutubeException.LiveVideoException("Can not download live stream");
    }

    public List<String> keywords() {
        return keywords;
    }

    public String description() {
        return shortDescription;
    }

    public long viewCount() {
        return viewCount;
    }

    public int averageRating() {
        return averageRating;
    }

    public boolean isLiveContent() {
        return isLiveContent;
    }

    public String liveUrl() {
        return liveUrl;
    }
}
