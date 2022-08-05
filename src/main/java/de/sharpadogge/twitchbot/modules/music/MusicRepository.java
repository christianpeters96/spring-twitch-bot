package de.sharpadogge.twitchbot.modules.music;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.sharpadogge.twitchbot.jooq.Tables;
import de.sharpadogge.twitchbot.modules.music.entity.MusicSettings;
import de.sharpadogge.twitchbot.modules.music.entity.MusicYoutubePlaylist;
import de.sharpadogge.twitchbot.modules.music.entity.MusicYoutubeRequest;
import de.sharpadogge.twitchbot.modules.music.entity.MusicYoutubeSettings;
import de.sharpadogge.twitchbot.modules.youtube.model.YoutubeVideo;
import de.sharpadogge.twitchbot.modules.youtube.model.playlist.PlaylistVideoDetails;

@Repository
public class MusicRepository {
    
    private final DSLContext create;

    @Autowired
    public MusicRepository(DSLContext create) {
        this.create = create;
    }

    public MusicSettings getMusicSettingsForUser(Long userId) {
        MusicSettings output = getMusicSettingsForUserInternal(userId);
        if (output == null) {
            resetMusicSettingsForUser(userId);
            output = getMusicSettingsForUserInternal(userId);
        }
        return output;
    }

    private MusicSettings getMusicSettingsForUserInternal(Long userId) {
        return create
            .selectFrom(Tables.MUSIC_SETTINGS)
            .where(Tables.MUSIC_SETTINGS.USER_ID.eq(userId))
            .fetchOneInto(MusicSettings.class);
    }

    public void resetMusicSettingsForUser(Long userId) {
        MusicSettings entry = getMusicSettingsForUserInternal(userId);
        if (entry == null) {
            entry = new MusicSettings();
            entry.setProvider("youtube");
            entry.setUserId(userId);
            create.newRecord(Tables.MUSIC_SETTINGS, entry).insert();
        }
        else {
            entry.setProvider("youtube");
            create.newRecord(Tables.MUSIC_SETTINGS, entry).update();
        }
    }

    public void updateMusicSettings(MusicSettings entry) {
        create.newRecord(Tables.MUSIC_SETTINGS, entry).update();
    }

    public MusicYoutubeSettings getMusicYoutubeSettingsForUser(Long userId) {
        MusicYoutubeSettings output = getMusicYoutubeSettingsForUserInternal(userId);
        if (output == null) {
            resetMusicYoutubeSettingsForUser(userId);
            output = getMusicYoutubeSettingsForUserInternal(userId);
        }
        return output;
    }

    private MusicYoutubeSettings getMusicYoutubeSettingsForUserInternal(Long userId) {
        return create
            .selectFrom(Tables.MUSIC_YOUTUBE_SETTINGS)
            .where(Tables.MUSIC_YOUTUBE_SETTINGS.USER_ID.eq(userId))
            .fetchOneInto(MusicYoutubeSettings.class);
    }

    public void resetMusicYoutubeSettingsForUser(Long userId) {
        MusicYoutubeSettings entry = getMusicYoutubeSettingsForUserInternal(userId);
        if (entry == null) {
            entry = new MusicYoutubeSettings();
            entry.setMaxVideoLength(300);
            entry.setUserId(userId);
            create.newRecord(Tables.MUSIC_YOUTUBE_SETTINGS, entry).insert();
        }
        else {
            entry.setMaxVideoLength(300);
            create.newRecord(Tables.MUSIC_YOUTUBE_SETTINGS, entry).update();
        }
    }

    public void updateMusicYoutubeSettings(MusicYoutubeSettings entry) {
        create.newRecord(Tables.MUSIC_YOUTUBE_SETTINGS, entry).update();
    }

