package de.sharpadogge.twitchbot.modules.spam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_spam_filters")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSpamFilter {

    @Id
    @Column(name = "user_id")
    @JsonIgnore
    private Long userId;

    @Column(name = "filter")
    private String filter;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "silent")
    private Boolean silent;

    @Column(name = "timeout_duration")
    @JsonProperty("timeout_duration")
    private Integer timeoutDuration;

    @Column(name = "allowed_limit")

    @JsonProperty("allowed_limit")
    private Integer allowedLimit;

    @Column(name = "message")
    private String message;

    public UserSpamFilter() {
    }

    public UserSpamFilter(Long userId, String filter, Boolean active, Boolean silent, Integer timeoutDuration, Integer allowedLimit, String message) {
        this.userId = userId;
        this.filter = filter;
        this.active = active;
        this.silent = silent;
        this.timeoutDuration = timeoutDuration;
        this.allowedLimit = allowedLimit;
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getSilent() {
        return silent;
    }

    public void setSilent(Boolean silent) {
        this.silent = silent;
    }

    public Integer getTimeoutDuration() {
        return timeoutDuration;
    }

    public void setTimeoutDuration(Integer timeoutDuration) {
        this.timeoutDuration = timeoutDuration;
    }

    public Integer getAllowedLimit() {
        return allowedLimit;
    }

    public void setAllowedLimit(Integer allowedLimit) {
        this.allowedLimit = allowedLimit;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UserSpamFilter{" +
                "userId=" + userId +
                ", filter='" + filter + '\'' +
                ", active=" + active +
                ", silent=" + silent +
                ", timeoutDuration=" + timeoutDuration +
                ", allowedLimit=" + allowedLimit +
                ", message='" + message + '\'' +
                '}';
    }
}
