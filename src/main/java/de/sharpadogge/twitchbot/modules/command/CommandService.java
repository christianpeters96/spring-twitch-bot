package de.sharpadogge.twitchbot.modules.command;

import de.sharpadogge.twitchbot.modules.command.entity.UserCommand;
import de.sharpadogge.twitchbot.modules.command.entity.UserCommandAction;
import de.sharpadogge.twitchbot.modules.command.entity.UserCommandAlias;
import de.sharpadogge.twitchbot.modules.command.entity.UserCommandPermission;
import de.sharpadogge.twitchbot.modules.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandService {

    private final CommandRepository commandRepository;

    @Autowired
    public CommandService(final CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    public int create(UserCommand command) {
        int res = 0;
        if (commandRepository.commandExists(command.getUserId(), command.getCmd())) return -1;
        for (UserCommandAlias alias : command.getAliases()) {
            if (commandRepository.commandExists(command.getUserId(), alias.getAlias())) return -1;
        }
        res += commandRepository.createUserCommand(command);
        command.setId(commandRepository.getUserCommandByCmd(command.getUserId(), command.getCmd()).getId());
        for (UserCommandAlias alias : command.getAliases()) {
            if (alias.getAlias().length() != 0) {
                alias.setUserId(command.getUserId());
                alias.setCommandId(command.getId());
            }
        }
        int idx = 0;
        for (UserCommandAction action : command.getActions()) {
            if (action.getAction().length() != 0) {
                action.setIdx(idx++);
                action.setUserId(command.getUserId());
                action.setCommandId(command.getId());
            }
        }
        for (UserCommandPermission permission : command.getPermissions()) {
            if (permission.getPermission().length() != 0) {
                permission.setUserId(command.getUserId());
                permission.setCommandId(command.getId());
            }
        }
        res += commandRepository.createUserCommandAliases(command);
        res += commandRepository.saveUserCommandActions(command);
        res += commandRepository.saveUserCommandPermissions(command);
        return res;
    }

    public int update(UserCommand command) {
        int res = 0;
        if (commandRepository.commandExists(command.getUserId(), command.getCmd(), command.getId())) return -1;
        for (UserCommandAlias alias : command.getAliases()) {
            if (commandRepository.commandExists(command.getUserId(), alias.getAlias(), command.getId())) return -1;
        }
        res += commandRepository.updateUserCommand(command);
        for (UserCommandAlias alias : command.getAliases()) {
            if (alias.getAlias().length() != 0) {
                alias.setUserId(command.getUserId());
                alias.setCommandId(command.getId());
            }
        }
        int idx = 0;
        for (UserCommandAction action : command.getActions()) {
            if (action.getAction().length() != 0) {
                action.setIdx(idx++);
                action.setUserId(command.getUserId());
                action.setCommandId(command.getId());
            }
        }
        for (UserCommandPermission permission : command.getPermissions()) {
            if (permission.getPermission().length() != 0) {
                permission.setUserId(command.getUserId());
                permission.setCommandId(command.getId());
            }
        }
        res += commandRepository.createUserCommandAliases(command);
        res += commandRepository.saveUserCommandActions(command);
        res += commandRepository.saveUserCommandPermissions(command);
        return res;
    }

    public List<UserCommand> getCommands(final Long userId) {
        List<UserCommand> commands = commandRepository.getCustomCommands(userId);
        for (UserCommand command : commands) {
            command.setAliases(commandRepository.getAliases(userId, command.getId()));
            command.setPermissions(commandRepository.getPermissions(userId, command.getId()));
            command.setActions(commandRepository.getActions(userId, command.getId()));
        }
        return commands;
    }


    public List<UserCommand> getEnabledCommands(final Long userId) {
        List<UserCommand> commands = commandRepository.getEnabledCustomCommands(userId);
        for (UserCommand command : commands) {
            command.setAliases(commandRepository.getAliases(userId, command.getId()));
            command.setPermissions(commandRepository.getPermissions(userId, command.getId()));
            command.setActions(commandRepository.getActions(userId, command.getId()));
        }
        return commands;
    }

    public UserCommand getCommand(final User user, final long commandId) {
        Optional<UserCommand> commandOpt = commandRepository.getCustomCommand(user.getId(), commandId);
        if (commandOpt.isPresent()) {
            UserCommand command = commandOpt.get();
            command.setAliases(commandRepository.getAliases(user.getId(), command.getId()));
            command.setPermissions(commandRepository.getPermissions(user.getId(), command.getId()));
            command.setActions(commandRepository.getActions(user.getId(), command.getId()));
            return command;
        }
        return null;
    }

    public boolean hasAccess(final long userId, final long commandId) {
        return commandRepository.hasAccess(userId, commandId);
    }

    public int updateStatus(final long commandId, final boolean status) {
        return commandRepository.updateStatus(commandId, status);
    }

    public int delete(final long userId, final long commandId) {
        return commandRepository.delete(userId, commandId);
    }
}
