package de.sharpadogge.twitchbot.modules.bot.utils;

import de.sharpadogge.twitchbot.modules.bot.AbstractCommand;
import de.sharpadogge.twitchbot.modules.bot.BotClient;
import de.sharpadogge.twitchbot.modules.command.CommandService;
import de.sharpadogge.twitchbot.modules.command.entity.UserCommand;

import java.util.List;

public class MessageUtils {

    public static UserCommand getCommand(final Long userId, final String message) {
        if (message.startsWith(AbstractCommand.prefix)) {
            final String cmdString = message.substring(AbstractCommand.prefix.length()).split(" ")[0];
            CommandService commandService = BotClient.getContext().getBean(CommandService.class);
            List<UserCommand> userCommands = commandService.getEnabledCommands(userId);
            for (UserCommand command : userCommands) {
                if (command.getCmd().substring(AbstractCommand.prefix.length()).equalsIgnoreCase(cmdString)) {
                    return command;
                }
                else {
                    if (command.getAliases().stream()
                            .anyMatch(alias -> alias.getAlias().substring(AbstractCommand.prefix.length()).equalsIgnoreCase(cmdString))) {

                        return command;
                    }
                }
            }
        }
        return null;
    }
}
