package de.sharpadogge.twitchbot.modules.bot;

public enum PublicBots {
    JUERGEN("der_kleine_juergen", "oauth:w13ud13j7xkmntgt31xnf2wvgpxa1y"),
    SHARPA_BOT("der_kleine_juergen", "oauth:e36046zkuk6o91m0f88abo3i7jn7c6"),
    HERR_TIM("HerrTimTV", "oauth:k67wzpbmiwhfehavk9i8egzfnosfd0");

    private final String name;

    private final String auth;

    PublicBots(String name, String auth) {
        this.name = name;
        this.auth = auth;
    }

    public String getName() {
        return name;
    }

    public String getAuth() {
        return auth;
    }
}
