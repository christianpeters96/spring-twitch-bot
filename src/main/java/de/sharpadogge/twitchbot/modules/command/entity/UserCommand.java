package de.sharpadogge.twitchbot.modules.command.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_command")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCommand {

    @Id
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "cmd")
    @JsonProperty("command")
    private String cmd;

    @Column(name = "argc")
    private Integer argc = 0;

    @Column(name = "enabled")
    private Boolean enabled = false;

    @Column(name = "name")
    private String name = "Unnamed";

    @Column(name = "description")
    private String description = "No description";

    @Column(name = "global_delay")
    @JsonProperty("global_delay")
    private Integer globalDelay = 0;

    @Column(name = "user_delay")
    @JsonProperty("user_delay")
    private Integer userDelay = 0;

    @Column(name = "cost")
    private Integer cost = 0;

    @Transient
    private List<UserCommandAction> actions;

    @Transient
    private List<UserCommandAlias> aliases;

    @Transient
    private List<UserCommandPermission> permissions;

    public UserCommand() {
    }

    public UserCommand(Long id, Long userId, String cmd, Integer argc, Boolean enabled, String name, String description, Integer globalDelay, Integer userDelay, Integer cost) {
        this.id = id;
        this.userId = userId;
        this.cmd = cmd;
        this.argc = argc;
        this.enabled = enabled;
        this.name = name;
        this.description = description;
        this.globalDelay = globalDelay;
        this.userDelay = userDelay;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Integer getArgc() {
        return argc;
    }

    public void setArgc(Integer argc) {
        this.argc = argc;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getGlobalDelay() {
        return globalDelay;
    }

    public void setGlobalDelay(Integer globalDelay) {
        this.globalDelay = globalDelay;
    }

    public Integer getUserDelay() {
        return userDelay;
    }

    public void setUserDelay(Integer userDelay) {
        this.userDelay = userDelay;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public List<UserCommandAction> getActions() {
        return actions;
    }

    public void setActions(List<UserCommandAction> actions) {
        this.actions = actions;
    }

    public List<UserCommandAlias> getAliases() {
        return aliases;
    }

    public void setAliases(List<UserCommandAlias> aliases) {
        this.aliases = aliases;
    }

    public List<UserCommandPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<UserCommandPermission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "UserCommand{" +
                "id=" + id +
                ", userId=" + userId +
                ", cmd='" + cmd + '\'' +
                ", argc=" + argc +
                ", enabled=" + enabled +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", globalDelay=" + globalDelay +
                ", userDelay=" + userDelay +
                ", cost=" + cost +
                ", actions=" + actions +
                ", aliases=" + aliases +
                ", permissions=" + permissions +
                '}';
    }
}
