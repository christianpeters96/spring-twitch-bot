package de.sharpadogge.twitchbot.modules.twitch;

import org.kitteh.irc.client.library.feature.twitch.messagetag.Badges;

import java.util.LinkedList;
import java.util.List;

public enum Roles {
    BROADCASTER("Broadcaster", "role:broadcaster", Badges.KnownNames.BROADCASTER),
    MODERATOR("Moderator", "role:moderator", Badges.KnownNames.MODERATOR),
    SUBSCRIBER("Subscriber", "role:subscriber", Badges.KnownNames.SUBSCRIBER, true),
    VIP("VIP", "role:vip", "vip", true),
    REGULAR("Regular", "role:regular", null, true);

    private final String name;

    private final String query;

    private final String bagdeName;

    private final Boolean antiSpam;

    Roles(String name, String query, String bagdeName) {
        this.name = name;
        this.query = query;
        this.bagdeName = bagdeName;
        this.antiSpam = false;
    }

    Roles(String name, String query, String bagdeName, Boolean antiSpam) {
        this.name = name;
        this.query = query;
        this.bagdeName = bagdeName;
        this.antiSpam = antiSpam;
    }

    public String getName() {
        return name;
    }

    public String getQuery() {
        return query;
    }

    public String getBagdeName() {
        return bagdeName;
    }

    public static List<String> webList() {
        List<String> output = new LinkedList<>();
        for (Roles role : Roles.values()) {
            output.add(role.name+":"+role.query.replace("role:", ""));
        }
        return output;
    }

    public static List<String> spamWebList() {
        List<String> output = new LinkedList<>();
        for (Roles role : Roles.values()) {
            if (role.antiSpam) {
                output.add(role.name+":"+role.query.replace("role:", ""));
            }
        }
        return output;
    }

    public static Roles byQuery(final String query) {
        for (Roles role : values()) {
            if (role.query.toLowerCase().equals(query.toLowerCase())) {
                return role;
            }
        }
        return null;
    }
}
