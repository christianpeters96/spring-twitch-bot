package de.sharpadogge.twitchbot.modules.bot;

import de.sharpadogge.twitchbot.modules.user.User;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

public abstract class AbstractCommand {
    public final static String prefix = "!";
    public abstract boolean execute(final User user, final ChannelMessageEvent event, final String commander, final String ... args);
}
