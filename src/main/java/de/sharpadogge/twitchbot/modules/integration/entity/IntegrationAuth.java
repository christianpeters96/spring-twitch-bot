package de.sharpadogge.twitchbot.modules.integration.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "integration_auth")
public class IntegrationAuth {

    @Id
    @Column(name = "id")
    public Long id;

    @Column(name = "provider")
    public String provider;

    @Column(name = "access_token")
    @JsonProperty("access_token")
    public String accessToken;

    @Column(name = "refresh_token")
    @JsonProperty("refresh_token")
    public String refreshToken;

    @Column(name = "expires_at")
    @JsonProperty("expires_at")
    public LocalDateTime expiresAt;

    @Transient
    @JsonProperty("expires_in")
    public Long expiresIn;

    @Column(name = "scope")
    @JsonProperty("scope")
    public String scope;

    @Column(name = "token_type")
    @JsonProperty("token_type")
    public String tokenType;

    public IntegrationAuth() {
    }

    public IntegrationAuth(Long id, String provider, String accessToken, String refreshToken, LocalDateTime expiresAt, Long expiresIn, String scope, String tokenType) {
        this.id = id;
        this.provider = provider;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
        this.expiresIn = expiresIn;
        this.scope = scope;
        this.tokenType = tokenType;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return "IntegrationAuth{" +
                "id=" + id +
                ", provider='" + provider + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresAt=" + expiresAt +
                ", expiresIn=" + expiresIn +
                ", scope='" + scope + '\'' +
                ", tokenType='" + tokenType + '\'' +
                '}';
    }
}
