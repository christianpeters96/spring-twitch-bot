package de.sharpadogge.twitchbot.modules.twitch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Channel {

    private Boolean mature;

    private String status;

    @JsonProperty("broadcaster_language")
    private String broadcasterLanguage;

    @JsonProperty("display_name")
    private String displayName;

    private String game;

    private String language;

    @JsonProperty("_id")
    private Long id;

    private String name;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    private Boolean partner;

    private String logo;

    @JsonProperty("video_banner")
    private String videoBanner;

    @JsonProperty("profile_banner")
    private String profileBanner;

    @JsonProperty("profile_banner_background_color")
    private String profileBannerBackgroundColor;

    private String url;

    private Integer views;

    private Integer followers;

    public Channel() {
    }

    public Channel(Boolean mature, String status, String broadcasterLanguage, String displayName, String game, String language, Long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean partner, String logo, String videoBanner, String profileBanner, String profileBannerBackgroundColor, String url, Integer views, Integer followers) {
        this.mature = mature;
        this.status = status;
        this.broadcasterLanguage = broadcasterLanguage;
        this.displayName = displayName;
        this.game = game;
        this.language = language;
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.partner = partner;
        this.logo = logo;
        this.videoBanner = videoBanner;
        this.profileBanner = profileBanner;
        this.profileBannerBackgroundColor = profileBannerBackgroundColor;
        this.url = url;
        this.views = views;
        this.followers = followers;
    }

    public Boolean getMature() {
        return mature;
    }

    public void setMature(Boolean mature) {
        this.mature = mature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBroadcasterLanguage() {
        return broadcasterLanguage;
    }

    public void setBroadcasterLanguage(String broadcasterLanguage) {
        this.broadcasterLanguage = broadcasterLanguage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getPartner() {
        return partner;
    }

    public void setPartner(Boolean partner) {
        this.partner = partner;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getVideoBanner() {
        return videoBanner;
    }

    public void setVideoBanner(String videoBanner) {
        this.videoBanner = videoBanner;
    }

    public String getProfileBanner() {
        return profileBanner;
    }

    public void setProfileBanner(String profileBanner) {
        this.profileBanner = profileBanner;
    }

    public String getProfileBannerBackgroundColor() {
        return profileBannerBackgroundColor;
    }

    public void setProfileBannerBackgroundColor(String profileBannerBackgroundColor) {
        this.profileBannerBackgroundColor = profileBannerBackgroundColor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }
}
