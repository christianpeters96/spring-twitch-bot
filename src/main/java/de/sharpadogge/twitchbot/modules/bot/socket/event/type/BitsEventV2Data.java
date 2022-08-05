package de.sharpadogge.twitchbot.modules.bot.socket.event.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitsEventV2Data {

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("channel_name")
    private String channelName;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("channel_id")
    private String channelId;

    @JsonProperty("time")
    private String time;

    @JsonProperty("chat_message")
    private String chatMessage;

    @JsonProperty("bits_used")
    private Integer bitsUsed;

    @JsonProperty("total_bits_used")
    private Integer totalBitsUsed;

    @JsonProperty("context")
    private String context;

    @JsonProperty("badge_entitlement")
    private Object badgeEntitlement;


    public BitsEventV2Data() {
    }

    public BitsEventV2Data(String userName, String channelName, String userId, String channelId, String time, String chatMessage, Integer bitsUsed, Integer totalBitsUsed, String context, Object badgeEntitlement) {
        this.userName = userName;
        this.channelName = channelName;
        this.userId = userId;
        this.channelId = channelId;
        this.time = time;
        this.chatMessage = chatMessage;
        this.bitsUsed = bitsUsed;
        this.totalBitsUsed = totalBitsUsed;
        this.context = context;
        this.badgeEntitlement = badgeEntitlement;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getChatMessage() {
        return this.chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public Integer getBitsUsed() {
        return this.bitsUsed;
    }

    public void setBitsUsed(Integer bitsUsed) {
        this.bitsUsed = bitsUsed;
    }

    public Integer getTotalBitsUsed() {
        return this.totalBitsUsed;
    }

    public void setTotalBitsUsed(Integer totalBitsUsed) {
        this.totalBitsUsed = totalBitsUsed;
    }

    public String getContext() {
        return this.context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Object getBadgeEntitlement() {
        return this.badgeEntitlement;
    }

    public void setBadgeEntitlement(Object badgeEntitlement) {
        this.badgeEntitlement = badgeEntitlement;
    }

    @Override
    public String toString() {
        return "{" +
            " userName='" + getUserName() + "'" +
            ", channelName='" + getChannelName() + "'" +
            ", userId='" + getUserId() + "'" +
            ", channelId='" + getChannelId() + "'" +
            ", time='" + getTime() + "'" +
            ", chatMessage='" + getChatMessage() + "'" +
            ", bitsUsed='" + getBitsUsed() + "'" +
            ", totalBitsUsed='" + getTotalBitsUsed() + "'" +
            ", context='" + getContext() + "'" +
            ", badgeEntitlement='" + getBadgeEntitlement() + "'" +
            "}";
    }
}
