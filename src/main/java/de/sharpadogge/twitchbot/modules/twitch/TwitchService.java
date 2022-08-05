package de.sharpadogge.twitchbot.modules.twitch;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.sharpadogge.twitchbot.modules.user.User;

@Service
public class TwitchService {
    
    public <T> ResponseEntity<T> apiRequest(final String endpoint, final User user, T clazz) {
        return null;
    }
}