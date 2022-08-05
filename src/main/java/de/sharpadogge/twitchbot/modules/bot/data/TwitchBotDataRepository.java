package de.sharpadogge.twitchbot.modules.bot.data;

import java.time.LocalDate;
import java.util.*;

import de.sharpadogge.twitchbot.modules.bot.data.entity.CommandStatsDaily;
import de.sharpadogge.twitchbot.modules.bot.data.entity.UserStatsOverall;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.sharpadogge.twitchbot.jooq.Tables;
import de.sharpadogge.twitchbot.modules.bot.data.entity.UserStatsDaily;

@Repository
public class TwitchBotDataRepository {
    
    private final DSLContext create;

    @Autowired
    public TwitchBotDataRepository(DSLContext create) {
        this.create = create;
    }

    public void addOrUpdateUserDailyStats(final UserStatsDaily stats) {
        final boolean dailyExists = create
                .selectCount().from(Tables.TWITCH_USER_STATS_DAILY)
                .where(Tables.TWITCH_USER_STATS_DAILY.CHANNEL.eq(stats.getChannel()))
                .and(Tables.TWITCH_USER_STATS_DAILY.USERNAME.eq(stats.getUsername()))
                .and(Tables.TWITCH_USER_STATS_DAILY.DATE.eq(stats.getDate()))
                .fetchOneInto(Integer.class) != 0;

        final boolean overallExists = create
                .selectCount().from(Tables.TWITCH_USER_STATS_OVERALL)
                .where(Tables.TWITCH_USER_STATS_OVERALL.CHANNEL.eq(stats.getChannel()))
                .and(Tables.TWITCH_USER_STATS_OVERALL.USERNAME.eq(stats.getUsername()))
                .fetchOneInto(Integer.class) != 0;

        if (!dailyExists) {
            create.newRecord(Tables.TWITCH_USER_STATS_DAILY, stats).insert();
        }
        else {
            create.update(Tables.TWITCH_USER_STATS_DAILY)
                    .set(Tables.TWITCH_USER_STATS_DAILY.MESSAGE_COUNT, Tables.TWITCH_USER_STATS_DAILY.MESSAGE_COUNT.plus(stats.getMessageCount()))
                    .set(Tables.TWITCH_USER_STATS_DAILY.COMMAND_COUNT, Tables.TWITCH_USER_STATS_DAILY.COMMAND_COUNT.plus(stats.getCommandCount()))
                    .set(Tables.TWITCH_USER_STATS_DAILY.TIMEOUT_COUNT, Tables.TWITCH_USER_STATS_DAILY.TIMEOUT_COUNT.plus(stats.getTimeoutCount()))
                    .set(Tables.TWITCH_USER_STATS_DAILY.TIMEOUT_MAXTIME, Tables.TWITCH_USER_STATS_DAILY.TIMEOUT_MAXTIME.plus(stats.getTimeoutMaxtime()))
                    .set(Tables.TWITCH_USER_STATS_DAILY.DELETED_MESSAGE_COUNT, Tables.TWITCH_USER_STATS_DAILY.DELETED_MESSAGE_COUNT.plus(stats.getDeletedMessageCount()))
                    .set(Tables.TWITCH_USER_STATS_DAILY.ACTIVITY_TIME, Tables.TWITCH_USER_STATS_DAILY.ACTIVITY_TIME.plus(stats.getActivityTime()))
                    .set(Tables.TWITCH_USER_STATS_DAILY.ACTIVITY_POINTS, Tables.TWITCH_USER_STATS_DAILY.ACTIVITY_POINTS.plus(stats.getActivityPoints()))
                    .where(Tables.TWITCH_USER_STATS_DAILY.CHANNEL.eq(stats.getChannel()))
                    .and(Tables.TWITCH_USER_STATS_DAILY.USERNAME.eq(stats.getUsername()))
                    .and(Tables.TWITCH_USER_STATS_DAILY.DATE.eq(stats.getDate()))
                    .execute();
        }

        if (!overallExists) {
            create.newRecord(Tables.TWITCH_USER_STATS_OVERALL, stats.toOverall()).insert();
        }
        else {
            create.update(Tables.TWITCH_USER_STATS_OVERALL)
                    .set(Tables.TWITCH_USER_STATS_OVERALL.MESSAGE_COUNT, Tables.TWITCH_USER_STATS_OVERALL.MESSAGE_COUNT.plus(stats.getMessageCount()))
                    .set(Tables.TWITCH_USER_STATS_OVERALL.COMMAND_COUNT, Tables.TWITCH_USER_STATS_OVERALL.COMMAND_COUNT.plus(stats.getCommandCount()))
                    .set(Tables.TWITCH_USER_STATS_OVERALL.TIMEOUT_COUNT, Tables.TWITCH_USER_STATS_OVERALL.TIMEOUT_COUNT.plus(stats.getTimeoutCount()))
                    .set(Tables.TWITCH_USER_STATS_OVERALL.TIMEOUT_MAXTIME, Tables.TWITCH_USER_STATS_OVERALL.TIMEOUT_MAXTIME.plus(stats.getTimeoutMaxtime()))
                    .set(Tables.TWITCH_USER_STATS_OVERALL.DELETED_MESSAGE_COUNT, Tables.TWITCH_USER_STATS_OVERALL.DELETED_MESSAGE_COUNT.plus(stats.getDeletedMessageCount()))
                    .set(Tables.TWITCH_USER_STATS_OVERALL.ACTIVITY_TIME, Tables.TWITCH_USER_STATS_OVERALL.ACTIVITY_TIME.plus(stats.getActivityTime()))
                    .set(Tables.TWITCH_USER_STATS_OVERALL.ACTIVITY_POINTS, Tables.TWITCH_USER_STATS_OVERALL.ACTIVITY_POINTS.plus(stats.getActivityPoints()))
                    .where(Tables.TWITCH_USER_STATS_OVERALL.CHANNEL.eq(stats.getChannel()))
                    .and(Tables.TWITCH_USER_STATS_OVERALL.USERNAME.eq(stats.getUsername()))
                    .execute();
        }
    }

