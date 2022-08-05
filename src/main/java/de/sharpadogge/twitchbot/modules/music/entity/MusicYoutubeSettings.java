package de.sharpadogge.twitchbot.modules.music.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.sharpadogge.twitchbot.modules.user.User;

@Entity
@Table(name = "music_youtube_settings")
public class MusicYoutubeSettings {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "max_video_length")
    private Integer maxVideoLength;

    @Transient
    @JsonIgnore
    private User user;

    public MusicYoutubeSettings(Long id, Long userId, Integer maxVideoLength) {
        this.id = id;
        this.userId = userId;
        this.maxVideoLength = maxVideoLength;
    }

    public MusicYoutubeSettings() {
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

    public Integer getMaxVideoLength() {
        return this.maxVideoLength;
    }

    public void setMaxVideoLength(Integer maxVideoLength) {
        this.maxVideoLength = maxVideoLength;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", userId='" + getUserId() + "'" +
            ", maxVideoLength='" + getMaxVideoLength() + "'" +
            ", user='" + getUser() + "'" +
            "}";
    }
}
