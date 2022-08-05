package de.sharpadogge.twitchbot.modules.music.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.sharpadogge.twitchbot.modules.user.User;

@Entity
@Table(name = "music_settings")
public class MusicSettings {
    
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "provider")
    private String provider;

    @Transient
    @JsonIgnore
    private User user;

    public MusicSettings(Long id, Long userId, String provider) {
        this.id = id;
        this.userId = userId;
        this.provider = provider;
    }

    public MusicSettings() {
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

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProvider() {
        return this.provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", userId='" + getUserId() + "'" +
            ", user='" + getUser() + "'" +
            ", provider='" + getProvider() + "'" +
            "}";
    }

}