    public void addOrUpdateCommandDailyStats(final CommandStatsDaily stats) {
        final boolean dailyExists = create
                .selectCount().from(Tables.TWITCH_COMMAND_STATS_DAILY)
                .where(Tables.TWITCH_COMMAND_STATS_DAILY.CHANNEL.eq(stats.getChannel()))
                .and(Tables.TWITCH_COMMAND_STATS_DAILY.CMD.eq(stats.getCmd()))
                .and(Tables.TWITCH_COMMAND_STATS_DAILY.DATE.eq(stats.getDate()))
                .fetchOneInto(Integer.class) != 0;

        final boolean overallExists = create
                .selectCount().from(Tables.TWITCH_COMMAND_STATS_OVERALL)
                .where(Tables.TWITCH_COMMAND_STATS_OVERALL.CHANNEL.eq(stats.getChannel()))
                .and(Tables.TWITCH_COMMAND_STATS_OVERALL.CMD.eq(stats.getCmd()))
                .fetchOneInto(Integer.class) != 0;

        if (!dailyExists) {
            create.newRecord(Tables.TWITCH_COMMAND_STATS_DAILY, stats).insert();
        }
        else {
            create.update(Tables.TWITCH_COMMAND_STATS_DAILY)
                    .set(Tables.TWITCH_COMMAND_STATS_DAILY.CALL_COUNT, Tables.TWITCH_COMMAND_STATS_DAILY.CALL_COUNT.plus(stats.getCallCount()))
                    .where(Tables.TWITCH_COMMAND_STATS_DAILY.CHANNEL.eq(stats.getChannel()))
                    .and(Tables.TWITCH_COMMAND_STATS_DAILY.CMD.eq(stats.getCmd()))
                    .and(Tables.TWITCH_COMMAND_STATS_DAILY.DATE.eq(stats.getDate()))
                    .execute();
        }

        if (!overallExists) {
            create.newRecord(Tables.TWITCH_COMMAND_STATS_OVERALL, stats.toOverall()).insert();
        }
        else {
            create.update(Tables.TWITCH_COMMAND_STATS_OVERALL)
                    .set(Tables.TWITCH_COMMAND_STATS_OVERALL.CALL_COUNT, Tables.TWITCH_COMMAND_STATS_OVERALL.CALL_COUNT.plus(stats.getCallCount()))
                    .where(Tables.TWITCH_COMMAND_STATS_OVERALL.CHANNEL.eq(stats.getChannel()))
                    .and(Tables.TWITCH_COMMAND_STATS_OVERALL.CMD.eq(stats.getCmd()))
                    .execute();
        }
    }

