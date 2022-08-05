package de.sharpadogge.twitchbot.modules.command.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_command_action")
public class UserCommandAction {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "command_id")
    private Long commandId;

    @Column(name = "idx")
    private Integer idx;

    @Column(name = "action")
    private String action;

    public UserCommandAction() {
    }

    public UserCommandAction(String action) {
        this.action = action;
    }

    public UserCommandAction(Long userId, Long commandId, Integer idx, String action) {
        this.userId = userId;
        this.commandId = commandId;
        this.idx = idx;
        this.action = action;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCommandId() {
        return commandId;
    }

    public void setCommandId(Long commandId) {
        this.commandId = commandId;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "UserCommandAction{" +
                "userId=" + userId +
                ", commandId=" + commandId +
                ", idx=" + idx +
                ", action='" + action + '\'' +
                '}';
    }
}
