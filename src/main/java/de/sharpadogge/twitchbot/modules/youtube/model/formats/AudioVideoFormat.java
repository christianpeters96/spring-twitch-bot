package de.sharpadogge.twitchbot.modules.youtube.model.formats;

import com.fasterxml.jackson.databind.JsonNode;

import de.sharpadogge.twitchbot.modules.youtube.model.quality.AudioQuality;
import de.sharpadogge.twitchbot.modules.youtube.model.quality.VideoQuality;

public class AudioVideoFormat extends Format {
    
    private final Integer averageBitrate;
    private final Integer audioSampleRate;
    private final AudioQuality audioQuality;
    private final String qualityLabel;
    private final Integer width;
    private final Integer height;
    private final VideoQuality videoQuality;

    public AudioVideoFormat(JsonNode json, boolean isAdaptive) {
        super(json, isAdaptive);
        audioSampleRate = json.get("audioSampleRate").intValue();
        averageBitrate = json.get("averageBitrate").intValue();
        qualityLabel = json.get("qualityLabel").textValue();
        width = json.get("width").intValue();
        height = json.get("height").intValue();

        VideoQuality videoQuality = null;
        if (json.has("quality")) {
            try {
                videoQuality = VideoQuality.valueOf(json.get("quality").textValue());
            } catch (IllegalArgumentException ignore) {
            }
        }
        this.videoQuality = videoQuality;

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
        return AUDIO_VIDEO;
    }

    public VideoQuality videoQuality() {
        return videoQuality != null ? videoQuality : itag.videoQuality();
    }

    public String qualityLabel() {
        return qualityLabel;
    }

    public Integer width() {
        return width;
    }

    public Integer height() {
        return height;
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