    public List<MusicYoutubeRequest> getMusicYoutubeRequestsForUser(Long userId) {
        List<MusicYoutubeRequest> output = create
            .selectFrom(Tables.MUSIC_YOUTUBE_REQUESTS)
            .where(Tables.MUSIC_YOUTUBE_REQUESTS.USER_ID.eq(userId))
            .and(Tables.MUSIC_YOUTUBE_REQUESTS.FINISHED.eq((byte)0))
            .fetchInto(MusicYoutubeRequest.class);

        return output.stream().map(r -> {
            r.setVideoTitle(URLDecoder.decode(r.getVideoTitle(), StandardCharsets.UTF_8));
            r.setVideoAuthor(URLDecoder.decode(r.getVideoAuthor(), StandardCharsets.UTF_8));
            return r;
        }).collect(Collectors.toList());
    }

    public void addSongRequest(Long userId, String requestedBy, YoutubeVideo video) {
        MusicYoutubeRequest request = new MusicYoutubeRequest();
        request.setUserId(userId);
        request.setVideoId(video.details().videoId());
        request.setVideoDurationSec((int)(video.getDurationMs()/1000));
        request.setVideoTitle(URLEncoder.encode(video.details().title(), StandardCharsets.UTF_8));
        request.setVideoAuthor(URLEncoder.encode(video.details().author(), StandardCharsets.UTF_8));
        request.setRequestedTs(LocalDateTime.now());
        request.setRequestedBy(requestedBy);
        request.setFinished(false);
        create.newRecord(Tables.MUSIC_YOUTUBE_REQUESTS, request).insert();
    }

    public int setRequestedSongFinished(Long userId, Long id) {
        return create.update(Tables.MUSIC_YOUTUBE_REQUESTS)
            .set(Tables.MUSIC_YOUTUBE_REQUESTS.FINISHED, (byte) 1)
            .where(Tables.MUSIC_YOUTUBE_REQUESTS.USER_ID.eq(userId))
            .and(Tables.MUSIC_YOUTUBE_REQUESTS.ID.eq(id))
            .execute();
    }

    public List<MusicYoutubePlaylist> getMusicYoutubePlaylistForUser(Long userId) {
        List<MusicYoutubePlaylist> output = create
            .selectFrom(Tables.MUSIC_YOUTUBE_PLAYLIST)
            .where(Tables.MUSIC_YOUTUBE_PLAYLIST.USER_ID.eq(userId))
            .fetchInto(MusicYoutubePlaylist.class);

        return output.stream().map(r -> {
            r.setVideoTitle(URLDecoder.decode(r.getVideoTitle(), StandardCharsets.UTF_8));
            r.setVideoAuthor(URLDecoder.decode(r.getVideoAuthor(), StandardCharsets.UTF_8));
            return r;
        }).collect(Collectors.toList());
    }

    public int addSongToPlaylist(Long userId, YoutubeVideo video) {
        MusicYoutubePlaylist song = new MusicYoutubePlaylist();
        song.setUserId(userId);
        song.setVideoId(video.details().videoId());
        song.setVideoDurationSec((int)(video.getDurationMs()/1000));
        song.setVideoTitle(URLEncoder.encode(video.details().title(), StandardCharsets.UTF_8));
        song.setVideoAuthor(URLEncoder.encode(video.details().author(), StandardCharsets.UTF_8));
        return create.newRecord(Tables.MUSIC_YOUTUBE_PLAYLIST, song).insert();
    }

    public int addSongToPlaylist(Long userId, PlaylistVideoDetails video) {
        MusicYoutubePlaylist song = new MusicYoutubePlaylist();
        song.setUserId(userId);
        song.setVideoId(video.videoId());
        song.setVideoDurationSec(video.lengthSeconds());
        song.setVideoTitle(URLEncoder.encode(video.title(), StandardCharsets.UTF_8));
        song.setVideoAuthor(URLEncoder.encode(video.author(), StandardCharsets.UTF_8));
        return create.newRecord(Tables.MUSIC_YOUTUBE_PLAYLIST, song).insert();
    }

    public int removePlaylistSong(Long userId, Long id) {
        return create.deleteFrom(Tables.MUSIC_YOUTUBE_PLAYLIST)
            .where(Tables.MUSIC_YOUTUBE_PLAYLIST.USER_ID.eq(userId))
            .and(Tables.MUSIC_YOUTUBE_PLAYLIST.ID.eq(id))
            .execute();
    }
}
