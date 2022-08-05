package de.sharpadogge.twitchbot.modules.integration;

public enum AvailableIntegration {
    TWITCH("twitch", null, null, false),
    SPOTIFY("spotify", null, "img/spotify-logo.svg"),
    DISCORD("discord", null, "img/discord-logo.svg");

    private final String key;

    private final String mdi;

    private final String image;

    // if this is set to 'true' you can see this connection in /user/settings/auth
    private final Boolean frontend;

    AvailableIntegration(String key, String mdi, String image) {
        this.key = key;
        this.mdi = mdi;
        this.image = image;
        this.frontend = true;
    }

    AvailableIntegration(String key, String mdi, String image, Boolean frontend) {
        this.key = key;
        this.mdi = mdi;
        this.image = image;
        this.frontend = frontend;
    }

    public String getKey() {
        return key;
    }

    public String getMdi() {
        return mdi;
    }

    public String getImage() {
        return image;
    }

    public Boolean getFrontend() {
        return frontend;
    }

    public static AvailableIntegration byKey(String key) {
        for (AvailableIntegration integration : values()) {
            if (integration.key.equalsIgnoreCase(key)) {
                return integration;
            }
        }
        return null;
    }
}
