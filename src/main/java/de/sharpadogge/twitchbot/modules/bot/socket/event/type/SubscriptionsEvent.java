package de.sharpadogge.twitchbot.modules.bot.socket.event.type;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionsEvent {

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("channel_name")
    private String channelName;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("channel_id")
    private String channelId;

    @JsonProperty("time")
    public String time;

    @JsonProperty("sub_plan")
    public String subPlan;

    @JsonProperty("sub_plan_name")
    public String subPlanName;

    @JsonProperty("cumulative_months")
    public Integer cumulativeMonths;

    @JsonProperty("streak_months")
    public Integer streakMonths;

    @JsonProperty("context")
    public String context;

    @JsonProperty("is_gift")
    public Boolean isGift;

    @JsonProperty("sub_message")
    public Map<String, Object> subMessage;

    public SubscriptionsEvent(String userName, String displayName, String channelName, String userId, String channelId, String time, String subPlan, String subPlanName, Integer cumulativeMonths, Integer streakMonths, String context, Boolean isGift, Map<String,Object> subMessage) {
        this.userName = userName;
        this.displayName = displayName;
        this.channelName = channelName;
        this.userId = userId;
        this.channelId = channelId;
        this.time = time;
        this.subPlan = subPlan;
        this.subPlanName = subPlanName;
        this.cumulativeMonths = cumulativeMonths;
        this.streakMonths = streakMonths;
        this.context = context;
        this.isGift = isGift;
        this.subMessage = subMessage;
    }

    public SubscriptionsEvent() {
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getChannelName() {
        return this.channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubPlan() {
        return this.subPlan;
    }

    public void setSubPlan(String subPlan) {
        this.subPlan = subPlan;
    }

    public String getSubPlanName() {
        return this.subPlanName;
    }

    public void setSubPlanName(String subPlanName) {
        this.subPlanName = subPlanName;
    }

    public Integer getCumulativeMonths() {
        return this.cumulativeMonths;
    }

    public void setCumulativeMonths(Integer cumulativeMonths) {
        this.cumulativeMonths = cumulativeMonths;
    }

    public Integer getStreakMonths() {
        return this.streakMonths;
    }

    public void setStreakMonths(Integer streakMonths) {
        this.streakMonths = streakMonths;
    }

    public String getContext() {
        return this.context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Boolean isIsGift() {
        return this.isGift;
    }

    public Boolean getIsGift() {
        return this.isGift;
    }

    public void setIsGift(Boolean isGift) {
        this.isGift = isGift;
    }

    public Map<String,Object> getSubMessage() {
        return this.subMessage;
    }

    public void setSubMessage(Map<String,Object> subMessage) {
        this.subMessage = subMessage;
    }

    @Override
    public String toString() {
        return "{" +
            " userName='" + getUserName() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", channelName='" + getChannelName() + "'" +
            ", userId='" + getUserId() + "'" +
            ", channelId='" + getChannelId() + "'" +
            ", time='" + getTime() + "'" +
            ", subPlan='" + getSubPlan() + "'" +
            ", subPlanName='" + getSubPlanName() + "'" +
            ", cumulativeMonths='" + getCumulativeMonths() + "'" +
            ", streakMonths='" + getStreakMonths() + "'" +
            ", context='" + getContext() + "'" +
            ", isGift='" + isIsGift() + "'" +
            ", subMessage='" + getSubMessage() + "'" +
            "}";
    }
}
