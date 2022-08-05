package de.sharpadogge.twitchbot.modules.bot.data.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "twitch_user_stats_daily")
public class UserStatsDaily {

    @Id
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "channel")
    private String channel;

    @Column(name = "username")
    private String username;

    @Column(name = "message_count")
    private Long messageCount = 0L;

    @Column(name = "command_count")
    private Long commandCount = 0L;

    @Column(name = "timeout_count")
    private Long timeoutCount = 0L;

    @Column(name = "timeout_maxtime")
    private Long timeoutMaxtime = 0L;

    @Column(name = "deleted_message_count")
    private Long deletedMessageCount = 0L;

    @Column(name = "activity_time")
    private Long activityTime = 0L;

    @Column(name = "activity_points")
    private Float activityPoints = .0f;

    public UserStatsOverall toOverall() {
        return new UserStatsOverall(0L, channel, username, messageCount, commandCount, timeoutCount, timeoutMaxtime, deletedMessageCount, activityTime, activityPoints);
    }

    public UserStatsDaily() {
    }

    public UserStatsDaily(LocalDate date, String channel, String username, Long messageCount, Long commandCount, Long timeoutCount, Long timeoutMaxtime, Long deletedMessageCount, Long activityTime, Float activityPoints) {
        this.date = date;
        this.channel = channel;
        this.username = username;
        this.messageCount = messageCount;
        this.commandCount = commandCount;
        this.timeoutCount = timeoutCount;
        this.timeoutMaxtime = timeoutMaxtime;
        this.deletedMessageCount = deletedMessageCount;
        this.activityTime = activityTime;
        this.activityPoints = activityPoints;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getMessageCount() {
        return this.messageCount;
    }

    public void setMessageCount(Long messageCount) {
        this.messageCount = messageCount;
    }

    public Long getCommandCount() {
        return this.commandCount;
    }

    public void setCommandCount(Long commandCount) {
        this.commandCount = commandCount;
    }

    public Long getTimeoutCount() {
        return this.timeoutCount;
    }

    public void setTimeoutCount(Long timeoutCount) {
        this.timeoutCount = timeoutCount;
    }

    public Long getTimeoutMaxtime() {
        return this.timeoutMaxtime;
    }

    public void setTimeoutMaxtime(Long timeoutMaxtime) {
        this.timeoutMaxtime = timeoutMaxtime;
    }

    public Long getDeletedMessageCount() {
        return this.deletedMessageCount;
    }

    public void setDeletedMessageCount(Long deletedMessageCount) {
        this.deletedMessageCount = deletedMessageCount;
    }

    public Long getActivityTime() {
        return this.activityTime;
    }

    public void setActivityTime(Long activityTime) {
        this.activityTime = activityTime;
    }

    public Float getActivityPoints() {
        return this.activityPoints;
    }

    public void setActivityPoints(Float activityPoints) {
        this.activityPoints = activityPoints;
    }

    public UserStatsDaily date(LocalDate date) {
        this.date = date;
        return this;
    }

    public UserStatsDaily channel(String channel) {
        this.channel = channel;
        return this;
    }

    public UserStatsDaily username(String username) {
        this.username = username;
        return this;
    }

    public UserStatsDaily messageCount(Long messageCount) {
        this.messageCount = messageCount;
        return this;
    }

    public UserStatsDaily commandCount(Long commandCount) {
        this.commandCount = commandCount;
        return this;
    }

    public UserStatsDaily timeoutCount(Long timeoutCount) {
        this.timeoutCount = timeoutCount;
        return this;
    }

    public UserStatsDaily timeoutMaxtime(Long timeoutMaxtime) {
        this.timeoutMaxtime = timeoutMaxtime;
        return this;
    }

    public UserStatsDaily deletedMessageCount(Long deletedMessageCount) {
        this.deletedMessageCount = deletedMessageCount;
        return this;
    }

    public UserStatsDaily activityTime(Long activityTime) {
        this.activityTime = activityTime;
        return this;
    }

    public UserStatsDaily activityPoints(Float activityPoints) {
        this.activityPoints = activityPoints;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserStatsDaily)) {
            return false;
        }
        UserStatsDaily userStatsDaily = (UserStatsDaily) o;
        return Objects.equals(date, userStatsDaily.date) && Objects.equals(channel, userStatsDaily.channel) && Objects.equals(username, userStatsDaily.username) && Objects.equals(messageCount, userStatsDaily.messageCount) && Objects.equals(commandCount, userStatsDaily.commandCount) && Objects.equals(timeoutCount, userStatsDaily.timeoutCount) && Objects.equals(timeoutMaxtime, userStatsDaily.timeoutMaxtime) && Objects.equals(deletedMessageCount, userStatsDaily.deletedMessageCount) && Objects.equals(activityTime, userStatsDaily.activityTime) && Objects.equals(activityPoints, userStatsDaily.activityPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, channel, username, messageCount, commandCount, timeoutCount, timeoutMaxtime, deletedMessageCount, activityTime, activityPoints);
    }

    @Override
    public String toString() {
        return "{" +
            " date='" + getDate() + "'" +
            ", channel='" + getChannel() + "'" +
            ", username='" + getUsername() + "'" +
            ", messageCount='" + getMessageCount() + "'" +
            ", commandCount='" + getCommandCount() + "'" +
            ", timeoutCount='" + getTimeoutCount() + "'" +
            ", timeoutMaxtime='" + getTimeoutMaxtime() + "'" +
            ", deletedMessageCount='" + getDeletedMessageCount() + "'" +
            ", activityTime='" + getActivityTime() + "'" +
            ", activityPoints='" + getActivityPoints() + "'" +
            "}";
    }
}