    public List<UserStatsDaily> getWeeklyStats(final String channel) {
        List<UserStatsDaily> output = create
            .select(
                Tables.TWITCH_USER_STATS_DAILY.DATE,
                DSL.sum(Tables.TWITCH_USER_STATS_DAILY.MESSAGE_COUNT).as("message_count"),
                DSL.sum(Tables.TWITCH_USER_STATS_DAILY.COMMAND_COUNT).as("command_count"),
                DSL.sum(Tables.TWITCH_USER_STATS_DAILY.TIMEOUT_COUNT).as("timeout_count"),
                DSL.sum(Tables.TWITCH_USER_STATS_DAILY.DELETED_MESSAGE_COUNT).as("deleted_message_count")
            )
            .from(Tables.TWITCH_USER_STATS_DAILY)
            .where(Tables.TWITCH_USER_STATS_DAILY.CHANNEL.eq(channel))
            .groupBy(Tables.TWITCH_USER_STATS_DAILY.DATE)
            .orderBy(Tables.TWITCH_USER_STATS_DAILY.DATE.desc())
            .limit(7)
            .fetchInto(UserStatsDaily.class);
        //List<UserStatsDaily> result = new ArrayList<>();

        // per day
//        for (int i = 0; i < 7; i++) {
//            LocalDate date = LocalDate.now().minusDays(i);
//            boolean exists = false;
//            UserStatsDaily data = null;
//            for (UserStatsDaily stats : output) {
//                if(stats.getDate().equals(date)) {
//                    exists = true;
//                    data = stats;
//                    break;
//                }
//            }
//            if (exists) {
//                result.add(data);
//            }
//            else {
//                UserStatsDaily stats = new UserStatsDaily();
//                stats.setDate(date);
//                stats.setMessageCount(0L);
//                stats.setCommandCount(0L);
//                stats.setTimeoutCount(0L);
//                stats.setTimeoutMaxtime(0L);
//                result.add(stats);
//            }
//        }

        // per stream
        if (output.size() != 0) {
            while (output.size() < 7) {
                LocalDate date = output.get(output.size() - 1).getDate().minusDays(1);
                UserStatsDaily stats = new UserStatsDaily();
                stats.setDate(date);
                stats.setMessageCount(0L);
                stats.setCommandCount(0L);
                stats.setTimeoutCount(0L);
                stats.setTimeoutMaxtime(0L);
                output.add(stats);
            }
        }
        else {
            for (int i = 0; i < 7; i++) {
                LocalDate date = LocalDate.now().minusDays(i);
                UserStatsDaily stats = new UserStatsDaily();
                stats.setDate(date);
                stats.setMessageCount(0L);
                stats.setCommandCount(0L);
                stats.setTimeoutCount(0L);
                stats.setTimeoutMaxtime(0L);
                output.add(stats);
            }
        }

//        for (int i = 0; i < 7; i++) {
//            LocalDate date = LocalDate.now().minusDays(i);
//            boolean exists = false;
//            for (UserStatsDaily stats : output) {
//                if(stats.getDate().equals(date)) {
//                    exists = true;
//                    break;
//                }
//            }
//            if (!exists) {
//                UserStatsDaily stats = new UserStatsDaily();
//                stats.setDate(date);
//                stats.setMessageCount(0L);
//                stats.setCommandCount(0L);
//                stats.setTimeoutCount(0L);
//                stats.setTimeoutMaxtime(0L);
//                output.add(stats);
//            }
//        }

//        output.sort(new Comparator<UserStatsDaily>() {
//            @Override
//            public int compare(UserStatsDaily o1, UserStatsDaily o2) {
//                return o2.getDate().compareTo(o1.getDate());
//            }
//        });

        // if (output.size() < 7) {
        //     LocalDate last = output.get(output.size() - 1).getDate();
        //     for (int i = 0; output.size() < 7; i++) {
        //         LocalDate date = last.minusDays(i);
    
        //         UserStatsDaily stats = new UserStatsDaily();
        //         stats.setDate(date);
        //         stats.setMessageCount(0L);
        //         stats.setCommandCount(0L);
        //         stats.setTimeoutCount(0L);
        //         stats.setTimeoutMaxtime(0L);
        //         output.add(stats);
        //     }
        // }

        return output;
    }

