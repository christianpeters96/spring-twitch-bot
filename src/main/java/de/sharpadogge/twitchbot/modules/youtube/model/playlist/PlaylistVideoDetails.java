package de.sharpadogge.twitchbot.modules.youtube.model.playlist;

import com.fasterxml.jackson.databind.JsonNode;

import de.sharpadogge.twitchbot.modules.youtube.YoutubeException.DownloadUnavailableException;
import de.sharpadogge.twitchbot.modules.youtube.model.AbstractVideoDetails;

import static de.sharpadogge.twitchbot.modules.youtube.YoutubeException.*;

public class PlaylistVideoDetails extends AbstractVideoDetails {
    
    private int index;
    private boolean isPlayable;

    public PlaylistVideoDetails() {
        
    }

    public PlaylistVideoDetails(JsonNode json) {
        super(json);
        if (json.has("shortBylineText")) {
            author = json.get("shortBylineText").get("runs").get(0).get("text").textValue();
        }
        JsonNode jsonTitle = json.get("title");
        if (jsonTitle.has("simpleText")) {
            title = jsonTitle.get("simpleText").textValue();
        } else {
            title = jsonTitle.get("runs").get(0).get("text").textValue();
        }
        if (!thumbnails().isEmpty()) {
            // Otherwise, contains "/hqdefault.jpg?"
            isLive = thumbnails().get(0).contains("/hqdefault_live.jpg?");
        }

        if (json.has("index")) {
            index = json.get("index").get("simpleText").intValue();
        }
        isPlayable = json.get("isPlayable").booleanValue();
    }

    @Override
    protected void checkDownload() throws DownloadUnavailableException {
        if (!isPlayable) {
            throw new RestrictedVideoException("Can not download " + title());
        } else if (isLive() || lengthSeconds() == 0) {
            throw new LiveVideoException("Can not download live stream");
        }
    }

    public int index() {
        return index;
    }

    public boolean isPlayable() {
        return isPlayable;
    }
}
