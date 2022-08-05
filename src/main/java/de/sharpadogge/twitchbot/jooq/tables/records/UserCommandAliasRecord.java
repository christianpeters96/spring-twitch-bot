/*
 * This file is generated by jOOQ.
 */
package de.sharpadogge.twitchbot.jooq.tables.records;


import de.sharpadogge.twitchbot.jooq.tables.UserCommandAlias;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserCommandAliasRecord extends UpdatableRecordImpl<UserCommandAliasRecord> implements Record3<Long, Long, String> {

    private static final long serialVersionUID = -1623910151;

    /**
     * Setter for <code>twitchbot.user_command_alias.user_id</code>.
     */
    public void setUserId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>twitchbot.user_command_alias.user_id</code>.
     */
    public Long getUserId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>twitchbot.user_command_alias.command_id</code>.
     */
    public void setCommandId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>twitchbot.user_command_alias.command_id</code>.
     */
    public Long getCommandId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>twitchbot.user_command_alias.alias</code>.
     */
    public void setAlias(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>twitchbot.user_command_alias.alias</code>.
     */
    public String getAlias() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<Long, String> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, Long, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Long, Long, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return UserCommandAlias.USER_COMMAND_ALIAS.USER_ID;
    }

    @Override
    public Field<Long> field2() {
        return UserCommandAlias.USER_COMMAND_ALIAS.COMMAND_ID;
    }

    @Override
    public Field<String> field3() {
        return UserCommandAlias.USER_COMMAND_ALIAS.ALIAS;
    }

    @Override
    public Long component1() {
        return getUserId();
    }

    @Override
    public Long component2() {
        return getCommandId();
    }

    @Override
    public String component3() {
        return getAlias();
    }

    @Override
    public Long value1() {
        return getUserId();
    }

    @Override
    public Long value2() {
        return getCommandId();
    }

    @Override
    public String value3() {
        return getAlias();
    }

    @Override
    public UserCommandAliasRecord value1(Long value) {
        setUserId(value);
        return this;
    }

    @Override
    public UserCommandAliasRecord value2(Long value) {
        setCommandId(value);
        return this;
    }

    @Override
    public UserCommandAliasRecord value3(String value) {
        setAlias(value);
        return this;
    }

    @Override
    public UserCommandAliasRecord values(Long value1, Long value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserCommandAliasRecord
     */
    public UserCommandAliasRecord() {
        super(UserCommandAlias.USER_COMMAND_ALIAS);
    }

    /**
     * Create a detached, initialised UserCommandAliasRecord
     */
    public UserCommandAliasRecord(Long userId, Long commandId, String alias) {
        super(UserCommandAlias.USER_COMMAND_ALIAS);

        set(0, userId);
        set(1, commandId);
        set(2, alias);
    }
}