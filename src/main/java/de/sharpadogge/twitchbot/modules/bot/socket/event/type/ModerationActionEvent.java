package de.sharpadogge.twitchbot.modules.bot.socket.event.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ModerationActionEvent {
    
    @JsonProperty("type")
    private String type;

    @JsonProperty("moderation_action")
    private String moderationAction;

    @JsonProperty("args")
    private Object args;

    @JsonProperty("created_by")
    private Object createdBy;

    @JsonProperty("created_by_user_id")
    private String createdByUserId;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("msg_id")
    private String messageId;

    @JsonProperty("target_user_id")
    private String targetUserId;

    @JsonProperty("target_user_login")
    private String targetUserLogin;

    @JsonProperty("from_automod")
    private Boolean fromAutomod;


    public ModerationActionEvent(String type, String moderationAction, Object args, Object createdBy, String createdByUserId, String createdAt, String messageId, String targetUserId, String targetUserLogin, Boolean fromAutomod) {
        this.type = type;
        this.moderationAction = moderationAction;
        this.args = args;
        this.createdBy = createdBy;
        this.createdByUserId = createdByUserId;
        this.createdAt = createdAt;
        this.messageId = messageId;
        this.targetUserId = targetUserId;
        this.targetUserLogin = targetUserLogin;
        this.fromAutomod = fromAutomod;
    }

    public ModerationActionEvent() {
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModerationAction() {
        return this.moderationAction;
    }

    public void setModerationAction(String moderationAction) {
        this.moderationAction = moderationAction;
    }

    public Object getArgs() {
        return this.args;
    }

    public void setArgs(Object args) {
        this.args = args;
    }

    public Object getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedByUserId() {
        return this.createdByUserId;
    }

    public void setCreatedByUserId(String createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTargetUserId() {
        return this.targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getTargetUserLogin() {
        return this.targetUserLogin;
    }

    public void setTargetUserLogin(String targetUserLogin) {
        this.targetUserLogin = targetUserLogin;
    }

    public Boolean isFromAutomod() {
        return this.fromAutomod;
    }

    public Boolean getFromAutomod() {
        return this.fromAutomod;
    }

    public void setFromAutomod(Boolean fromAutomod) {
        this.fromAutomod = fromAutomod;
    }

    @Override
    public String toString() {
        return "{" +
            " type='" + getType() + "'" +
            ", moderationAction='" + getModerationAction() + "'" +
            ", args='" + getArgs() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdByUserId='" + getCreatedByUserId() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", messageId='" + getMessageId() + "'" +
            ", targetUserId='" + getTargetUserId() + "'" +
            ", targetUserLogin='" + getTargetUserLogin() + "'" +
            ", fromAutomod='" + isFromAutomod() + "'" +
            "}";
    }
}
