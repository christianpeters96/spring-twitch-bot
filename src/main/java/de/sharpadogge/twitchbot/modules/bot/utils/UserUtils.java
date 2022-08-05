package de.sharpadogge.twitchbot.modules.bot.utils;

import de.sharpadogge.twitchbot.modules.bot.BotClient;
import de.sharpadogge.twitchbot.modules.bot.data.TwitchBotDataService;
import de.sharpadogge.twitchbot.modules.bot.data.entity.UserStatsOverall;

public class UserUtils {

    public static boolean canAfford(final String channel, final String nick, final Integer points) {
        final TwitchBotDataService dataService = BotClient.getContext().getBean(TwitchBotDataService.class);
        UserStatsOverall stats = dataService.getUserStats(channel, nick);
        return Integer.parseInt(stats.getActivityPoints().toString().replaceAll("\\.\\d+", "")) >= points;
    }

    public static int getActivityPoints(final String channel, final String nick) {
        final TwitchBotDataService dataService = BotClient.getContext().getBean(TwitchBotDataService.class);
        UserStatsOverall stats = dataService.getUserStats(channel, nick);
        return Integer.parseInt(stats.getActivityPoints().toString().replaceAll("\\.\\d+", ""));
    }
}
