package de.sharpadogge.twitchbot.modules.bot.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.sharpadogge.twitchbot.modules.bot.data.entity.CommandStatsDaily;
import de.sharpadogge.twitchbot.modules.bot.data.entity.UserStatsDaily;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

public class TwitchBotDataHolder {

    public static Map<String, Boolean> streamOnline = new HashMap<>();
    public static Map<String, List<ChannelMessageEvent>> latestMessages = new HashMap<>();
    public static Map<String, Map<String, UserStatsDaily>> userStats = new HashMap<>();
    public static Map<String, Map<String, CommandStatsDaily>> commandStats = new HashMap<>();
    public static Map<String, List<String>> activeUsers = new HashMap<>();

    public static void addUserStatsEntryIfNotExists(final String channel, final String user) {
        if (!TwitchBotDataHolder.userStats.containsKey(channel)) {
            TwitchBotDataHolder.userStats.put(channel, new HashMap<>());
        }
        if (!TwitchBotDataHolder.userStats.get(channel).containsKey(user)) {
            UserStatsDaily temp = new UserStatsDaily();
            temp.setDate(LocalDate.now());
            temp.setChannel(channel);
            temp.setUsername(user);
            TwitchBotDataHolder.userStats.get(channel).put(user, temp);
        }
    }

    public static void deleteMessagesFrom(final String channel, final String nick) {
        List<ChannelMessageEvent> newList = new ArrayList<>();
        for (ChannelMessageEvent event : TwitchBotDataHolder.latestMessages.get(channel)) {
            if (event.getActor().getNick().equals(nick)) {
                continue;
            }
            newList.add(event);
        }
        TwitchBotDataHolder.latestMessages.get(channel).clear();
        TwitchBotDataHolder.latestMessages.get(channel).addAll(newList);
    }
}

