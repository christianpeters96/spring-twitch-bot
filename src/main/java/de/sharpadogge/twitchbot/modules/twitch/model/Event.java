package de.sharpadogge.twitchbot.modules.twitch.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Event {

    private String type;
    
    private Long fromId;

    private String from;

    private LocalDateTime dateTime;

    public Event() {
    }

    public Event(String type, Long fromId, String from, LocalDateTime dateTime) {
        this.type = type;
        this.fromId = fromId;
        this.from = from;
        this.dateTime = dateTime;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getFromId() {
        return this.fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Event type(String type) {
        this.type = type;
        return this;
    }

    public Event fromId(Long fromId) {
        this.fromId = fromId;
        return this;
    }

    public Event from(String from) {
        this.from = from;
        return this;
    }

    public Event dateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Event)) {
            return false;
        }
        Event event = (Event) o;
        return Objects.equals(type, event.type) && Objects.equals(fromId, event.fromId) && Objects.equals(from, event.from) && Objects.equals(dateTime, event.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, fromId, from, dateTime);
    }

    @Override
    public String toString() {
        return "{" +
            " type='" + getType() + "'" +
            ", fromId='" + getFromId() + "'" +
            ", from='" + getFrom() + "'" +
            ", dateTime='" + getDateTime() + "'" +
            "}";
    }
}