package de.sharpadogge.twitchbot.modules.bot.data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import de.sharpadogge.twitchbot.modules.bot.data.entity.CommandStatsDaily;
import de.sharpadogge.twitchbot.modules.bot.data.entity.UserStatsOverall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import de.sharpadogge.twitchbot.modules.bot.data.entity.UserStatsDaily;

@Service
public class TwitchBotDataService {

    //private final Logger log = LoggerFactory.getLogger(getClass());

    private final TwitchBotDataRepository twitchBotDataRepository;

    @Autowired
    public TwitchBotDataService(TwitchBotDataRepository TwitchBotDataRepository) {
        this.twitchBotDataRepository = TwitchBotDataRepository;
    }

    @Scheduled(fixedDelay = 5_000)
    public void updateUserStats() {
        // 50 points per hour
        float activityPoints = 0.069444f;
        int activitySeconds = 5;

        if (TwitchBotDataHolder.activeUsers.size() != 0) {
            for (String channel : TwitchBotDataHolder.activeUsers.keySet()) {
                List<String> userList = TwitchBotDataHolder.activeUsers.get(channel);
                if (!TwitchBotDataHolder.userStats.containsKey(channel)) {
                    TwitchBotDataHolder.userStats.put(channel, new HashMap<>());
                }
                for (String user : userList) {
                    if (!TwitchBotDataHolder.userStats.get(channel).containsKey(user)) {
                        UserStatsDaily temp = new UserStatsDaily();
                        temp.setDate(LocalDate.now());
                        temp.setChannel(channel);
                        temp.setUsername(user);
                        TwitchBotDataHolder.userStats.get(channel).put(user, temp);
                    }
                    TwitchBotDataHolder.userStats.get(channel).get(user).setActivityTime((long) activitySeconds);
                    float points = TwitchBotDataHolder.userStats.get(channel).get(user).getActivityPoints();
                    TwitchBotDataHolder.userStats.get(channel).get(user).setActivityPoints(points + activityPoints);
                }
            }
        }

        // final loop (updates everything)
        if (TwitchBotDataHolder.userStats.size() != 0) {
            for (String channel : TwitchBotDataHolder.userStats.keySet()) {
                if (TwitchBotDataHolder.userStats.get(channel).size() != 0) {
                    for (String user : TwitchBotDataHolder.userStats.get(channel).keySet()) {
                        UserStatsDaily stats = TwitchBotDataHolder.userStats.get(channel).get(user);
                        stats.setChannel(channel);
                        stats.setUsername(user);
                        twitchBotDataRepository.addOrUpdateUserDailyStats(stats);
                    }
                }
            }
            TwitchBotDataHolder.userStats.clear();
        }
        if (TwitchBotDataHolder.commandStats.size() != 0) {
            for (String channel : TwitchBotDataHolder.commandStats.keySet()) {
                if (TwitchBotDataHolder.commandStats.get(channel).size() != 0) {
                    for (String cmd : TwitchBotDataHolder.commandStats.get(channel).keySet()) {
                        CommandStatsDaily stats =TwitchBotDataHolder.commandStats.get(channel).get(cmd);
                        twitchBotDataRepository.addOrUpdateCommandDailyStats(stats);
                    }
                }
            }
            TwitchBotDataHolder.commandStats.clear();
        }
    }

    public UserStatsOverall getUserStats(final String channel, final String username) {
        return twitchBotDataRepository.getUserStatsByName(channel, username)
                .orElseGet(() -> new UserStatsOverall(
                        0L, channel, username,
                        0L,
                        0L,
                        0L,
                        0L,
                        0L,
                        0L,
                        .0f
                ));
    }

    public void addActivityPoints(final String channel, final String username, final float activityPoints) {
        TwitchBotDataHolder.addUserStatsEntryIfNotExists(channel, username);
        float points = TwitchBotDataHolder.userStats.get(channel).get(username).getActivityPoints();
        TwitchBotDataHolder.userStats.get(channel).get(username).setActivityPoints(points + activityPoints);
        updateUserStats();
    }
}