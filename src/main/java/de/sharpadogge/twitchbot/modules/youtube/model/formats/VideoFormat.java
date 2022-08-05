package de.sharpadogge.twitchbot.modules.youtube.model.formats;

import com.fasterxml.jackson.databind.JsonNode;

import de.sharpadogge.twitchbot.modules.youtube.model.quality.VideoQuality;

public class VideoFormat extends Format {
    
    private final int fps;
    private final String qualityLabel;
    private final Integer width;
    private final Integer height;
    private final VideoQuality videoQuality;

    public VideoFormat(JsonNode json, boolean isAdaptive) {
        super(json, isAdaptive);
        fps = json.get("fps").intValue();
        qualityLabel = json.get("qualityLabel").textValue();
        if (json.has("size")) {
            String[] split = json.get("size").textValue().split("x");
            width = Integer.parseInt(split[0]);
            height = Integer.parseInt(split[1]);
        } else {
            width = json.get("width").intValue();
            height = json.get("height").intValue();
        }
        VideoQuality videoQuality = null;
        if (json.has("quality")) {
            try {
                videoQuality = VideoQuality.valueOf(json.get("quality").textValue());
            } catch (IllegalArgumentException ignore) {
            }
        }
        this.videoQuality = videoQuality;
    }

    @Override
    public String type() {
        return VIDEO;
    }

    public int fps() {
        return fps;
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
}
