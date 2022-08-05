package de.sharpadogge.twitchbot.modules.bot.socket.event.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitsEventV2 {
    
    @JsonProperty("version")
    private String version;
    
    @JsonProperty("message_type")
    private String messageType;
    
    @JsonProperty("message_id")
    private String messageId;
    
    @JsonProperty("is_anonymous")
    private Boolean isAnonymous;

    @JsonProperty("data")
    private BitsEventV2Data data;

    public BitsEventV2() {
    }

    public BitsEventV2(String version, String messageType, String messageId, Boolean isAnonymous, BitsEventV2Data data) {
        this.version = version;
        this.messageType = messageType;
        this.messageId = messageId;
        this.isAnonymous = isAnonymous;
        this.data = data;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMessageType() {
        return this.messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Boolean isIsAnonymous() {
        return this.isAnonymous;
    }

    public Boolean getIsAnonymous() {
        return this.isAnonymous;
    }

    public void setIsAnonymous(Boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public BitsEventV2Data getData() {
        return this.data;
    }

    public void setData(BitsEventV2Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
            " version='" + getVersion() + "'" +
            ", messageType='" + getMessageType() + "'" +
            ", messageId='" + getMessageId() + "'" +
            ", isAnonymous='" + isIsAnonymous() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}
