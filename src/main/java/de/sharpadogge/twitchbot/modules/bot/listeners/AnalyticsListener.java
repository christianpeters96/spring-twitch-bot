package de.sharpadogge.twitchbot.modules.bot.listeners;

import java.time.LocalDate;
import java.util.*;

import de.sharpadogge.twitchbot.modules.bot.AbstractCommand;
import de.sharpadogge.twitchbot.modules.bot.data.entity.CommandStatsDaily;
import de.sharpadogge.twitchbot.modules.bot.utils.MessageUtils;
import de.sharpadogge.twitchbot.modules.command.entity.UserCommand;
import org.kitteh.irc.client.library.element.User;
import org.kitteh.irc.client.library.event.channel.ChannelJoinEvent;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.client.library.event.channel.ChannelPartEvent;

import de.sharpadogge.twitchbot.modules.bot.EventListener;
import de.sharpadogge.twitchbot.modules.bot.data.TwitchBotDataHolder;
import de.sharpadogge.twitchbot.modules.bot.data.entity.UserStatsDaily;

public class AnalyticsListener extends EventListener {

    private final List<String> blacklistedUsers = Arrays.asList(
            "HT1997s_musikbot",
            "sharpa_bot",
            "der_kleine_juergen",
            "nightbot",
            "anotherttvviewers",
            "srizbot"
    );

    @Override
    public void onMessage(ChannelMessageEvent event) {
        final String author = event.getActor().getNick();
        final String message = event.getMessage();
        if (blacklistedUsers.contains(author)) return;

        final String channelName = event.getChannel().getName();

        TwitchBotDataHolder.addUserStatsEntryIfNotExists(channelName, author);

        UserStatsDaily stats = TwitchBotDataHolder.userStats.get(channelName).get(author);
        stats.setDate(LocalDate.now());

        UserCommand command = MessageUtils.getCommand(getClient().getUser().getId(), message);
        if (command != null) {
            String cmd = command.getCmd().substring(AbstractCommand.prefix.length()).split(" ")[0];
            stats.setCommandCount(stats.getCommandCount() + 1);

            if (!TwitchBotDataHolder.commandStats.containsKey(channelName)) {
                TwitchBotDataHolder.commandStats.put(channelName, new HashMap<>());
            }
            if (!TwitchBotDataHolder.commandStats.get(channelName).containsKey(cmd)) {
                CommandStatsDaily temp = new CommandStatsDaily();
                temp.setDate(LocalDate.now());
                temp.setChannel(channelName);
                temp.setCmd(cmd);
                TwitchBotDataHolder.commandStats.get(channelName).put(cmd, temp);
            }
            CommandStatsDaily csd = TwitchBotDataHolder.commandStats.get(channelName).get(cmd);
            TwitchBotDataHolder.commandStats.get(channelName).get(cmd).setCallCount(csd.getCallCount() + 1);
        }

        if (!event.getMessage().startsWith("!")) {
            stats.setMessageCount(stats.getMessageCount() + 1);
        }
    }

    @Override
    public void onJoin(ChannelJoinEvent event) {
        List<String> userList = new LinkedList<>();
        for (User user : event.getChannel().getUsers()) {
            userList.add(user.getNick());
        }
        if (!userList.contains(event.getActor().getNick())) {
            userList.add(event.getActor().getNick());
        }
        TwitchBotDataHolder.activeUsers.put(getClient().getChannel(), userList);
    }

    @Override
    public void onPart(ChannelPartEvent event) {
        List<String> userList = new LinkedList<>();
        for (User user : event.getChannel().getUsers()) {
            if (!user.getNick().equals(event.getActor().getNick()))
                userList.add(user.getNick());
        }
        TwitchBotDataHolder.activeUsers.put(getClient().getChannel(), userList);
    }
}