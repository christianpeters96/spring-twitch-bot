/*
 * This file is generated by jOOQ.
 */
package de.sharpadogge.twitchbot.jooq.tables.records;


import de.sharpadogge.twitchbot.jooq.tables.TwitchCommandStatsOverall;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TwitchCommandStatsOverallRecord extends UpdatableRecordImpl<TwitchCommandStatsOverallRecord> implements Record4<Long, String, String, Long> {

    private static final long serialVersionUID = -718971349;

    /**
     * Setter for <code>twitchbot.twitch_command_stats_overall.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>twitchbot.twitch_command_stats_overall.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>twitchbot.twitch_command_stats_overall.channel</code>.
     */
    public void setChannel(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>twitchbot.twitch_command_stats_overall.channel</code>.
     */
    public String getChannel() {
        return (String) get(1);
    }

    /**
     * Setter for <code>twitchbot.twitch_command_stats_overall.cmd</code>.
     */
    public void setCmd(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>twitchbot.twitch_command_stats_overall.cmd</code>.
     */
    public String getCmd() {
        return (String) get(2);
    }

    /**
     * Setter for <code>twitchbot.twitch_command_stats_overall.call_count</code>.
     */
    public void setCallCount(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>twitchbot.twitch_command_stats_overall.call_count</code>.
     */
    public Long getCallCount() {
        return (Long) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, String, String, Long> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Long, String, String, Long> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return TwitchCommandStatsOverall.TWITCH_COMMAND_STATS_OVERALL.ID;
    }

    @Override
    public Field<String> field2() {
        return TwitchCommandStatsOverall.TWITCH_COMMAND_STATS_OVERALL.CHANNEL;
    }

    @Override
    public Field<String> field3() {
        return TwitchCommandStatsOverall.TWITCH_COMMAND_STATS_OVERALL.CMD;
    }

    @Override
    public Field<Long> field4() {
        return TwitchCommandStatsOverall.TWITCH_COMMAND_STATS_OVERALL.CALL_COUNT;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getChannel();
    }

    @Override
    public String component3() {
        return getCmd();
    }

    @Override
    public Long component4() {
        return getCallCount();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getChannel();
    }

    @Override
    public String value3() {
        return getCmd();
    }

    @Override
    public Long value4() {
        return getCallCount();
    }

    @Override
    public TwitchCommandStatsOverallRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public TwitchCommandStatsOverallRecord value2(String value) {
        setChannel(value);
        return this;
    }

    @Override
    public TwitchCommandStatsOverallRecord value3(String value) {
        setCmd(value);
        return this;
    }

    @Override
    public TwitchCommandStatsOverallRecord value4(Long value) {
        setCallCount(value);
        return this;
    }

    @Override
    public TwitchCommandStatsOverallRecord values(Long value1, String value2, String value3, Long value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TwitchCommandStatsOverallRecord
     */
    public TwitchCommandStatsOverallRecord() {
        super(TwitchCommandStatsOverall.TWITCH_COMMAND_STATS_OVERALL);
    }

    /**
     * Create a detached, initialised TwitchCommandStatsOverallRecord
     */
    public TwitchCommandStatsOverallRecord(Long id, String channel, String cmd, Long callCount) {
        super(TwitchCommandStatsOverall.TWITCH_COMMAND_STATS_OVERALL);

        set(0, id);
        set(1, channel);
        set(2, cmd);
        set(3, callCount);
    }
}