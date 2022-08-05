package de.sharpadogge.twitchbot.modules.spotify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalUrls {

    @JsonProperty("spotify")
    private String spotify;

    public ExternalUrls() {
    }

    public ExternalUrls(String spotify) {
        this.spotify = spotify;
    }

    public String getSpotify() {
        return spotify;
    }

    public void setSpotify(String spotify) {
        this.spotify = spotify;
    }
}
