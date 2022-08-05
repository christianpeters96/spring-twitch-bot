package de.sharpadogge.twitchbot.modules.music;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.sharpadogge.twitchbot.modules.music.entity.MusicSettings;
import de.sharpadogge.twitchbot.modules.music.entity.MusicYoutubePlaylist;
import de.sharpadogge.twitchbot.modules.music.entity.MusicYoutubeRequest;
import de.sharpadogge.twitchbot.modules.music.entity.MusicYoutubeSettings;
import de.sharpadogge.twitchbot.modules.user.User;
import de.sharpadogge.twitchbot.modules.user.UserRepository;
import de.sharpadogge.twitchbot.modules.utils.StringUtils;
import de.sharpadogge.twitchbot.modules.utils.ToastNotification;
import de.sharpadogge.twitchbot.modules.utils.ToastResponse;
import de.sharpadogge.twitchbot.modules.utils.ToastUtils;
import de.sharpadogge.twitchbot.modules.youtube.YoutubeDownloader;
import de.sharpadogge.twitchbot.modules.youtube.model.YoutubeVideo;
import de.sharpadogge.twitchbot.modules.youtube.model.formats.Format;
import de.sharpadogge.twitchbot.modules.youtube.model.playlist.PlaylistVideoDetails;
import de.sharpadogge.twitchbot.modules.youtube.model.playlist.YoutubePlaylist;

@RestController
@RequestMapping("/api/music")
public class MusicController {
    
    private final UserRepository userRepository;

    private final MusicRepository musicRepository;

    @Autowired
    public MusicController(UserRepository userRepository, MusicRepository musicRepository) {
        this.userRepository = userRepository;
        this.musicRepository = musicRepository;
    }

    @GetMapping("/settings")
    public ResponseEntity<Map<String, Object>> getSettings(@RequestHeader("Authorization") final String authorization) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            Map<String, Object> output = new HashMap<>();
            output.put("general", musicRepository.getMusicSettingsForUser(user.getId()));
            output.put("youtube", musicRepository.getMusicYoutubeSettingsForUser(user.getId()));

            return new ResponseEntity<>(output, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/settings")
    public ResponseEntity<ToastResponse<Object>> updateSettings(@RequestHeader("Authorization") final String authorization, @RequestBody final Map<String, Object> settings) throws Exception {
        
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(mapper.writeValueAsString(settings));

            if (json.has("general")) {
                JsonNode general = json.get("general");
                MusicSettings musicSettings = new MusicSettings();
                musicSettings.setId(general.get("id").longValue());
                musicSettings.setUserId(user.getId());
                if (general.has("provider")) {
                    musicSettings.setProvider(general.get("provider").textValue());
                }
                musicRepository.updateMusicSettings(musicSettings);
            }
            if (json.has("youtube")) {
                JsonNode youtube = json.get("youtube");
                MusicYoutubeSettings youtubeSettings = new MusicYoutubeSettings();
                youtubeSettings.setId(youtube.get("id").longValue());
                youtubeSettings.setUserId(user.getId());
                if (youtube.has("maxVideoLength")) {
                    youtubeSettings.setMaxVideoLength(youtube.get("maxVideoLength").intValue());
                }
                musicRepository.updateMusicYoutubeSettings(youtubeSettings);
            }
            return ToastUtils.createResponse(true, HttpStatus.OK, new ToastNotification("success", "Erfolg", "Deine Einstellungen wurden aktualisiert."));
        }
        return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Du bist nicht autorisiert"));
    }

    @GetMapping("/requests")
    public ResponseEntity<List<MusicYoutubeRequest>> getSongRequests(@RequestHeader("Authorization") final String authorization) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return new ResponseEntity<>(musicRepository.getMusicYoutubeRequestsForUser(user.getId()), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/audio/{videoId}")
    public ResponseEntity<Map<String, String>> getYoutubeAudioStream(@RequestHeader("Authorization") final String authorization, @PathVariable final String videoId) throws Exception {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            Map<String, String> output = new HashMap<>();
            YoutubeDownloader downloader = new YoutubeDownloader();
            YoutubeVideo video = downloader.getVideo(videoId);
            Integer bitrate = 0;
            Format bestFormat = null;
            for (Format format : video.formats()) {
                if (format.mimeType().contains("audio/webm")) {
                    if (format.bitrate() > bitrate) {
                        bestFormat = format;
                    }
                }
            }
            output.put("url", bestFormat.url());
            return new ResponseEntity<>(output, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
    
    @DeleteMapping("/request/{videoDatabaseId}")
    public ResponseEntity<Object> removeRequestedSong(@RequestHeader("Authorization") final String authorization, @PathVariable final Long videoDatabaseId) throws Exception {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            int updated = musicRepository.setRequestedSongFinished(user.getId(), videoDatabaseId);
            return new ResponseEntity<>(null, updated > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/playlist")
    public ResponseEntity<List<MusicYoutubePlaylist>> getPlaylist(@RequestHeader("Authorization") final String authorization) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return new ResponseEntity<>(musicRepository.getMusicYoutubePlaylistForUser(user.getId()), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
    
    @PutMapping("/playlist/song/{videoId}")
    public ResponseEntity<Object> addSongToPlaylist(@RequestHeader("Authorization") final String authorization, @PathVariable final String videoId) throws Exception {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            musicRepository.addSongToPlaylist(user.getId(), new YoutubeDownloader().getVideo(videoId));
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
    
    @PutMapping("/playlist/playlist/{playlistId}")
    public ResponseEntity<Object> addPlaylistToPlaylist(@RequestHeader("Authorization") final String authorization, @PathVariable final String playlistId) throws Exception {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            YoutubePlaylist playlist = new YoutubeDownloader().getPlaylist(playlistId);
            int added = 0;
            for (PlaylistVideoDetails video : playlist.videos()) {
                if (video.isPlayable()) {
                    added += musicRepository.addSongToPlaylist(user.getId(), video);
                }
            }
            return new ResponseEntity<>(null, added > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
    
    @DeleteMapping("/playlist/{videoDatabaseId}")
    public ResponseEntity<Object> deletePlaylistSong(@RequestHeader("Authorization") final String authorization, @PathVariable final Long videoDatabaseId) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            int deleted = musicRepository.removePlaylistSong(user.getId(), videoDatabaseId);
            return new ResponseEntity<>(null, deleted > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
