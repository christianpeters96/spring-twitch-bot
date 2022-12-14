/*
 * This file is generated by jOOQ.
 */
package de.sharpadogge.twitchbot.jooq.tables;


import de.sharpadogge.twitchbot.jooq.Keys;
import de.sharpadogge.twitchbot.jooq.Twitchbot;
import de.sharpadogge.twitchbot.jooq.tables.records.UserSpamExceptionsRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
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
public class UserSpamExceptions extends TableImpl<UserSpamExceptionsRecord> {

    private static final long serialVersionUID = -819175695;

    /**
     * The reference instance of <code>twitchbot.user_spam_exceptions</code>
     */
    public static final UserSpamExceptions USER_SPAM_EXCEPTIONS = new UserSpamExceptions();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserSpamExceptionsRecord> getRecordType() {
        return UserSpamExceptionsRecord.class;
    }

    /**
     * The column <code>twitchbot.user_spam_exceptions.user_id</code>.
     */
    public final TableField<UserSpamExceptionsRecord, Long> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>twitchbot.user_spam_exceptions.filter</code>.
     */
    public final TableField<UserSpamExceptionsRecord, String> FILTER = createField(DSL.name("filter"), org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "");

    /**
     * The column <code>twitchbot.user_spam_exceptions.exception</code>.
     */
    public final TableField<UserSpamExceptionsRecord, String> EXCEPTION = createField(DSL.name("exception"), org.jooq.impl.SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * Create a <code>twitchbot.user_spam_exceptions</code> table reference
     */
    public UserSpamExceptions() {
        this(DSL.name("user_spam_exceptions"), null);
    }

    /**
     * Create an aliased <code>twitchbot.user_spam_exceptions</code> table reference
     */
    public UserSpamExceptions(String alias) {
        this(DSL.name(alias), USER_SPAM_EXCEPTIONS);
    }

    /**
     * Create an aliased <code>twitchbot.user_spam_exceptions</code> table reference
     */
    public UserSpamExceptions(Name alias) {
        this(alias, USER_SPAM_EXCEPTIONS);
    }

    private UserSpamExceptions(Name alias, Table<UserSpamExceptionsRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserSpamExceptions(Name alias, Table<UserSpamExceptionsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> UserSpamExceptions(Table<O> child, ForeignKey<O, UserSpamExceptionsRecord> key) {
        super(child, key, USER_SPAM_EXCEPTIONS);
    }

    @Override
    public Schema getSchema() {
        return Twitchbot.TWITCHBOT;
    }

    @Override
    public UniqueKey<UserSpamExceptionsRecord> getPrimaryKey() {
        return Keys.KEY_USER_SPAM_EXCEPTIONS_PRIMARY;
    }

    @Override
    public List<UniqueKey<UserSpamExceptionsRecord>> getKeys() {
        return Arrays.<UniqueKey<UserSpamExceptionsRecord>>asList(Keys.KEY_USER_SPAM_EXCEPTIONS_PRIMARY);
    }

    @Override
    public List<ForeignKey<UserSpamExceptionsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<UserSpamExceptionsRecord, ?>>asList(Keys.FK_SPAM_EXCEPTIONS_USER_ID);
    }

    public Users users() {
        return new Users(this, Keys.FK_SPAM_EXCEPTIONS_USER_ID);
    }

    @Override
    public UserSpamExceptions as(String alias) {
        return new UserSpamExceptions(DSL.name(alias), this);
    }

    @Override
    public UserSpamExceptions as(Name alias) {
        return new UserSpamExceptions(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserSpamExceptions rename(String name) {
        return new UserSpamExceptions(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserSpamExceptions rename(Name name) {
        return new UserSpamExceptions(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
