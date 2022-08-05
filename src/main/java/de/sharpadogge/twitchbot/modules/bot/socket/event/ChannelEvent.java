package de.sharpadogge.twitchbot.modules.bot.socket.event;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.sharpadogge.twitchbot.modules.user.User;

@Entity
@Table(name = "user_channel_events")
public class ChannelEvent {

    @Id
    @Column(name = "id")
    public Long id;

    @Column(name = "user_id")
    public Long userId;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Transient
    public User user;

    @Column(name = "topic")
    public String topic;

    @Column(name = "json_message")
    public String json;

    public ChannelEvent(Long id, Long userId, LocalDateTime createdAt, String topic, String json) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.topic = topic;
        this.json = json;
    }

    public ChannelEvent() {
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

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
        this.userId = user.getId();
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getJson() {
        return this.json;
    }

    public void setJson(String json) {
        this.json = json;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", userId='" + getUserId() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", user='" + getUser() + "'" +
            ", topic='" + getTopic() + "'" +
            ", json='" + getJson() + "'" +
            "}";
    }
}
