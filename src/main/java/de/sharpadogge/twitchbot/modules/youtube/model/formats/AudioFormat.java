package de.sharpadogge.twitchbot.modules.youtube.model.formats;

import com.fasterxml.jackson.databind.JsonNode;

import de.sharpadogge.twitchbot.modules.utils.JsonUtils;
import de.sharpadogge.twitchbot.modules.youtube.model.quality.AudioQuality;

public class AudioFormat extends Format {
    
    private final Integer averageBitrate;
    private final Integer audioSampleRate;
    private final AudioQuality audioQuality;

    public AudioFormat(JsonNode json, boolean isAdaptive) {
        super(json, isAdaptive);
        audioSampleRate = JsonUtils.getInt(json, "audioSampleRate").orElse(null);
        averageBitrate = JsonUtils.getInt(json, "averageBitrate").orElse(null);

        AudioQuality audioQuality = null;
        if (json.has("audioQuality")) {
            String[] split = json.get("audioQuality").textValue().split("_");
            String quality = split[split.length - 1].toLowerCase();
            try {
                audioQuality = AudioQuality.valueOf(quality);
            } catch (IllegalArgumentException ignore) {
            }
        }
        this.audioQuality = audioQuality;
    }

    @Override
    public String type() {
        return AUDIO;
    }

    public Integer averageBitrate() {
        return averageBitrate;
    }

    public AudioQuality audioQuality() {
        return audioQuality != null ? audioQuality : itag.audioQuality();
    }

    public Integer audioSampleRate() {
        return audioSampleRate;
    }
}
