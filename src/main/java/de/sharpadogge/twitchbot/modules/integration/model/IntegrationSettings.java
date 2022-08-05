package de.sharpadogge.twitchbot.modules.integration.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class IntegrationSettings {

    private String clientId;

    @JsonIgnore
    private String clientSecret;

    private String scopes;

    private String authorizationUrl;

    private String tokenUrl;

    private String revokeUrl;

    private String redirectUrl;

    private String mdi;

    private String image;

    private Boolean valid;

    public IntegrationSettings() {
    }

    public IntegrationSettings(String clientId, String clientSecret, String scopes, String authorizationUrl, String tokenUrl, String revokeUrl, String redirectUrl, String mdi, String image) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scopes = scopes;
        this.authorizationUrl = authorizationUrl;
        this.tokenUrl = tokenUrl;
        this.revokeUrl = revokeUrl;
        this.redirectUrl = redirectUrl;
        this.mdi = mdi;
        this.image = image;
    }

    public IntegrationSettings(IntegrationSettings other) {
        this.clientId = other.clientId;
        this.clientSecret = other.clientSecret;
        this.scopes = other.scopes;
        this.authorizationUrl = other.authorizationUrl;
        this.tokenUrl = other.tokenUrl;
        this.revokeUrl = other.revokeUrl;
        this.redirectUrl = other.redirectUrl;
        this.mdi = other.mdi;
        this.image = other.image;
        this.valid = other.valid;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getRevokeUrl() {
        return revokeUrl;
    }

    public void setRevokeUrl(String revokeUrl) {
        this.revokeUrl = revokeUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getMdi() {
        return mdi;
    }

    public void setMdi(String mdi) {
        this.mdi = mdi;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
