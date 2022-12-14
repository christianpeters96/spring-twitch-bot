package de.sharpadogge.twitchbot.modules.youtube.model.playlist;

import java.util.List;

import de.sharpadogge.twitchbot.modules.youtube.model.Filter;

public class YoutubePlaylist {
    
    private PlaylistDetails details;
    private List<PlaylistVideoDetails> videos;

    public YoutubePlaylist(PlaylistDetails details, List<PlaylistVideoDetails> videos) {
        this.details = details;
        this.videos = videos;
    }

    public PlaylistDetails details() {
        return details;
    }

    public List<PlaylistVideoDetails> videos() {
        return videos;
    }

    public PlaylistVideoDetails findVideoById(String videoId) {
        for (PlaylistVideoDetails video : videos) {
            if (video.videoId().equals(videoId))
                return video;
        }
        return null;
    }

    public List<PlaylistVideoDetails> findVideos(Filter<PlaylistVideoDetails> filter) {
        return filter.select(videos);
    }
}
