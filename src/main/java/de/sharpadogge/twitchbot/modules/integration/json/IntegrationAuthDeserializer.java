package de.sharpadogge.twitchbot.modules.integration.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.sharpadogge.twitchbot.modules.integration.entity.IntegrationAuth;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class IntegrationAuthDeserializer extends StdDeserializer<IntegrationAuth> {
    
    private static final long serialVersionUID = 865162827644569413L;

    private String provider;

    public IntegrationAuthDeserializer() {
        this(null);
    }

    protected IntegrationAuthDeserializer(Class<?> vc) {
        super(vc);
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public IntegrationAuth deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode json = jsonParser.getCodec().readTree(jsonParser);

        String accessToken = json.has("access_token") ? json.get("access_token").asText() : null;
        String refreshToken = json.has("refresh_token") ? json.get("refresh_token").asText() : null;
        String tokenType = json.has("token_type") ? json.get("token_type").asText() : null;
        String scope = json.has("scope") ? json.get("scope").asText() : "";
        Long expiresIn = json.has("expires_in") ? json.get("expires_in").asLong() : null;
        Long expiresAt = json.has("expires_at") ? json.get("expires_at").asLong() : null;
        if (expiresIn != null) {
            expiresAt = System.currentTimeMillis() + (expiresIn * 1000);
        }

        assert provider != null;
        assert expiresAt != null;
        return new IntegrationAuth(
                null,
                provider,
                accessToken,
                refreshToken,
                LocalDateTime.ofInstant(Instant.ofEpochMilli(expiresAt), TimeZone.getDefault().toZoneId()),
                expiresIn,
                scope,
                tokenType
        );
    }
}
