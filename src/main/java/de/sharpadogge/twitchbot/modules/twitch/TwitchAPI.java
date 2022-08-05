package de.sharpadogge.twitchbot.modules.twitch;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.sharpadogge.twitchbot.modules.integration.AvailableIntegration;
import de.sharpadogge.twitchbot.modules.integration.IntegrationService;
import de.sharpadogge.twitchbot.modules.twitch.model.Stream;
import de.sharpadogge.twitchbot.modules.twitch.model.StreamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.sharpadogge.twitchbot.modules.twitch.model.Event;
import de.sharpadogge.twitchbot.modules.user.User;

@Service
public class TwitchAPI {

    private final RestTemplate restTemplate;
    
    private final IntegrationService integrationService;

    @Autowired
    public TwitchAPI(final RestTemplateBuilder restTemplateBuilder,
                     final IntegrationService integrationService) {
        this.restTemplate = restTemplateBuilder.build();
        this.integrationService = integrationService;
    }


    public ResponseEntity<String> doRequest(final HttpMethod method, final String url, final String accessToken, final boolean useBearer) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Client-ID", integrationService.getIntegration(AvailableIntegration.TWITCH).getClientId());
        headers.add("Authorization", (useBearer ? "Bearer " : "OAuth ") + accessToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, method, entity, String.class);
    }

    public Stream getStream(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Client-ID", integrationService.getIntegration(AvailableIntegration.TWITCH).getClientId());
        HttpEntity<?> entity = new HttpEntity<>(null, headers);
        ResponseEntity<StreamResponse> streamResponse = restTemplate.exchange("https://api.twitch.tv/helix/streams", HttpMethod.GET, entity, StreamResponse.class);
        if (streamResponse.getStatusCode() == HttpStatus.OK) {
            if (streamResponse.getBody() != null) {
                return streamResponse.getBody().getStream();
            }
        }
        return null;
    }

    public User getUser(final String accessToken) {
        ResponseEntity<String> response = doRequest(HttpMethod.GET, "https://api.twitch.tv/helix/users", accessToken, true);
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode userJson = mapper.readTree(response.getBody()).get("data").get(0);
                User user = mapper.readValue(userJson.toString(), User.class);
                user.setTwitchUserId(userJson.get("id").asLong());
                user.setName(userJson.get("display_name").asText().trim());
                user.setLogo(userJson.get("profile_image_url").asText().trim());
                user.setType(userJson.get("broadcaster_type").asText().trim());
                user.setPartner(user.getType() == "partner");
                return user;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public List<Event> latestFollowers(final String accessToken, final Long userTwitchId) {
        List<Event> users = new ArrayList<>();
        ResponseEntity<String> response = doRequest(HttpMethod.GET, "https://api.twitch.tv/helix/users/follows?to_id=" + userTwitchId, accessToken, true);
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(response.getBody());
                if (node.get("data").size() > 0) {
                    for (JsonNode user : node.get("data")) {
                        Event event = new Event(
                            "Follow",
                            user.get("from_id").asLong(),
                            user.get("from_name").asText(),
                            LocalDateTime.parse(user.get("followed_at").asText(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
                        users.add(event);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return users;
    }
}