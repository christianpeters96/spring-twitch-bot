/*
 * This file is generated by jOOQ.
 */
package de.sharpadogge.twitchbot.jooq.tables;


import de.sharpadogge.twitchbot.jooq.Keys;
import de.sharpadogge.twitchbot.jooq.Twitchbot;
import de.sharpadogge.twitchbot.jooq.tables.records.UserStringBlacklistRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
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
public class UserStringBlacklist extends TableImpl<UserStringBlacklistRecord> {

    private static final long serialVersionUID = 567931344;

    /**
     * The reference instance of <code>twitchbot.user_string_blacklist</code>
     */
    public static final UserStringBlacklist USER_STRING_BLACKLIST = new UserStringBlacklist();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserStringBlacklistRecord> getRecordType() {
        return UserStringBlacklistRecord.class;
    }

    /**
     * The column <code>twitchbot.user_string_blacklist.user_id</code>.
     */
    public final TableField<UserStringBlacklistRecord, Long> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>twitchbot.user_string_blacklist.string</code>.
     */
    public final TableField<UserStringBlacklistRecord, String> STRING = createField(DSL.name("string"), org.jooq.impl.SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * Create a <code>twitchbot.user_string_blacklist</code> table reference
     */
    public UserStringBlacklist() {
        this(DSL.name("user_string_blacklist"), null);
    }

    /**
     * Create an aliased <code>twitchbot.user_string_blacklist</code> table reference
     */
    public UserStringBlacklist(String alias) {
        this(DSL.name(alias), USER_STRING_BLACKLIST);
    }

    /**
     * Create an aliased <code>twitchbot.user_string_blacklist</code> table reference
     */
    public UserStringBlacklist(Name alias) {
        this(alias, USER_STRING_BLACKLIST);
    }

    private UserStringBlacklist(Name alias, Table<UserStringBlacklistRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserStringBlacklist(Name alias, Table<UserStringBlacklistRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> UserStringBlacklist(Table<O> child, ForeignKey<O, UserStringBlacklistRecord> key) {
        super(child, key, USER_STRING_BLACKLIST);
    }

    @Override
    public Schema getSchema() {
        return Twitchbot.TWITCHBOT;
    }

    @Override
    public UniqueKey<UserStringBlacklistRecord> getPrimaryKey() {
        return Keys.KEY_USER_STRING_BLACKLIST_PRIMARY;
    }

    @Override
    public List<UniqueKey<UserStringBlacklistRecord>> getKeys() {
        return Arrays.<UniqueKey<UserStringBlacklistRecord>>asList(Keys.KEY_USER_STRING_BLACKLIST_PRIMARY);
    }

    @Override
    public List<ForeignKey<UserStringBlacklistRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<UserStringBlacklistRecord, ?>>asList(Keys.FK_STRING_BLACKLIST_USER_ID);
    }

    public Users users() {
        return new Users(this, Keys.FK_STRING_BLACKLIST_USER_ID);
    }

    @Override
    public UserStringBlacklist as(String alias) {
        return new UserStringBlacklist(DSL.name(alias), this);
    }

    @Override
    public UserStringBlacklist as(Name alias) {
        return new UserStringBlacklist(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserStringBlacklist rename(String name) {
        return new UserStringBlacklist(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserStringBlacklist rename(Name name) {
        return new UserStringBlacklist(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Long, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
