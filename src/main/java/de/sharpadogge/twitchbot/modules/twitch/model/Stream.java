package de.sharpadogge.twitchbot.modules.twitch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stream {

    @JsonProperty("_id")
    private Long id;

    private String game;

    private Integer viewers;

    @JsonProperty("video_height")
    private Integer videoHeight;

    @JsonProperty("average_fps")
    private Integer averageFps;

    private Integer delay;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("is_playlist")
    private Boolean isPlaylist;

    private Preview preview;

    private Channel channel;

    public Stream() {
    }

    public Stream(Long id, String game, Integer viewers, Integer videoHeight, Integer averageFps, Integer delay, LocalDateTime createdAt, Boolean isPlaylist, Preview preview, Channel channel) {
        this.id = id;
        this.game = game;
        this.viewers = viewers;
        this.videoHeight = videoHeight;
        this.averageFps = averageFps;
        this.delay = delay;
        this.createdAt = createdAt;
        this.isPlaylist = isPlaylist;
        this.preview = preview;
        this.channel = channel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public Integer getViewers() {
        return viewers;
    }

    public void setViewers(Integer viewers) {
        this.viewers = viewers;
    }

    public Integer getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(Integer videoHeight) {
        this.videoHeight = videoHeight;
    }

    public Integer getAverageFps() {
        return averageFps;
    }

    public void setAverageFps(Integer averageFps) {
        this.averageFps = averageFps;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getPlaylist() {
        return isPlaylist;
    }

    public void setPlaylist(Boolean playlist) {
        isPlaylist = playlist;
    }

    public Preview getPreview() {
        return preview;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
