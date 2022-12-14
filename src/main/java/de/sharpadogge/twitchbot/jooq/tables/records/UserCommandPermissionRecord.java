/*
 * This file is generated by jOOQ.
 */
package de.sharpadogge.twitchbot.jooq.tables.records;


import de.sharpadogge.twitchbot.jooq.tables.UserCommandPermission;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserCommandPermissionRecord extends UpdatableRecordImpl<UserCommandPermissionRecord> implements Record3<Long, Long, String> {

    private static final long serialVersionUID = -1265311182;

    /**
     * Setter for <code>twitchbot.user_command_permission.user_id</code>.
     */
    public void setUserId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>twitchbot.user_command_permission.user_id</code>.
     */
    public Long getUserId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>twitchbot.user_command_permission.command_id</code>.
     */
    public void setCommandId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>twitchbot.user_command_permission.command_id</code>.
     */
    public Long getCommandId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>twitchbot.user_command_permission.permission</code>.
     */
    public void setPermission(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>twitchbot.user_command_permission.permission</code>.
     */
    public String getPermission() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record3<Long, Long, String> key() {
        return (Record3) super.key();
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
        return UserCommandPermission.USER_COMMAND_PERMISSION.USER_ID;
    }

    @Override
    public Field<Long> field2() {
        return UserCommandPermission.USER_COMMAND_PERMISSION.COMMAND_ID;
    }

    @Override
    public Field<String> field3() {
        return UserCommandPermission.USER_COMMAND_PERMISSION.PERMISSION;
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
        return getPermission();
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
        return getPermission();
    }

    @Override
    public UserCommandPermissionRecord value1(Long value) {
        setUserId(value);
        return this;
    }

    @Override
    public UserCommandPermissionRecord value2(Long value) {
        setCommandId(value);
        return this;
    }

    @Override
    public UserCommandPermissionRecord value3(String value) {
        setPermission(value);
        return this;
    }

    @Override
    public UserCommandPermissionRecord values(Long value1, Long value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserCommandPermissionRecord
     */
    public UserCommandPermissionRecord() {
        super(UserCommandPermission.USER_COMMAND_PERMISSION);
    }

    /**
     * Create a detached, initialised UserCommandPermissionRecord
     */
    public UserCommandPermissionRecord(Long userId, Long commandId, String permission) {
        super(UserCommandPermission.USER_COMMAND_PERMISSION);

        set(0, userId);
        set(1, commandId);
        set(2, permission);
    }
}