    public Map<String, Object> getTopStats(final String channel) {
        Map<String, Object> result = new HashMap<>();
        result.put("users", getTopUserStats(channel));
        result.put("commands", getTopCommandStats(channel));
        return result;
    }

    public List<UserStatsDaily> getTopUserStats(final String channel) {
        List<LocalDate> dates = create
                .select(Tables.TWITCH_USER_STATS_DAILY.DATE)
                .from(Tables.TWITCH_USER_STATS_DAILY)
                .where(Tables.TWITCH_USER_STATS_DAILY.CHANNEL.eq(channel))
                .groupBy(Tables.TWITCH_USER_STATS_DAILY.DATE)
                .orderBy(Tables.TWITCH_USER_STATS_DAILY.DATE.desc())
                .limit(7)
                .fetchInto(LocalDate.class);

        return create
                .select(
                        Tables.TWITCH_USER_STATS_DAILY.USERNAME,
                        DSL.sum(Tables.TWITCH_USER_STATS_DAILY.MESSAGE_COUNT).as("message_count")
                )
                .from(Tables.TWITCH_USER_STATS_DAILY)
                .where(Tables.TWITCH_USER_STATS_DAILY.CHANNEL.eq(channel))
                .and(Tables.TWITCH_USER_STATS_DAILY.DATE.in(dates))
                .groupBy(Tables.TWITCH_USER_STATS_DAILY.USERNAME)
                .orderBy(DSL.field("message_count").desc())
                .fetchInto(UserStatsDaily.class);
    }

    public List<CommandStatsDaily> getTopCommandStats(final String channel) {
        List<LocalDate> dates = create
                .select(Tables.TWITCH_COMMAND_STATS_DAILY.DATE)
                .from(Tables.TWITCH_COMMAND_STATS_DAILY)
                .where(Tables.TWITCH_COMMAND_STATS_DAILY.CHANNEL.eq(channel))
                .groupBy(Tables.TWITCH_COMMAND_STATS_DAILY.DATE)
                .orderBy(Tables.TWITCH_COMMAND_STATS_DAILY.DATE.desc())
                .limit(7)
                .fetchInto(LocalDate.class);

        return create
                .select(
                        Tables.TWITCH_COMMAND_STATS_DAILY.CMD,
                        DSL.sum(Tables.TWITCH_COMMAND_STATS_DAILY.CALL_COUNT).as("call_count")
                )
                .from(Tables.TWITCH_COMMAND_STATS_DAILY)
                .where(Tables.TWITCH_COMMAND_STATS_DAILY.CHANNEL.eq(channel))
                .and(Tables.TWITCH_COMMAND_STATS_DAILY.DATE.in(dates))
                .groupBy(Tables.TWITCH_COMMAND_STATS_DAILY.CMD)
                .orderBy(DSL.field("call_count").desc())
                .fetchInto(CommandStatsDaily.class);
    }

    public Optional<UserStatsOverall> getUserStatsByName(final String channel, final String username) {
        return create
                .selectFrom(Tables.TWITCH_USER_STATS_OVERALL)
                .where(Tables.TWITCH_USER_STATS_OVERALL.CHANNEL.eq(channel))
                .and(Tables.TWITCH_USER_STATS_OVERALL.USERNAME.eq(username))
                .fetchOptionalInto(UserStatsOverall.class);
    }
}
