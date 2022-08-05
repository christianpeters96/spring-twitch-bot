package de.sharpadogge.twitchbot.modules.spotify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultCurrentSong {

    @JsonProperty("timestamp")
    private Long timestamp;

    @JsonProperty("context")
    private Context context;

    @JsonProperty("progress_ms")
    private Integer progressMs;

    @JsonProperty("item")
    private TrackItem item;

    @JsonProperty("currently_playing_type")
    private String currentlyPlayingType;

    @JsonProperty("actions")
    private Actions actions;

    @JsonProperty("is_playing")
    private Boolean isPlaying;

    public ResultCurrentSong() {
    }

    public ResultCurrentSong(Long timestamp, Context context, Integer progressMs, TrackItem item, String currentlyPlayingType, Actions actions, Boolean isPlaying) {
        this.timestamp = timestamp;
        this.context = context;
        this.progressMs = progressMs;
        this.item = item;
        this.currentlyPlayingType = currentlyPlayingType;
        this.actions = actions;
        this.isPlaying = isPlaying;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Integer getProgressMs() {
        return progressMs;
    }

    public void setProgressMs(Integer progressMs) {
        this.progressMs = progressMs;
    }

    public TrackItem getItem() {
        return item;
    }

    public void setItem(TrackItem item) {
        this.item = item;
    }

    public String getCurrentlyPlayingType() {
        return currentlyPlayingType;
    }

    public void setCurrentlyPlayingType(String currentlyPlayingType) {
        this.currentlyPlayingType = currentlyPlayingType;
    }

    public Actions getActions() {
        return actions;
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }

    public Boolean getPlaying() {
        return isPlaying;
    }

    public void setPlaying(Boolean playing) {
        isPlaying = playing;
    }
}
