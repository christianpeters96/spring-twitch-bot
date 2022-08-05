package de.sharpadogge.twitchbot.modules.integration;

import de.sharpadogge.twitchbot.jooq.Tables;
import de.sharpadogge.twitchbot.modules.integration.entity.IntegrationAuth;
import de.sharpadogge.twitchbot.modules.integration.entity.UserIntegration;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class IntegrationRepository {

    private final DSLContext create;

    @Autowired
    public IntegrationRepository(DSLContext create) {
        this.create = create;
    }

    public int insert(IntegrationAuth auth) {
        //noinspection SimplifyOptionalCallChains
        if (!findByAccessToken(auth.getAccessToken()).isPresent()) {
            return create.newRecord(Tables.INTEGRATION_AUTH, auth).insert();
        }
        return 0; // some services (like Discord) send the same access_token (if not revoked earlier)
    }

    public Optional<IntegrationAuth> findByAccessToken(String accessToken) {
        return create
                .selectFrom(Tables.INTEGRATION_AUTH)
                .where(Tables.INTEGRATION_AUTH.ACCESS_TOKEN.eq(accessToken))
                .fetchOptionalInto(IntegrationAuth.class);
    }

    public int bind(Long authId, Long userId) {
        return create.newRecord(Tables.USER_INTEGRATION, new UserIntegration(userId, authId)).insert();
    }

    public int unbind(String provider, Long userId) {
        if (AvailableIntegration.byKey(provider) != null) {
            return create.query("delete ui from user_integration ui left join integration_auth ia on ui.integration_id = ia.id where ui.user_id = " + userId + " and ia.provider = '" + provider + "';").execute();
        }
        return 0;
    }

    public List<IntegrationAuth> getUserIntegrations(Long userId) {
        return create
                .select(Tables.INTEGRATION_AUTH.fields())
                .from(Tables.USER_INTEGRATION)
                .leftJoin(Tables.INTEGRATION_AUTH).on(Tables.INTEGRATION_AUTH.ID.eq(Tables.USER_INTEGRATION.INTEGRATION_ID))
                .where(Tables.USER_INTEGRATION.USER_ID.eq(userId))
                .fetchInto(IntegrationAuth.class);
    }

    public Map<Long, List<IntegrationAuth>> getAllIntegrations() {
        Map<Long, List<IntegrationAuth>> _map = new HashMap<>();
        List<Long> userIds = create.select(Tables.USERS.ID).from(Tables.USERS).fetchInto(Long.class);
        for (Long userId : userIds) {
            _map.put(userId, getUserIntegrations(userId));
        }
        return _map;
    }

    public int deleteUnused() {
        return create.query("delete ia from integration_auth ia left join user_integration ui on ia.id = ui.integration_id where ui.user_id is null;").execute();
    }
}
