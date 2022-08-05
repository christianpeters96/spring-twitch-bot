package de.sharpadogge.twitchbot.modules.bot.listeners;

import de.sharpadogge.twitchbot.modules.bot.EventListener;
import de.sharpadogge.twitchbot.modules.bot.data.TwitchBotDataHolder;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

import java.util.ArrayList;

public class ChatListener extends EventListener {

    @Override
    public void onMessage(ChannelMessageEvent event) {
        final String channelName = event.getChannel().getName();

        if (!TwitchBotDataHolder.latestMessages.containsKey(channelName)) {
            TwitchBotDataHolder.latestMessages.put(channelName, new ArrayList<>());
        }
        TwitchBotDataHolder.latestMessages.get(channelName).add(event);
        while (TwitchBotDataHolder.latestMessages.get(channelName).size() > 25) {
            TwitchBotDataHolder.latestMessages.get(channelName).remove(0);
        }
    }
}
