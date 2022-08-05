/*
 * This file is generated by jOOQ.
 */
package de.sharpadogge.twitchbot.jooq.tables.records;


import de.sharpadogge.twitchbot.jooq.tables.UserStringBlacklist;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserStringBlacklistRecord extends UpdatableRecordImpl<UserStringBlacklistRecord> implements Record2<Long, String> {

    private static final long serialVersionUID = -1419744629;

    /**
     * Setter for <code>twitchbot.user_string_blacklist.user_id</code>.
     */
    public void setUserId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>twitchbot.user_string_blacklist.user_id</code>.
     */
    public Long getUserId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>twitchbot.user_string_blacklist.string</code>.
     */
    public void setString(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>twitchbot.user_string_blacklist.string</code>.
     */
    public String getString() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<Long, String> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Long, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Long, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return UserStringBlacklist.USER_STRING_BLACKLIST.USER_ID;
    }

    @Override
    public Field<String> field2() {
        return UserStringBlacklist.USER_STRING_BLACKLIST.STRING;
    }

    @Override
    public Long component1() {
        return getUserId();
    }

    @Override
    public String component2() {
        return getString();
    }

    @Override
    public Long value1() {
        return getUserId();
    }

    @Override
    public String value2() {
        return getString();
    }

    @Override
    public UserStringBlacklistRecord value1(Long value) {
        setUserId(value);
        return this;
    }

    @Override
    public UserStringBlacklistRecord value2(String value) {
        setString(value);
        return this;
    }

    @Override
    public UserStringBlacklistRecord values(Long value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserStringBlacklistRecord
     */
    public UserStringBlacklistRecord() {
        super(UserStringBlacklist.USER_STRING_BLACKLIST);
    }

    /**
     * Create a detached, initialised UserStringBlacklistRecord
     */
    public UserStringBlacklistRecord(Long userId, String string) {
        super(UserStringBlacklist.USER_STRING_BLACKLIST);

        set(0, userId);
        set(1, string);
    }
}