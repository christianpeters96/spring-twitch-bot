package de.sharpadogge.twitchbot.modules.spotify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Disallows {

    @JsonProperty("pausing")
    private Boolean pausing;

    @JsonProperty("skipping_prev")
    private Boolean skippingPrev;

    public Disallows() {
    }

    public Disallows(Boolean pausing, Boolean skippingPrev) {
        this.pausing = pausing;
        this.skippingPrev = skippingPrev;
    }

    public Boolean getPausing() {
        return pausing;
    }

    public void setPausing(Boolean pausing) {
        this.pausing = pausing;
    }

    public Boolean getSkippingPrev() {
        return skippingPrev;
    }

    public void setSkippingPrev(Boolean skippingPrev) {
        this.skippingPrev = skippingPrev;
    }
}
