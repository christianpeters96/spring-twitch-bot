/*
 * This file is generated by jOOQ.
 */
package de.sharpadogge.twitchbot.jooq.tables.records;


import de.sharpadogge.twitchbot.jooq.tables.UserUrlWhitelist;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserUrlWhitelistRecord extends UpdatableRecordImpl<UserUrlWhitelistRecord> implements Record2<Long, String> {

    private static final long serialVersionUID = -1069734741;

    /**
     * Setter for <code>twitchbot.user_url_whitelist.user_id</code>.
     */
    public void setUserId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>twitchbot.user_url_whitelist.user_id</code>.
     */
    public Long getUserId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>twitchbot.user_url_whitelist.url</code>.
     */
    public void setUrl(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>twitchbot.user_url_whitelist.url</code>.
     */
    public String getUrl() {
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
        return UserUrlWhitelist.USER_URL_WHITELIST.USER_ID;
    }

    @Override
    public Field<String> field2() {
        return UserUrlWhitelist.USER_URL_WHITELIST.URL;
    }

    @Override
    public Long component1() {
        return getUserId();
    }

    @Override
    public String component2() {
        return getUrl();
    }

    @Override
    public Long value1() {
        return getUserId();
    }

    @Override
    public String value2() {
        return getUrl();
    }

    @Override
    public UserUrlWhitelistRecord value1(Long value) {
        setUserId(value);
        return this;
    }

    @Override
    public UserUrlWhitelistRecord value2(String value) {
        setUrl(value);
        return this;
    }

    @Override
    public UserUrlWhitelistRecord values(Long value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserUrlWhitelistRecord
     */
    public UserUrlWhitelistRecord() {
        super(UserUrlWhitelist.USER_URL_WHITELIST);
    }

    /**
     * Create a detached, initialised UserUrlWhitelistRecord
     */
    public UserUrlWhitelistRecord(Long userId, String url) {
        super(UserUrlWhitelist.USER_URL_WHITELIST);

        set(0, userId);
        set(1, url);
    }
}