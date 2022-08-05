package de.sharpadogge.twitchbot.modules.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.sharpadogge.twitchbot.jooq.tables.IntegrationAuth;

import java.util.Map;

@Entity
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    
    @Id
    @Column(name = "id")
    public Long id;

    @Column(name = "token")
    public String token;

    @Column(name = "ttv_id")
    @JsonProperty("_id")
    public Long twitchUserId;

    @Column(name = "name")
    public String name;

    @Column(name = "email")
    public String email;

    @Column(name = "logo")
    public String logo;

    @Column(name = "partner")
    @JsonProperty("partnered")
    public Boolean partner;

    @Column(name = "type")
    public String type;

    @Column(name = "bot_token")
    public String botToken;

    @Transient
    public Map<String, IntegrationAuth> integrationAuth;

    public User() {
    }

    public User(Long id, String token, Long twitchUserId, String name, String email, String logo, Boolean partner, String type, String botToken, Map<String, IntegrationAuth> integrationAuth) {
        this.id = id;
        this.token = token;
        this.twitchUserId = twitchUserId;
        this.name = name;
        this.email = email;
        this.logo = logo;
        this.partner = partner;
        this.type = type;
        this.botToken = botToken;
        this.integrationAuth = integrationAuth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTwitchUserId() {
        return twitchUserId;
    }

    public void setTwitchUserId(Long twitchUserId) {
        this.twitchUserId = twitchUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean getPartner() {
        return partner;
    }

    public void setPartner(Boolean partner) {
        this.partner = partner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public Map<String, IntegrationAuth> getIntegrationAuth() {
        return integrationAuth;
    }

    public void setIntegrationAuth(Map<String, IntegrationAuth> integrationAuth) {
        this.integrationAuth = integrationAuth;
    }
}