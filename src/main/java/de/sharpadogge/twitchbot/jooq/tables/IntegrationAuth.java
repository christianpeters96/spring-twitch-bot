/*
 * This file is generated by jOOQ.
 */
package de.sharpadogge.twitchbot.jooq.tables;


import de.sharpadogge.twitchbot.jooq.Keys;
import de.sharpadogge.twitchbot.jooq.Twitchbot;
import de.sharpadogge.twitchbot.jooq.tables.records.IntegrationAuthRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row7;
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
public class IntegrationAuth extends TableImpl<IntegrationAuthRecord> {

    private static final long serialVersionUID = -1343613656;

    /**
     * The reference instance of <code>twitchbot.integration_auth</code>
     */
    public static final IntegrationAuth INTEGRATION_AUTH = new IntegrationAuth();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<IntegrationAuthRecord> getRecordType() {
        return IntegrationAuthRecord.class;
    }

    /**
     * The column <code>twitchbot.integration_auth.id</code>.
     */
    public final TableField<IntegrationAuthRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>twitchbot.integration_auth.provider</code>.
     */
    public final TableField<IntegrationAuthRecord, String> PROVIDER = createField(DSL.name("provider"), org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>twitchbot.integration_auth.access_token</code>.
     */
    public final TableField<IntegrationAuthRecord, String> ACCESS_TOKEN = createField(DSL.name("access_token"), org.jooq.impl.SQLDataType.VARCHAR(1024).nullable(false), this, "");

    /**
     * The column <code>twitchbot.integration_auth.refresh_token</code>.
     */
    public final TableField<IntegrationAuthRecord, String> REFRESH_TOKEN = createField(DSL.name("refresh_token"), org.jooq.impl.SQLDataType.VARCHAR(1024).nullable(false), this, "");

    /**
     * The column <code>twitchbot.integration_auth.expires_at</code>.
     */
    public final TableField<IntegrationAuthRecord, LocalDateTime> EXPIRES_AT = createField(DSL.name("expires_at"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("current_timestamp()", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>twitchbot.integration_auth.scope</code>.
     */
    public final TableField<IntegrationAuthRecord, String> SCOPE = createField(DSL.name("scope"), org.jooq.impl.SQLDataType.VARCHAR(1024).nullable(false), this, "");

    /**
     * The column <code>twitchbot.integration_auth.token_type</code>.
     */
    public final TableField<IntegrationAuthRecord, String> TOKEN_TYPE = createField(DSL.name("token_type"), org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "");

    /**
     * Create a <code>twitchbot.integration_auth</code> table reference
     */
    public IntegrationAuth() {
        this(DSL.name("integration_auth"), null);
    }

    /**
     * Create an aliased <code>twitchbot.integration_auth</code> table reference
     */
    public IntegrationAuth(String alias) {
        this(DSL.name(alias), INTEGRATION_AUTH);
    }

    /**
     * Create an aliased <code>twitchbot.integration_auth</code> table reference
     */
    public IntegrationAuth(Name alias) {
        this(alias, INTEGRATION_AUTH);
    }

    private IntegrationAuth(Name alias, Table<IntegrationAuthRecord> aliased) {
        this(alias, aliased, null);
    }

    private IntegrationAuth(Name alias, Table<IntegrationAuthRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> IntegrationAuth(Table<O> child, ForeignKey<O, IntegrationAuthRecord> key) {
        super(child, key, INTEGRATION_AUTH);
    }

    @Override
    public Schema getSchema() {
        return Twitchbot.TWITCHBOT;
    }

    @Override
    public Identity<IntegrationAuthRecord, Long> getIdentity() {
        return Keys.IDENTITY_INTEGRATION_AUTH;
    }

    @Override
    public UniqueKey<IntegrationAuthRecord> getPrimaryKey() {
        return Keys.KEY_INTEGRATION_AUTH_PRIMARY;
    }

    @Override
    public List<UniqueKey<IntegrationAuthRecord>> getKeys() {
        return Arrays.<UniqueKey<IntegrationAuthRecord>>asList(Keys.KEY_INTEGRATION_AUTH_PRIMARY);
    }

    @Override
    public IntegrationAuth as(String alias) {
        return new IntegrationAuth(DSL.name(alias), this);
    }

    @Override
    public IntegrationAuth as(Name alias) {
        return new IntegrationAuth(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public IntegrationAuth rename(String name) {
        return new IntegrationAuth(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public IntegrationAuth rename(Name name) {
        return new IntegrationAuth(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, String, String, String, LocalDateTime, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}
