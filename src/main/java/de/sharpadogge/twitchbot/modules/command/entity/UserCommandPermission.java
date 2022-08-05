package de.sharpadogge.twitchbot.modules.command.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_command_permission")
public class UserCommandPermission {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "command_id")
    private Long commandId;

    @Column(name = "permission")
    private String permission;

    public UserCommandPermission() {
    }

    public UserCommandPermission(String permission) {
        this.permission = permission;
    }

    public UserCommandPermission(Long userId, Long commandId, String permission) {
        this.userId = userId;
        this.commandId = commandId;
        this.permission = permission;
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

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "UserCommandPermission{" +
                "userId=" + userId +
                ", commandId=" + commandId +
                ", permission='" + permission + '\'' +
                '}';
    }
}
