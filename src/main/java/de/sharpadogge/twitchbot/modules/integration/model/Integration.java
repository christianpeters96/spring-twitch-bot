package de.sharpadogge.twitchbot.modules.integration.model;

import de.sharpadogge.twitchbot.modules.integration.entity.IntegrationAuth;

public class Integration {

    private final IntegrationSettings settings;

    private IntegrationAuth auth;

    public Integration(IntegrationSettings settings) {
        this.settings = settings;
    }

    public Integration(IntegrationSettings settings, IntegrationAuth auth) {
        this.settings = settings;
        this.auth = auth;
    }

    public IntegrationSettings getSettings() {
        return settings;
    }

    public IntegrationAuth getAuth() {
        return auth;
    }

    public void setAuth(IntegrationAuth auth) {
        this.auth = auth;
    }
}
