package de.sharpadogge.twitchbot.modules.music.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.sharpadogge.twitchbot.modules.user.User;

@Entity
@Table(name = "music_youtube_playlist")
public class MusicYoutubePlaylist {
    
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

    @Transient
    private User user;

    public MusicYoutubePlaylist(Long id, Long userId, String videoId, Integer videoDurationSec, String videoTitle, String videoAuthor) {
        this.id = id;
        this.userId = userId;
        this.videoId = videoId;
        this.videoDurationSec = videoDurationSec;
        this.videoTitle = videoTitle;
        this.videoAuthor = videoAuthor;
    }

    public MusicYoutubePlaylist() {
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

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", userId='" + getUserId() + "'" +
            ", videoId='" + getVideoId() + "'" +
            ", videoDurationSec='" + getVideoDurationSec() + "'" +
            ", videoTitle='" + getVideoTitle() + "'" +
            ", videoAuthor='" + getVideoAuthor() + "'" +
            "}";
    }

}
