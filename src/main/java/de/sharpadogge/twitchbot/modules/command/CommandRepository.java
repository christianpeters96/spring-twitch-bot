package de.sharpadogge.twitchbot.modules.command;

import de.sharpadogge.twitchbot.jooq.Tables;
import de.sharpadogge.twitchbot.modules.command.entity.UserCommand;
import de.sharpadogge.twitchbot.modules.command.entity.UserCommandAction;
import de.sharpadogge.twitchbot.modules.command.entity.UserCommandAlias;
import de.sharpadogge.twitchbot.modules.command.entity.UserCommandPermission;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommandRepository {

    private final DSLContext create;

    @Autowired
    public CommandRepository(final DSLContext create) {
        this.create = create;
    }

    public List<UserCommand> getCustomCommands(final long userId) {
        return create
                .selectFrom(Tables.USER_COMMAND)
                .where(Tables.USER_COMMAND.USER_ID.eq(userId))
                .fetchInto(UserCommand.class);
    }

    public List<UserCommand> getEnabledCustomCommands(final long userId) {
        return create
                .selectFrom(Tables.USER_COMMAND)
                .where(Tables.USER_COMMAND.USER_ID.eq(userId))
                .and(Tables.USER_COMMAND.ENABLED.isTrue())
                .fetchInto(UserCommand.class);
    }

    public Optional<UserCommand> getCustomCommand(final long userId, final long commandId) {
        return create
                .selectFrom(Tables.USER_COMMAND)
                .where(Tables.USER_COMMAND.USER_ID.eq(userId))
                .and(Tables.USER_COMMAND.ID.eq(commandId))
                .fetchOptionalInto(UserCommand.class);
    }

    public List<UserCommandAlias> getAliases(final long userId, final long commandId) {
        return create
                .selectFrom(Tables.USER_COMMAND_ALIAS)
                .where(Tables.USER_COMMAND_ALIAS.USER_ID.eq(userId))
                .and(Tables.USER_COMMAND_ALIAS.COMMAND_ID.eq(commandId))
                .fetchInto(UserCommandAlias.class);
    }

    public List<UserCommandPermission> getPermissions(final long userId, final long commandId) {
        return create
                .selectFrom(Tables.USER_COMMAND_PERMISSION)
                .where(Tables.USER_COMMAND_PERMISSION.USER_ID.eq(userId))
                .and(Tables.USER_COMMAND_PERMISSION.COMMAND_ID.eq(commandId))
                .fetchInto(UserCommandPermission.class);
    }

    public List<UserCommandAction> getActions(final long userId, final long commandId) {
        return create
                .selectFrom(Tables.USER_COMMAND_ACTION)
                .where(Tables.USER_COMMAND_ACTION.USER_ID.eq(userId))
                .and(Tables.USER_COMMAND_ACTION.COMMAND_ID.eq(commandId))
                .orderBy(Tables.USER_COMMAND_ACTION.IDX.asc())
                .fetchInto(UserCommandAction.class);
    }

    public boolean commandExists(final Long userId, final String cmd) {
        boolean exists = create
                .selectCount()
                .from(Tables.USER_COMMAND)
                .where(Tables.USER_COMMAND.USER_ID.eq(userId))
                .and(Tables.USER_COMMAND.CMD.eq(cmd))
                .fetchOneInto(Integer.class) != 0;
        if (exists) return true;
        exists = create
                .selectCount()
                .from(Tables.USER_COMMAND_ALIAS)
                .where(Tables.USER_COMMAND_ALIAS.USER_ID.eq(userId))
                .and(Tables.USER_COMMAND_ALIAS.ALIAS.eq(cmd))
                .fetchOneInto(Integer.class) != 0;
        return exists;
    }

    public boolean commandExists(final Long userId, final String cmd, final long excludedId) {
        boolean exists = create
                .selectCount()
                .from(Tables.USER_COMMAND)
                .where(Tables.USER_COMMAND.USER_ID.eq(userId))
                .and(Tables.USER_COMMAND.CMD.eq(cmd))
                .and(Tables.USER_COMMAND.ID.notEqual(excludedId))
                .fetchOneInto(Integer.class) != 0;
        if (exists) return true;
        exists = create
                .selectCount()
                .from(Tables.USER_COMMAND_ALIAS)
                .where(Tables.USER_COMMAND_ALIAS.USER_ID.eq(userId))
                .and(Tables.USER_COMMAND_ALIAS.ALIAS.eq(cmd))
                .and(Tables.USER_COMMAND_ALIAS.COMMAND_ID.notEqual(excludedId))
                .fetchOneInto(Integer.class) != 0;
        return exists;
    }

    public boolean commandExists(final Long userId, final long cmdId) {
        return create
                .selectCount()
                .from(Tables.USER_COMMAND)
                .where(Tables.USER_COMMAND.USER_ID.eq(userId))
                .and(Tables.USER_COMMAND.ID.eq(cmdId))
                .fetchOneInto(Integer.class) != 0;
    }

    public int createUserCommand(final UserCommand command) {
        return create.newRecord(Tables.USER_COMMAND, command).insert();
    }

    public int updateUserCommand(final UserCommand command) {
        return create.newRecord(Tables.USER_COMMAND, command).update();
    }

    public UserCommand getUserCommandByCmd(final Long userId, final String cmd) {
        return create
                .selectFrom(Tables.USER_COMMAND)
                .where(Tables.USER_COMMAND.USER_ID.eq(userId))
                .and(Tables.USER_COMMAND.CMD.eq(cmd))
                .fetchOneInto(UserCommand.class);
    }

    public boolean hasAccess(final Long userId, final Long cmdId) {
        return create
                .selectCount()
                .from(Tables.USER_COMMAND)
                .where(Tables.USER_COMMAND.USER_ID.eq(userId))
                .and(Tables.USER_COMMAND.ID.eq(cmdId))
                .fetchOneInto(Integer.class) != 0;
    }

    public int createUserCommandAliases(final UserCommand command) {
        int out = 0;
        create.deleteFrom(Tables.USER_COMMAND_ALIAS)
                .where(Tables.USER_COMMAND_ALIAS.USER_ID.eq(command.getUserId()))
                .and(Tables.USER_COMMAND_ALIAS.COMMAND_ID.eq(command.getId()))
                .execute();
        if (command.getAliases().size() != 0) {
            for (UserCommandAlias alias : command.getAliases()) {
                if (alias.getAlias().length() != 0) {
                    out += create.newRecord(Tables.USER_COMMAND_ALIAS, alias).insert();
                }
            }
        }
        return out;
    }

    public int saveUserCommandActions(final UserCommand command) {
        int out = 0;
        create.deleteFrom(Tables.USER_COMMAND_ACTION)
                .where(Tables.USER_COMMAND_ACTION.USER_ID.eq(command.getUserId()))
                .and(Tables.USER_COMMAND_ACTION.COMMAND_ID.eq(command.getId()))
                .execute();
        if (command.getActions().size() != 0) {
            for (UserCommandAction action : command.getActions()) {
                if (action.getAction().length() != 0) {
                    out += create.newRecord(Tables.USER_COMMAND_ACTION, action).insert();
                }
            }
        }
        return out;
    }

    public int saveUserCommandPermissions(final UserCommand command) {
        int out = 0;
        create.deleteFrom(Tables.USER_COMMAND_PERMISSION)
                .where(Tables.USER_COMMAND_PERMISSION.USER_ID.eq(command.getUserId()))
                .and(Tables.USER_COMMAND_PERMISSION.COMMAND_ID.eq(command.getId()))
                .execute();
        if (command.getPermissions().size() != 0) {
            for (UserCommandPermission permission : command.getPermissions()) {
                if (permission.getPermission().length() != 0) {
                    out += create.newRecord(Tables.USER_COMMAND_PERMISSION, permission).insert();
                }
            }
        }
        return out;
    }

    public int updateStatus(final long commandId, final boolean status) {
        return create
                .update(Tables.USER_COMMAND)
                .set(Tables.USER_COMMAND.ENABLED, status ? (byte) 1 : (byte) 0)
                .where(Tables.USER_COMMAND.ID.eq(commandId))
                .execute();
    }

    public int delete(final long userId, final long commandId) {
        return create
                .deleteFrom(Tables.USER_COMMAND)
                .where(Tables.USER_COMMAND.USER_ID.eq(userId))
                .and(Tables.USER_COMMAND.ID.eq(commandId))
                .execute();
    }
}
