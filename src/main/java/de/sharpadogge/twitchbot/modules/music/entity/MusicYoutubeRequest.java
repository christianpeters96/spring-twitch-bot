package de.sharpadogge.twitchbot.modules.music.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.sharpadogge.twitchbot.modules.user.User;

@Entity
@Table(name = "music_youtube_requests")
public class MusicYoutubeRequest {
    
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "video_id")
    private String videoId;

    @Column(name = "video_duration_sec")
    private Integer videoDurationSec;

    @Column(name = "video_title")
    private String videoTitle;

    @Column(name = "video_author")
    private String videoAuthor;

    @Column(name = "requested_ts")
    private LocalDateTime requestedTs;

    @Column(name = "requested_by")
    private String requestedBy;

    @Column(name = "finished")
    private Boolean finished;

    @Transient
    private User user;

    public MusicYoutubeRequest(Long id, Long userId, String videoId, Integer videoDurationSec, String videoTitle, String videoAuthor, LocalDateTime requestedTs, String requestedBy, Boolean finished) {
        this.id = id;
        this.userId = userId;
        this.videoId = videoId;
        this.videoDurationSec = videoDurationSec;
        this.videoTitle = videoTitle;
        this.videoAuthor = videoAuthor;
        this.requestedTs = requestedTs;
        this.requestedBy = requestedBy;
        this.finished = finished;
    }

    public MusicYoutubeRequest() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Integer getVideoDurationSec() {
        return this.videoDurationSec;
    }

    public void setVideoDurationSec(Integer videoDurationSec) {
        this.videoDurationSec = videoDurationSec;
    }

    public String getVideoTitle() {
        return this.videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoAuthor() {
        return this.videoAuthor;
    }

    public void setVideoAuthor(String videoAuthor) {
        this.videoAuthor = videoAuthor;
    }

    public LocalDateTime getRequestedTs() {
        return this.requestedTs;
    }

    public void setRequestedTs(LocalDateTime requestedTs) {
        this.requestedTs = requestedTs;
    }

    public String getRequestedBy() {
        return this.requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Boolean isFinished() {
        return this.finished;
    }

    public Boolean getFinished() {
        return this.finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", userId='" + getUserId() + "'" +
            ", videoId='" + getVideoId() + "'" +
            ", videoDurationSec='" + getVideoDurationSec() + "'" +
            ", videoTitle='" + getVideoTitle() + "'" +
            ", videoAuthor='" + getVideoAuthor() + "'" +
            ", requestedTs='" + getRequestedTs() + "'" +
            ", requestedBy='" + getRequestedBy() + "'" +
            ", finished='" + isFinished() + "'" +
            "}";
    }

}
