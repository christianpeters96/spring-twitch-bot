package de.sharpadogge.twitchbot.modules.twitch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Preview {

    private String small;

    private String medium;

    private String large;

    private String template;

    public Preview() {
    }

    public Preview(String small, String medium, String large, String template) {
        this.small = small;
        this.medium = medium;
        this.large = large;
        this.template = template;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
