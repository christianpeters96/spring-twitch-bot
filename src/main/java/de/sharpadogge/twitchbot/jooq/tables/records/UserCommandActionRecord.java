/*
 * This file is generated by jOOQ.
 */
package de.sharpadogge.twitchbot.jooq.tables.records;


import de.sharpadogge.twitchbot.jooq.tables.UserCommandAction;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserCommandActionRecord extends UpdatableRecordImpl<UserCommandActionRecord> implements Record4<Long, Long, Integer, String> {

    private static final long serialVersionUID = 1230772560;

    /**
     * Setter for <code>twitchbot.user_command_action.user_id</code>.
     */
    public void setUserId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>twitchbot.user_command_action.user_id</code>.
     */
    public Long getUserId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>twitchbot.user_command_action.command_id</code>.
     */
    public void setCommandId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>twitchbot.user_command_action.command_id</code>.
     */
    public Long getCommandId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>twitchbot.user_command_action.idx</code>.
     */
    public void setIdx(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>twitchbot.user_command_action.idx</code>.
     */
    public Integer getIdx() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>twitchbot.user_command_action.action</code>.
     */
    public void setAction(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>twitchbot.user_command_action.action</code>.
     */
    public String getAction() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record3<Long, Long, Integer> key() {
        return (Record3) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, Long, Integer, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Long, Long, Integer, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return UserCommandAction.USER_COMMAND_ACTION.USER_ID;
    }

    @Override
    public Field<Long> field2() {
        return UserCommandAction.USER_COMMAND_ACTION.COMMAND_ID;
    }

    @Override
    public Field<Integer> field3() {
        return UserCommandAction.USER_COMMAND_ACTION.IDX;
    }

    @Override
    public Field<String> field4() {
        return UserCommandAction.USER_COMMAND_ACTION.ACTION;
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
    public Integer component3() {
        return getIdx();
    }

    @Override
    public String component4() {
        return getAction();
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
    public Integer value3() {
        return getIdx();
    }

    @Override
    public String value4() {
        return getAction();
    }

    @Override
    public UserCommandActionRecord value1(Long value) {
        setUserId(value);
        return this;
    }

    @Override
    public UserCommandActionRecord value2(Long value) {
        setCommandId(value);
        return this;
    }

    @Override
    public UserCommandActionRecord value3(Integer value) {
        setIdx(value);
        return this;
    }

    @Override
    public UserCommandActionRecord value4(String value) {
        setAction(value);
        return this;
    }

    @Override
    public UserCommandActionRecord values(Long value1, Long value2, Integer value3, String value4) {
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
     * Create a detached UserCommandActionRecord
     */
    public UserCommandActionRecord() {
        super(UserCommandAction.USER_COMMAND_ACTION);
    }

    /**
     * Create a detached, initialised UserCommandActionRecord
     */
    public UserCommandActionRecord(Long userId, Long commandId, Integer idx, String action) {
        super(UserCommandAction.USER_COMMAND_ACTION);

        set(0, userId);
        set(1, commandId);
        set(2, idx);
        set(3, action);
    }
}
