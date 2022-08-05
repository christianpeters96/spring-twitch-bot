/*
 * This file is generated by jOOQ.
 */
package de.sharpadogge.twitchbot.jooq.tables;


import de.sharpadogge.twitchbot.jooq.Keys;
import de.sharpadogge.twitchbot.jooq.Twitchbot;
import de.sharpadogge.twitchbot.jooq.tables.records.TwitchUserStatsOverallRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row10;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TwitchUserStatsOverall extends TableImpl<TwitchUserStatsOverallRecord> {

    private static final long serialVersionUID = 1874310177;

    /**
     * The reference instance of <code>twitchbot.twitch_user_stats_overall</code>
     */
    public static final TwitchUserStatsOverall TWITCH_USER_STATS_OVERALL = new TwitchUserStatsOverall();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TwitchUserStatsOverallRecord> getRecordType() {
        return TwitchUserStatsOverallRecord.class;
    }

    /**
     * The column <code>twitchbot.twitch_user_stats_overall.id</code>.
     */
    public final TableField<TwitchUserStatsOverallRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>twitchbot.twitch_user_stats_overall.channel</code>.
     */
    public final TableField<TwitchUserStatsOverallRecord, String> CHANNEL = createField(DSL.name("channel"), org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>twitchbot.twitch_user_stats_overall.username</code>.
     */
    public final TableField<TwitchUserStatsOverallRecord, String> USERNAME = createField(DSL.name("username"), org.jooq.impl.SQLDataType.VARCHAR(80).nullable(false), this, "");

    /**
     * The column <code>twitchbot.twitch_user_stats_overall.message_count</code>.
     */
    public final TableField<TwitchUserStatsOverallRecord, Long> MESSAGE_COUNT = createField(DSL.name("message_count"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>twitchbot.twitch_user_stats_overall.command_count</code>.
     */
    public final TableField<TwitchUserStatsOverallRecord, Long> COMMAND_COUNT = createField(DSL.name("command_count"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>twitchbot.twitch_user_stats_overall.timeout_count</code>.
     */
    public final TableField<TwitchUserStatsOverallRecord, Long> TIMEOUT_COUNT = createField(DSL.name("timeout_count"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>twitchbot.twitch_user_stats_overall.timeout_maxtime</code>.
     */
    public final TableField<TwitchUserStatsOverallRecord, Long> TIMEOUT_MAXTIME = createField(DSL.name("timeout_maxtime"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>twitchbot.twitch_user_stats_overall.deleted_message_count</code>.
     */
    public final TableField<TwitchUserStatsOverallRecord, Long> DELETED_MESSAGE_COUNT = createField(DSL.name("deleted_message_count"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>twitchbot.twitch_user_stats_overall.activity_time</code>. Seconds
     */
    public final TableField<TwitchUserStatsOverallRecord, Long> ACTIVITY_TIME = createField(DSL.name("activity_time"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "Seconds");

    /**
     * The column <code>twitchbot.twitch_user_stats_overall.activity_points</code>.
     */
    public final TableField<TwitchUserStatsOverallRecord, Double> ACTIVITY_POINTS = createField(DSL.name("activity_points"), org.jooq.impl.SQLDataType.FLOAT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.FLOAT)), this, "");

    /**
     * Create a <code>twitchbot.twitch_user_stats_overall</code> table reference
     */
    public TwitchUserStatsOverall() {
        this(DSL.name("twitch_user_stats_overall"), null);
    }

    /**
     * Create an aliased <code>twitchbot.twitch_user_stats_overall</code> table reference
     */
    public TwitchUserStatsOverall(String alias) {
        this(DSL.name(alias), TWITCH_USER_STATS_OVERALL);
    }

    /**
     * Create an aliased <code>twitchbot.twitch_user_stats_overall</code> table reference
     */
    public TwitchUserStatsOverall(Name alias) {
        this(alias, TWITCH_USER_STATS_OVERALL);
    }

    private TwitchUserStatsOverall(Name alias, Table<TwitchUserStatsOverallRecord> aliased) {
        this(alias, aliased, null);
    }

    private TwitchUserStatsOverall(Name alias, Table<TwitchUserStatsOverallRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> TwitchUserStatsOverall(Table<O> child, ForeignKey<O, TwitchUserStatsOverallRecord> key) {
        super(child, key, TWITCH_USER_STATS_OVERALL);
    }

    @Override
    public Schema getSchema() {
        return Twitchbot.TWITCHBOT;
    }

    @Override
    public Identity<TwitchUserStatsOverallRecord, Integer> getIdentity() {
        return Keys.IDENTITY_TWITCH_USER_STATS_OVERALL;
    }

    @Override
    public UniqueKey<TwitchUserStatsOverallRecord> getPrimaryKey() {
        return Keys.KEY_TWITCH_USER_STATS_OVERALL_PRIMARY;
    }

    @Override
    public List<UniqueKey<TwitchUserStatsOverallRecord>> getKeys() {
        return Arrays.<UniqueKey<TwitchUserStatsOverallRecord>>asList(Keys.KEY_TWITCH_USER_STATS_OVERALL_PRIMARY);
    }

    @Override
    public TwitchUserStatsOverall as(String alias) {
        return new TwitchUserStatsOverall(DSL.name(alias), this);
    }

    @Override
    public TwitchUserStatsOverall as(Name alias) {
        return new TwitchUserStatsOverall(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TwitchUserStatsOverall rename(String name) {
        return new TwitchUserStatsOverall(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TwitchUserStatsOverall rename(Name name) {
        return new TwitchUserStatsOverall(name, null);
    }

    // -------------------------------------------------------------------------
    // Row10 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row10<Integer, String, String, Long, Long, Long, Long, Long, Long, Double> fieldsRow() {
        return (Row10) super.fieldsRow();
    }
}
