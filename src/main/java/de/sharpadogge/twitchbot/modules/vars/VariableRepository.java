package de.sharpadogge.twitchbot.modules.vars;

import de.sharpadogge.twitchbot.jooq.Tables;
import de.sharpadogge.twitchbot.modules.vars.model.UserVariable;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class VariableRepository {

    private final DSLContext create;

    @Autowired
    public VariableRepository(DSLContext create) {
        this.create = create;
    }

    public Optional<UserVariable> getVariable(Long userId, String name) {
        return create
                .selectFrom(Tables.USER_VARS)
                .where(Tables.USER_VARS.USER_ID.eq(userId))
                .and(Tables.USER_VARS.NAME.eq(name))
                .fetchOptionalInto(UserVariable.class);
    }

    public int insertVariable(Long userId, String name, String value) {
        return create.newRecord(Tables.USER_VARS, new UserVariable(userId, name, value)).insert();
    }

    public int updateVariable(Long userId, String name, String value) {
        return create
                .update(Tables.USER_VARS)
                .set(Tables.USER_VARS.VALUE, value)
                .where(Tables.USER_VARS.USER_ID.eq(userId))
                .and(Tables.USER_VARS.NAME.eq(name))
                .execute();
    }

    public int deleteVariable(Long userId, String name) {
        return create
                .deleteFrom(Tables.USER_VARS)
                .where(Tables.USER_VARS.USER_ID.eq(userId))
                .and(Tables.USER_VARS.NAME.eq(name))
                .execute();
    }
}
