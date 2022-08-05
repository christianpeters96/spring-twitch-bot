package de.sharpadogge.twitchbot.modules.bot.data.entity;

import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "twitch_user_stats_overall")
public class UserStatsOverall {

    @Id
    @Column(name = "id")
    private Long id;

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

    public UserStatsOverall() {
    }

    public UserStatsOverall(Long id, String channel, String username, Long messageCount, Long commandCount, Long timeoutCount, Long timeoutMaxtime, Long deletedMessageCount, Long activityTime, Float activityPoints) {
        this.id = id;
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserStatsOverall id(Long id) {
        this.id = id;
        return this;
    }

    public UserStatsOverall channel(String channel) {
        this.channel = channel;
        return this;
    }

    public UserStatsOverall username(String username) {
        this.username = username;
        return this;
    }

    public UserStatsOverall messageCount(Long messageCount) {
        this.messageCount = messageCount;
        return this;
    }

    public UserStatsOverall commandCount(Long commandCount) {
        this.commandCount = commandCount;
        return this;
    }

    public UserStatsOverall timeoutCount(Long timeoutCount) {
        this.timeoutCount = timeoutCount;
        return this;
    }

    public UserStatsOverall timeoutMaxtime(Long timeoutMaxtime) {
        this.timeoutMaxtime = timeoutMaxtime;
        return this;
    }

    public UserStatsOverall deletedMessageCount(Long deletedMessageCount) {
        this.deletedMessageCount = deletedMessageCount;
        return this;
    }

    public UserStatsOverall activityTime(Long activityTime) {
        this.activityTime = activityTime;
        return this;
    }

    public UserStatsOverall activityPoints(Float activityPoints) {
        this.activityPoints = activityPoints;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserStatsOverall)) {
            return false;
        }
        UserStatsOverall userStatsOverall = (UserStatsOverall) o;
        return Objects.equals(id, userStatsOverall.id) && Objects.equals(channel, userStatsOverall.channel) && Objects.equals(username, userStatsOverall.username) && Objects.equals(messageCount, userStatsOverall.messageCount) && Objects.equals(commandCount, userStatsOverall.commandCount) && Objects.equals(timeoutCount, userStatsOverall.timeoutCount) && Objects.equals(timeoutMaxtime, userStatsOverall.timeoutMaxtime) && Objects.equals(deletedMessageCount, userStatsOverall.deletedMessageCount) && Objects.equals(activityTime, userStatsOverall.activityTime) && Objects.equals(activityPoints, userStatsOverall.activityPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, channel, username, messageCount, commandCount, timeoutCount, timeoutMaxtime, deletedMessageCount, activityTime, activityPoints);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
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