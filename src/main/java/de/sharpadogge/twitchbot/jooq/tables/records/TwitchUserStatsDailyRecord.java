/*
 * This file is generated by jOOQ.
 */
package de.sharpadogge.twitchbot.jooq.tables.records;


import de.sharpadogge.twitchbot.jooq.tables.TwitchUserStatsDaily;

import java.time.LocalDate;

import org.jooq.Field;
import org.jooq.Record10;
import org.jooq.Record3;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TwitchUserStatsDailyRecord extends UpdatableRecordImpl<TwitchUserStatsDailyRecord> implements Record10<LocalDate, String, String, Long, Long, Long, Long, Long, Long, Double> {

    private static final long serialVersionUID = -503396772;

    /**
     * Setter for <code>twitchbot.twitch_user_stats_daily.date</code>.
     */
    public void setDate(LocalDate value) {
        set(0, value);
    }

    /**
     * Getter for <code>twitchbot.twitch_user_stats_daily.date</code>.
     */
    public LocalDate getDate() {
        return (LocalDate) get(0);
    }

    /**
     * Setter for <code>twitchbot.twitch_user_stats_daily.channel</code>.
     */
    public void setChannel(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>twitchbot.twitch_user_stats_daily.channel</code>.
     */
    public String getChannel() {
        return (String) get(1);
    }

    /**
     * Setter for <code>twitchbot.twitch_user_stats_daily.username</code>.
     */
    public void setUsername(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>twitchbot.twitch_user_stats_daily.username</code>.
     */
    public String getUsername() {
        return (String) get(2);
    }

    /**
     * Setter for <code>twitchbot.twitch_user_stats_daily.message_count</code>.
     */
    public void setMessageCount(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>twitchbot.twitch_user_stats_daily.message_count</code>.
     */
    public Long getMessageCount() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>twitchbot.twitch_user_stats_daily.command_count</code>.
     */
    public void setCommandCount(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>twitchbot.twitch_user_stats_daily.command_count</code>.
     */
    public Long getCommandCount() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>twitchbot.twitch_user_stats_daily.timeout_count</code>.
     */
    public void setTimeoutCount(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>twitchbot.twitch_user_stats_daily.timeout_count</code>.
     */
    public Long getTimeoutCount() {
        return (Long) get(5);
    }

    /**
     * Setter for <code>twitchbot.twitch_user_stats_daily.timeout_maxtime</code>.
     */
    public void setTimeoutMaxtime(Long value) {
        set(6, value);
    }

    /**
     * Getter for <code>twitchbot.twitch_user_stats_daily.timeout_maxtime</code>.
     */
    public Long getTimeoutMaxtime() {
        return (Long) get(6);
    }

    /**
     * Setter for <code>twitchbot.twitch_user_stats_daily.deleted_message_count</code>.
     */
    public void setDeletedMessageCount(Long value) {
        set(7, value);
    }

    /**
     * Getter for <code>twitchbot.twitch_user_stats_daily.deleted_message_count</code>.
     */
    public Long getDeletedMessageCount() {
        return (Long) get(7);
    }

    /**
     * Setter for <code>twitchbot.twitch_user_stats_daily.activity_time</code>. Seconds
     */
    public void setActivityTime(Long value) {
        set(8, value);
    }

    /**
     * Getter for <code>twitchbot.twitch_user_stats_daily.activity_time</code>. Seconds
     */
    public Long getActivityTime() {
        return (Long) get(8);
    }

    /**
     * Setter for <code>twitchbot.twitch_user_stats_daily.activity_points</code>.
     */
    public void setActivityPoints(Double value) {
        set(9, value);
    }

    /**
     * Getter for <code>twitchbot.twitch_user_stats_daily.activity_points</code>.
     */
    public Double getActivityPoints() {
        return (Double) get(9);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record3<LocalDate, String, String> key() {
        return (Record3) super.key();
    }

    // -------------------------------------------------------------------------
    // Record10 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row10<LocalDate, String, String, Long, Long, Long, Long, Long, Long, Double> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    @Override
    public Row10<LocalDate, String, String, Long, Long, Long, Long, Long, Long, Double> valuesRow() {
        return (Row10) super.valuesRow();
    }

    @Override
    public Field<LocalDate> field1() {
        return TwitchUserStatsDaily.TWITCH_USER_STATS_DAILY.DATE;
    }

    @Override
    public Field<String> field2() {
        return TwitchUserStatsDaily.TWITCH_USER_STATS_DAILY.CHANNEL;
    }

    @Override
    public Field<String> field3() {
        return TwitchUserStatsDaily.TWITCH_USER_STATS_DAILY.USERNAME;
    }

    @Override
    public Field<Long> field4() {
        return TwitchUserStatsDaily.TWITCH_USER_STATS_DAILY.MESSAGE_COUNT;
    }

    @Override
    public Field<Long> field5() {
        return TwitchUserStatsDaily.TWITCH_USER_STATS_DAILY.COMMAND_COUNT;
    }

    @Override
    public Field<Long> field6() {
        return TwitchUserStatsDaily.TWITCH_USER_STATS_DAILY.TIMEOUT_COUNT;
    }

    @Override
    public Field<Long> field7() {
        return TwitchUserStatsDaily.TWITCH_USER_STATS_DAILY.TIMEOUT_MAXTIME;
    }

    @Override
    public Field<Long> field8() {
        return TwitchUserStatsDaily.TWITCH_USER_STATS_DAILY.DELETED_MESSAGE_COUNT;
    }

    @Override
    public Field<Long> field9() {
        return TwitchUserStatsDaily.TWITCH_USER_STATS_DAILY.ACTIVITY_TIME;
    }

    @Override
    public Field<Double> field10() {
        return TwitchUserStatsDaily.TWITCH_USER_STATS_DAILY.ACTIVITY_POINTS;
    }

    @Override
    public LocalDate component1() {
        return getDate();
    }

    @Override
    public String component2() {
        return getChannel();
    }

    @Override
    public String component3() {
        return getUsername();
    }

    @Override
    public Long component4() {
        return getMessageCount();
    }

    @Override
    public Long component5() {
        return getCommandCount();
    }

    @Override
    public Long component6() {
        return getTimeoutCount();
    }

    @Override
    public Long component7() {
        return getTimeoutMaxtime();
    }

    @Override
    public Long component8() {
        return getDeletedMessageCount();
    }

    @Override
    public Long component9() {
        return getActivityTime();
    }

    @Override
    public Double component10() {
        return getActivityPoints();
    }

    @Override
    public LocalDate value1() {
        return getDate();
    }

    @Override
    public String value2() {
        return getChannel();
    }

    @Override
    public String value3() {
        return getUsername();
    }

    @Override
    public Long value4() {
        return getMessageCount();
    }

    @Override
    public Long value5() {
        return getCommandCount();
    }

    @Override
    public Long value6() {
        return getTimeoutCount();
    }

    @Override
    public Long value7() {
        return getTimeoutMaxtime();
    }

    @Override
    public Long value8() {
        return getDeletedMessageCount();
    }

    @Override
    public Long value9() {
        return getActivityTime();
    }

    @Override
    public Double value10() {
        return getActivityPoints();
    }

    @Override
    public TwitchUserStatsDailyRecord value1(LocalDate value) {
        setDate(value);
        return this;
    }

    @Override
    public TwitchUserStatsDailyRecord value2(String value) {
        setChannel(value);
        return this;
    }

    @Override
    public TwitchUserStatsDailyRecord value3(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public TwitchUserStatsDailyRecord value4(Long value) {
        setMessageCount(value);
        return this;
    }

    @Override
    public TwitchUserStatsDailyRecord value5(Long value) {
        setCommandCount(value);
        return this;
    }

    @Override
    public TwitchUserStatsDailyRecord value6(Long value) {
        setTimeoutCount(value);
        return this;
    }

    @Override
    public TwitchUserStatsDailyRecord value7(Long value) {
        setTimeoutMaxtime(value);
        return this;
    }

    @Override
    public TwitchUserStatsDailyRecord value8(Long value) {
        setDeletedMessageCount(value);
        return this;
    }

    @Override
    public TwitchUserStatsDailyRecord value9(Long value) {
        setActivityTime(value);
        return this;
    }

    @Override
    public TwitchUserStatsDailyRecord value10(Double value) {
        setActivityPoints(value);
        return this;
    }

    @Override
    public TwitchUserStatsDailyRecord values(LocalDate value1, String value2, String value3, Long value4, Long value5, Long value6, Long value7, Long value8, Long value9, Double value10) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TwitchUserStatsDailyRecord
     */
    public TwitchUserStatsDailyRecord() {
        super(TwitchUserStatsDaily.TWITCH_USER_STATS_DAILY);
    }

    /**
     * Create a detached, initialised TwitchUserStatsDailyRecord
     */
    public TwitchUserStatsDailyRecord(LocalDate date, String channel, String username, Long messageCount, Long commandCount, Long timeoutCount, Long timeoutMaxtime, Long deletedMessageCount, Long activityTime, Double activityPoints) {
        super(TwitchUserStatsDaily.TWITCH_USER_STATS_DAILY);

        set(0, date);
        set(1, channel);
        set(2, username);
        set(3, messageCount);
        set(4, commandCount);
        set(5, timeoutCount);
        set(6, timeoutMaxtime);
        set(7, deletedMessageCount);
        set(8, activityTime);
        set(9, activityPoints);
    }
}