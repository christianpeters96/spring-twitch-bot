/*
 * This file is generated by jOOQ.
 */
package de.sharpadogge.twitchbot.jooq.tables;


import de.sharpadogge.twitchbot.jooq.Keys;
import de.sharpadogge.twitchbot.jooq.Twitchbot;
import de.sharpadogge.twitchbot.jooq.tables.records.UserIntegrationRecord;

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
public class UserIntegration extends TableImpl<UserIntegrationRecord> {

    private static final long serialVersionUID = -643973968;

    /**
     * The reference instance of <code>twitchbot.user_integration</code>
     */
    public static final UserIntegration USER_INTEGRATION = new UserIntegration();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserIntegrationRecord> getRecordType() {
        return UserIntegrationRecord.class;
    }

    /**
     * The column <code>twitchbot.user_integration.user_id</code>.
     */
    public final TableField<UserIntegrationRecord, Long> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>twitchbot.user_integration.integration_id</code>.
     */
    public final TableField<UserIntegrationRecord, Long> INTEGRATION_ID = createField(DSL.name("integration_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>twitchbot.user_integration</code> table reference
     */
    public UserIntegration() {
        this(DSL.name("user_integration"), null);
    }

    /**
     * Create an aliased <code>twitchbot.user_integration</code> table reference
     */
    public UserIntegration(String alias) {
        this(DSL.name(alias), USER_INTEGRATION);
    }

    /**
     * Create an aliased <code>twitchbot.user_integration</code> table reference
     */
    public UserIntegration(Name alias) {
        this(alias, USER_INTEGRATION);
    }

    private UserIntegration(Name alias, Table<UserIntegrationRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserIntegration(Name alias, Table<UserIntegrationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> UserIntegration(Table<O> child, ForeignKey<O, UserIntegrationRecord> key) {
        super(child, key, USER_INTEGRATION);
    }

    @Override
    public Schema getSchema() {
        return Twitchbot.TWITCHBOT;
    }

    @Override
    public UniqueKey<UserIntegrationRecord> getPrimaryKey() {
        return Keys.KEY_USER_INTEGRATION_PRIMARY;
    }

    @Override
    public List<UniqueKey<UserIntegrationRecord>> getKeys() {
        return Arrays.<UniqueKey<UserIntegrationRecord>>asList(Keys.KEY_USER_INTEGRATION_PRIMARY);
    }

    @Override
    public List<ForeignKey<UserIntegrationRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<UserIntegrationRecord, ?>>asList(Keys.FK_USER_INTEGRATION_USER_ID, Keys.FK_USER_INTEGRATION_INTEGRATION_ID);
    }

    public Users users() {
        return new Users(this, Keys.FK_USER_INTEGRATION_USER_ID);
    }

    public IntegrationAuth integrationAuth() {
        return new IntegrationAuth(this, Keys.FK_USER_INTEGRATION_INTEGRATION_ID);
    }

    @Override
    public UserIntegration as(String alias) {
        return new UserIntegration(DSL.name(alias), this);
    }

    @Override
    public UserIntegration as(Name alias) {
        return new UserIntegration(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserIntegration rename(String name) {
        return new UserIntegration(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserIntegration rename(Name name) {
        return new UserIntegration(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
