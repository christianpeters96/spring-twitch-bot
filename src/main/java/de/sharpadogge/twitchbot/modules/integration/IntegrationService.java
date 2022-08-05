package de.sharpadogge.twitchbot.modules.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.sharpadogge.twitchbot.config.IntegrationConfiguration;
import de.sharpadogge.twitchbot.modules.integration.entity.IntegrationAuth;
import de.sharpadogge.twitchbot.modules.integration.json.IntegrationAuthDeserializer;
import de.sharpadogge.twitchbot.modules.integration.model.Integration;
import de.sharpadogge.twitchbot.modules.integration.model.IntegrationSettings;
import de.sharpadogge.twitchbot.modules.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IntegrationService {

    private final Logger log = LoggerFactory.getLogger(IntegrationService.class);

    private final HashMap<String, IntegrationSettings> integrationSettings = new HashMap<>();

    private final IntegrationConfiguration integrationConfiguration;

    private final IntegrationRepository integrationRepository;

    private final RestTemplate restTemplate;

    @Autowired
    public IntegrationService(final IntegrationConfiguration integrationConfiguration,
                              final IntegrationRepository integrationRepository,
                              final RestTemplateBuilder restTemplateBuilder) {

        this.integrationConfiguration = integrationConfiguration;
        this.integrationRepository = integrationRepository;
        this.restTemplate = restTemplateBuilder.build();
    }

    @PostConstruct
    public void initialize() {
        for (final AvailableIntegration integration : AvailableIntegration.values()) {
            if (integrationConfiguration.getServices().containsKey(integration.getKey())) {
                Map<String, Boolean> checks = new HashMap<>();
                checks.put("client-id", integrationConfiguration.getServices().get(integration.getKey()).containsKey("client-id"));
                checks.put("client-secret", integrationConfiguration.getServices().get(integration.getKey()).containsKey("client-secret"));
                checks.put("scopes", integrationConfiguration.getServices().get(integration.getKey()).containsKey("scopes"));
                checks.put("authorization-url", integrationConfiguration.getServices().get(integration.getKey()).containsKey("authorization-url"));
                checks.put("token-url", integrationConfiguration.getServices().get(integration.getKey()).containsKey("token-url"));
                checks.put("revoke-url", integrationConfiguration.getServices().get(integration.getKey()).containsKey("revoke-url"));
                checks.put("redirect-url", integrationConfiguration.getServices().get(integration.getKey()).containsKey("redirect-url"));
                List<String> failures = checks.entrySet()
                        .stream()
                        .filter(e -> !e.getValue())
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
                if (failures.size() != 0) {
                    log.error("Failed adding integration for '" + integration.getKey() + "' : missing configuration [" + String.join(", ", failures) + "]");
                }
                else {

                    IntegrationSettings settings = new IntegrationSettings(
                            integrationConfiguration.getServices().get(integration.getKey()).get("client-id"),
                            integrationConfiguration.getServices().get(integration.getKey()).get("client-secret"),
                            integrationConfiguration.getServices().get(integration.getKey()).get("scopes"),
                            integrationConfiguration.getServices().get(integration.getKey()).get("authorization-url"),
                            integrationConfiguration.getServices().get(integration.getKey()).get("token-url"),
                            integrationConfiguration.getServices().get(integration.getKey()).get("revoke-url"),
                            integrationConfiguration.getServices().get(integration.getKey()).get("redirect-url"),
                            integration.getMdi(),
                            integration.getImage()
                    );
                    String fullAuthorizationUrl = createAuthorizationUrl(integration.getKey(), settings);
                    settings.setAuthorizationUrl(fullAuthorizationUrl);

                    integrationSettings.put(integration.getKey(), settings);

                    log.info("Added '" + integration.getKey() + "' integration");
                }
            }
        }
    }

    public Map<String, IntegrationSettings> getIntegrationSettings() {
        return integrationSettings;
    }

    public IntegrationSettings getIntegration(final String provider) {
        return integrationSettings.get(provider);
    }

    public IntegrationSettings getIntegration(AvailableIntegration availableIntegration) {
        return integrationSettings.get(availableIntegration.getKey());
    }

    public Integration getUserIntegration(Long userId, final String provider, boolean validate) {
        IntegrationSettings settings = new IntegrationSettings(integrationSettings.get(provider));
        Integration integration = new Integration(settings, null);
        List<IntegrationAuth> integrationAuthList = integrationRepository.getUserIntegrations(userId);
        for (IntegrationAuth auth : integrationAuthList) {
            if (auth.getProvider().equalsIgnoreCase(provider)) {
                integration.setAuth(auth);
                if (validate) validate(userId, integration);
                break;
            }
        }
        return integration;
    }

    public Map<String, Integration> getUserIntegrations(Long userId, boolean validate) {
        Map<String, Integration> integrationMap = new HashMap<>();
        List<IntegrationAuth> integrationAuthList = integrationRepository.getUserIntegrations(userId);
        for (Map.Entry<String, IntegrationSettings> entry : integrationSettings.entrySet()) {
            IntegrationSettings settings = new IntegrationSettings(entry.getValue());
            boolean found = false;
            for (IntegrationAuth auth : integrationAuthList) {
                if (auth.getProvider().equalsIgnoreCase(entry.getKey())) {
                    Integration integration = new Integration(settings, auth);
                    if (validate) validate(userId, integration);
                    integrationMap.put(entry.getKey(), integration);
                    found = true;
                    break;
                }
            }
            if (!found) {
                integrationMap.put(entry.getKey(), new Integration(settings, null));
            }
        }
        return integrationMap;
    }

    public int bind(Long authId, Long userId) {
        return integrationRepository.bind(authId, userId);
    }

    public int unbind(String provider, Long userId) {
        return integrationRepository.unbind(provider, userId);
    }

    public String createAuthorizationUrl(String provider, IntegrationSettings integrationSettings) {
        String url = integrationSettings.getAuthorizationUrl();
        url += "?response_type=code";
        url += "&client_id=" + integrationSettings.getClientId();
        url += "&scope=" + integrationSettings.getScopes().replaceAll(" ", "+");
        url += "&state=" + provider; // TODO: add some hash and validate it on return (for security)
        url += "&redirect_uri=" + integrationSettings.getRedirectUrl();
        return url;
    }

    public IntegrationAuth authorizeWithCode(final User user, final String provider, final String code) {
        IntegrationSettings integrationSettings = this.integrationSettings.get(provider);
        String url = integrationSettings.getTokenUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((integrationSettings.getClientId() + ":" + integrationSettings.getClientSecret()).getBytes()));

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("client_id", integrationSettings.getClientId());
        requestBody.add("client_secret", integrationSettings.getClientSecret());
        requestBody.add("code", code.trim());
        requestBody.add("redirect_uri", integrationSettings.getRedirectUrl());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

        try {
            ObjectMapper mapper = getObjectMapper(provider);
            JsonNode json = restTemplate.postForObject(url, request, JsonNode.class);
            if (json != null) {
                IntegrationAuth auth = mapper.readValue(json.toString(), IntegrationAuth.class);
                if (auth.getScope() == null || auth.getScope().length() == 0) {
                    auth.setScope(integrationSettings.getScopes());
                }
                integrationRepository.insert(auth);
                Optional<IntegrationAuth> authOpt = integrationRepository.findByAccessToken(auth.getAccessToken());
                if (authOpt.isPresent()) {
                    if (user != null) integrationRepository.bind(authOpt.get().getId(), user.getId());
                    return auth;
                }
            }
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public IntegrationAuth refreshToken(Long userId, IntegrationSettings integrationSettings, IntegrationAuth auth) {
        final String url = integrationSettings.getTokenUrl();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((integrationSettings.getClientId() + ":" + integrationSettings.getClientSecret()).getBytes()));

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", integrationSettings.getClientId());
        requestBody.add("client_secret", integrationSettings.getClientSecret());
        requestBody.add("grant_type", "refresh_token");
        requestBody.add("refresh_token", auth.getRefreshToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, request, JsonNode.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            try {
                ObjectMapper mapper = getObjectMapper(auth.getProvider());
                IntegrationAuth newAuth = mapper.readValue(response.getBody().toString(), IntegrationAuth.class);
                if (newAuth.getRefreshToken() == null) newAuth.setRefreshToken(auth.getRefreshToken());
                newAuth.setScope(integrationSettings.getScopes());

                integrationRepository.insert(newAuth);
                Optional<IntegrationAuth> authOpt = integrationRepository.findByAccessToken(newAuth.getAccessToken());
                if (authOpt.isPresent()) {
                    integrationRepository.unbind(auth.getProvider(), userId);
                    integrationRepository.bind(authOpt.get().getId(), userId);
                    return newAuth;
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public boolean revokeToken(final String provider, final User user) {
        try {
            if (!integrationSettings.containsKey(provider)) return false;

            Integration integration = getUserIntegration(user.getId(), provider, true);
            IntegrationSettings settings = integration.getSettings();

            String url = settings.getRevokeUrl();
            if (url.equalsIgnoreCase("null") || url.equalsIgnoreCase("none")) return true;

            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("client_id", settings.getClientId());
            requestBody.add("client_secret", settings.getClientSecret());
            requestBody.add("token", integration.getAuth().getAccessToken());

            System.out.println(requestBody);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, request, JsonNode.class);

            return response.getStatusCode().is2xxSuccessful();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ObjectMapper getObjectMapper(final String provider) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        IntegrationAuthDeserializer deserializer = new IntegrationAuthDeserializer();
        deserializer.setProvider(provider);
        module.addDeserializer(IntegrationAuth.class, deserializer);
        mapper.registerModule(module);
        return mapper;
    }

    public Map<String, IntegrationSettings> validateAndReturnUserIntegrations(final Long userId) {
        Map<String, Integration> userIntegrations = getUserIntegrations(userId, true);

        return userIntegrations
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getSettings()));
    }

    public void validate(Long userId, Integration integration) {
        IntegrationAuth auth = integration.getAuth();
        if (auth == null) {
            integration.getSettings().setValid(false);
            return;
        }
        integration.getSettings().setValid(false);

        int missingScopes = 0;
        for (String scope : integration.getSettings().getScopes().split(" ")) {
            if (!Arrays.asList(auth.getScope().split(" ")).contains(scope)) {
                missingScopes++;
            }
        }
        if (missingScopes != 0) {
            unbind(integration.getAuth().getProvider(), userId);
        }
        else {
            if (LocalDateTime.now().isAfter(auth.getExpiresAt().minusMinutes(5))) {
                IntegrationAuth refreshedAuth = refreshToken(userId, integration.getSettings(), auth);
                integration.setAuth(refreshedAuth);
                integration.getSettings().setValid(refreshedAuth != null && LocalDateTime.now().isBefore(refreshedAuth.getExpiresAt().minusMinutes(5)));
            }
            else integration.getSettings().setValid(true);
        }
    }

    @Scheduled(initialDelay = 10_000, fixedDelay = 30_000)
    private void deleteUnused() {
        int del = integrationRepository.deleteUnused();
        if (del > 0) {
            log.info("deleted {} unused authentication tokens", del);
        }
    }

    @Scheduled(initialDelay = 10_000, fixedDelay = 60_000 * 60)
    private void autoRefreshTokensBeforeExpiration() {
        log.info("Check for refreshable tokens");
        int refreshedTokens = 0;
        Map<Long, List<IntegrationAuth>> authMap = integrationRepository.getAllIntegrations();
        for (Map.Entry<Long, List<IntegrationAuth>> authMapEntry : authMap.entrySet()) {
            Long userId = authMapEntry.getKey();
            List<IntegrationAuth> authList = authMapEntry.getValue();
            for (IntegrationAuth auth : authList) {
                for (Map.Entry<String, IntegrationSettings> entry : integrationSettings.entrySet()) {
                    if (auth.getProvider().equalsIgnoreCase(entry.getKey())) {
                        if (LocalDateTime.now().isAfter(auth.getExpiresAt().minusMinutes(90))) {
                            refreshedTokens++;
                            refreshToken(userId, entry.getValue(), auth);
                        }
                    }
                }
            }
        }
        if (refreshedTokens != 0) {
            log.info("Refreshed " + refreshedTokens + " tokens");
        }        
    }
}
