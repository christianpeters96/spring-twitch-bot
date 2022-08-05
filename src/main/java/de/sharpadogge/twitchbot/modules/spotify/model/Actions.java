package de.sharpadogge.twitchbot.modules.spotify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Actions {

    @JsonProperty("disallows")
    private Disallows disallows;

    public Actions() {
    }

    public Actions(Disallows disallows) {
        this.disallows = disallows;
    }

    public Disallows getDisallows() {
        return disallows;
    }

    public void setDisallows(Disallows disallows) {
        this.disallows = disallows;
    }
}
