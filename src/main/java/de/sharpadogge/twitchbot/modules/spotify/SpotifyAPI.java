package de.sharpadogge.twitchbot.modules.spotify;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.sharpadogge.twitchbot.modules.integration.AvailableIntegration;
import de.sharpadogge.twitchbot.modules.integration.IntegrationService;
import de.sharpadogge.twitchbot.modules.integration.entity.IntegrationAuth;
import de.sharpadogge.twitchbot.modules.spotify.model.ResultCurrentSong;
import de.sharpadogge.twitchbot.modules.spotify.model.ResultSearchTrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SpotifyAPI {

    private final IntegrationService integrationService;

    private final RestTemplate restTemplate;

    @Autowired
    public SpotifyAPI(final IntegrationService integrationService,
                      final RestTemplateBuilder restTemplateBuilder) {
        this.integrationService = integrationService;
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getAuthorizationUrl() {
        return integrationService.getIntegration(AvailableIntegration.SPOTIFY).getAuthorizationUrl();
    }

    public ResultSearchTrack searchTrack(final IntegrationAuth auth, final String query) {
        try {
            ResponseEntity<JsonNode> response = apiRequest(auth, HttpMethod.GET, "/v1/search?q=" + query + "&type=track&market=DE&limit=1");
            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode node = response.getBody();
                if (node != null) {
                    return new ObjectMapper().readValue(node.toString(), ResultSearchTrack.class);
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResultCurrentSong getCurrentSong(final IntegrationAuth auth) {
        try {
            ResponseEntity<JsonNode> response = apiRequest(auth, HttpMethod.GET, "/v1/me/player/currently-playing?market=DE");
            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode node = response.getBody();
                if (node != null) {
                    return new ObjectMapper().readValue(node.toString(), ResultCurrentSong.class);
                }
                // success (song is playing)
                // is_playing == true / false
            }
            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                // no song playing / spotify is not running
                return null;
            }
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean addTrackToQueue(final IntegrationAuth auth, ResultSearchTrack trackInfo) {
        String trackUri = trackInfo.getTracks().getItems().get(0).getUri();
        ResponseEntity<JsonNode> result = apiRequest(auth, HttpMethod.POST, "/v1/me/player/queue?uri=" + trackUri);
        return result.getStatusCode().is2xxSuccessful();
    }

    public boolean skipSong(final IntegrationAuth auth) {
        String uri = "/v1/me/player/next";
        ResponseEntity<JsonNode> response = apiRequest(auth, HttpMethod.POST, uri);
        System.out.println(response);
        if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.NO_CONTENT) {
            return true;
        }
        return false;
    }

    public ResponseEntity<JsonNode> apiRequest(final IntegrationAuth auth, final HttpMethod httpMethod, final String endpoint) {
        final String api = "https://api.spotify.com";
        final String url = api + endpoint;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + auth.getAccessToken());

        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        try {
            return restTemplate.exchange(url, httpMethod, request, JsonNode.class);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
