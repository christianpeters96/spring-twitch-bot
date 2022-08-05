package de.sharpadogge.twitchbot.modules.command.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_command_alias")
public class UserCommandAlias {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "command_id")
    private Long commandId;

    @Column(name = "alias")
    private String alias;

    public UserCommandAlias() {
    }

    public UserCommandAlias(String alias) {
        this.alias = alias;
    }

    public UserCommandAlias(Long userId, Long commandId, String alias) {
        this.userId = userId;
        this.commandId = commandId;
        this.alias = alias;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "UserCommandAlias{" +
                "userId=" + userId +
                ", commandId=" + commandId +
                ", alias='" + alias + '\'' +
                '}';
    }
}
