package de.sharpadogge.twitchbot.modules.youtube.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import de.sharpadogge.twitchbot.modules.youtube.YoutubeException.DownloadUnavailableException;

public abstract class AbstractVideoDetails {
    
    private String videoId;
    private int lengthSeconds;
    private List<String> thumbnails;

    // Subclass specific extraction
    protected String title;
    protected String author;
    protected boolean isLive;

    protected abstract void checkDownload() throws DownloadUnavailableException;

    public AbstractVideoDetails() {
    }

    public AbstractVideoDetails(JsonNode json) {
        videoId = json.get("videoId").textValue();
        lengthSeconds = Integer.parseInt(json.get("lengthSeconds").textValue());
        JsonNode jsonThumbnails = json.get("thumbnail").get("thumbnails");
        thumbnails = new ArrayList<>(jsonThumbnails.size());
        for (int i = 0; i < jsonThumbnails.size(); i++) {
            JsonNode jsonObject = jsonThumbnails.get(i);
            if (jsonObject.has("url"))
                thumbnails.add(jsonObject.get("url").textValue());
        }
    }

    public String videoId() {
        return videoId;
    }

    public String title() {
        return title;
    }

    public int lengthSeconds() {
        return lengthSeconds;
    }

    public List<String> thumbnails() {
        return thumbnails;
    }

    public String author() {
        return author;
    }

    public boolean isLive() {
        return isLive;
    }
}
