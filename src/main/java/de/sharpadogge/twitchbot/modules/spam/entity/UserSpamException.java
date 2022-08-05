package de.sharpadogge.twitchbot.modules.spam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_spam_exceptions")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSpamException {

    @Id
    @Column(name = "user_id")
    @JsonIgnore
    private Long userId;

    @Column(name = "filter")
    private String filter;

    @Column(name = "exception")
    private String exception;

    public UserSpamException() {
    }

    public UserSpamException(Long userId, String filter, String exception) {
        this.userId = userId;
        this.filter = filter;
        this.exception = exception;
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

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
