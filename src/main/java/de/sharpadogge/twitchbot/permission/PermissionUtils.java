package de.sharpadogge.twitchbot.permission;

import de.sharpadogge.twitchbot.modules.twitch.Roles;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.client.library.feature.twitch.messagetag.Badges;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PermissionUtils {

    public static boolean checkCustomPermission(ChannelMessageEvent event, List<String> permissions) {
        Optional<Badges> badgesOpt = event.getSource().getTag("badges", Badges.class);
        List<String> badges = new LinkedList<>();
        boolean permitted = false;
        if (badgesOpt.isPresent()) {
            for (Badges.Badge badge : badgesOpt.get().getBadges()) {
                badges.add(badge.getName());
                if (badge.getName().equals(Badges.KnownNames.BROADCASTER)
                        || badge.getName().equals(Badges.KnownNames.ADMIN)
                        || badge.getName().equals(Badges.KnownNames.GLOBAL_MOD)
                        || badge.getName().equals(Badges.KnownNames.STAFF)
                        || badge.getName().equals(Badges.KnownNames.MODERATOR)) {
                    permitted = true;
                }
            }
        }
        if (permitted) return true;

        for (String permission : permissions) {
            if (permission.split(":").length == 2) {
                if (permission.indexOf("role:") == 0) {
                    Roles role = Roles.byQuery(permission);
                    if (role != null) {
                        if (role.getBagdeName() != null) {
                            if (badges.contains(role.getBagdeName())) {
                                permitted = true;
                            }
                        }
                    }
                }
                if (permission.indexOf("user:") == 0) {
                    final String searchUser = permission.split(":")[1];
                    if (event.getActor().getNick().toLowerCase().equals(searchUser.toLowerCase())) {
                        permitted = true;
                    }
                }
            }
        }
        return permitted;
    }
}
