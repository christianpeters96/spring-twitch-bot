package de.sharpadogge.twitchbot.modules.discord;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.sharpadogge.twitchbot.modules.twitch.model.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Service
public class DiscordService {

    private final RestTemplate restTemplate;

    @Autowired
    public DiscordService(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public boolean pushLiveWebhook(final Stream stream, final String webHook) {
        try {
            if (stream != null) {
                File file = ResourceUtils.getFile("classpath:discord-webhook.json");
                String jsonString = new ObjectMapper().readTree(file).toString();
                jsonString = jsonString
                        .replaceAll("%channel%", stream.getChannel().getName())
                        .replaceAll("%avatar%", stream.getChannel().getLogo())
                        .replaceAll("%title%", stream.getChannel().getStatus())
                        .replaceAll("%game%", stream.getGame())
                        .replaceAll("%viewers%", stream.getViewers().toString())
                        .replaceAll("%preview%", stream.getPreview().getMedium())
                        .replaceAll("%author_name%", "Twitch Live Notification")
                        .replaceAll("%author_avatar%", "https://techcrunch.com/wp-content/uploads/2017/10/twitch-logo.jpg");

                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
                restTemplate.postForObject(webHook, request, Object.class);
                return true;
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
/*
{
    "content": "Hey @everyone, sharpadogge ist nun Live! Schaut doch mal auf seinem Kanal vorbei! https://twitch.tv/sharpadogge",
    "username": "Twitch",
    "avatar_url": "https://i.pinimg.com/564x/b0/2b/38/b02b38a84206ef8c7ef248e22f13d1fb.jpg",
    "embeds": [
        {
            "author": {
                "name": "sharpadogge",
                "url": "https://twitch.tv/sharpadogge",
                "icon_url": "https://static-cdn.jtvnw.net/user-default-pictures-uv/75305d54-c7cc-40d1-bb9c-91fbe85943c7-profile_image-300x300.png"
            },
            "thumbnail": {
                "url": "https://static-cdn.jtvnw.net/user-default-pictures-uv/75305d54-c7cc-40d1-bb9c-91fbe85943c7-profile_image-300x300.png"
            },
            "description": "[**Kleiner Talk in die Runde und später ein wenig Among Us!**](https://twitch.tv/sharpadogge)",
            "fields": [
                {
                    "name": "Game",
                    "value": "Just Chatting",
                    "inline": true
                },
                {
                    "name": "Viewers",
                    "value": "0",
                    "inline": true
                }
            ],
            "image": {
                "url": "https://static-cdn.jtvnw.net/previews-ttv/live_user_sharpadogge-320x180.jpg"
            }
        }
    ]
}
*/