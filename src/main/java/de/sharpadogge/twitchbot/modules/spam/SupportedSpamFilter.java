package de.sharpadogge.twitchbot.modules.spam;

public enum SupportedSpamFilter {
    BLACKLISTED_WORDS("words"),
    CAPS_LOCK("caps"),
    EMOTE_SPAM("emotes"),
    LINKS("links"),
    SPECIAL_CHARS("symbols");

    private final String identifier;

    SupportedSpamFilter(String identifier) {
        this.identifier = identifier;
    }

    public static SupportedSpamFilter byIdentifier(final String identifier) {
        for (SupportedSpamFilter filter : SupportedSpamFilter.values()) {
            if (filter.getIdentifier().equalsIgnoreCase(identifier)) {
                return filter;
            }
        }
        return null;
    }

    public String getIdentifier() {
        return identifier;
    }
}
