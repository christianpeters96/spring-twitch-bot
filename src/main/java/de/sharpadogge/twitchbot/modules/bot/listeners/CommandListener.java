package de.sharpadogge.twitchbot.modules.bot.listeners;

import de.sharpadogge.twitchbot.modules.bot.AbstractCommand;
import de.sharpadogge.twitchbot.modules.bot.BotClient;
import de.sharpadogge.twitchbot.modules.bot.EventListener;
import de.sharpadogge.twitchbot.modules.bot.data.TwitchBotDataService;
import de.sharpadogge.twitchbot.modules.bot.commands.CommandExecutor;
import de.sharpadogge.twitchbot.modules.bot.utils.MessageUtils;
import de.sharpadogge.twitchbot.modules.bot.utils.UserUtils;
import de.sharpadogge.twitchbot.modules.command.entity.UserCommand;
import de.sharpadogge.twitchbot.modules.command.entity.UserCommandAction;
import de.sharpadogge.twitchbot.modules.command.entity.UserCommandPermission;
import de.sharpadogge.twitchbot.permission.PermissionUtils;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandListener extends EventListener {
    private final Map<String, LocalDateTime> delayEndTime = new HashMap<>();

    @Override
    public void onMessage(ChannelMessageEvent event) {
        final String author = event.getActor().getNick();
        final String message = event.getMessage();

        // TODO: Check Permissions
        if (message.startsWith(AbstractCommand.prefix)) {
            UserCommand userCommand = MessageUtils.getCommand(getClient().getUser().getId(), message);

            if (userCommand != null) {
                boolean isPermitted = userCommand.getPermissions().size() == 0;
                if (userCommand.getPermissions().size() != 0) {
                    List<String> permissions = userCommand.getPermissions().stream()
                            .map(UserCommandPermission::getPermission)
                            .collect(Collectors.toList());
                    isPermitted = PermissionUtils.checkCustomPermission(event, permissions);
                }
                if (isPermitted) {
                    final TwitchBotDataService dataService = BotClient.getContext().getBean(TwitchBotDataService.class);

                    String identifier = userCommand.getId() + "-" + userCommand.getCmd();
                    String identifier4user = identifier + ":" + author;
                    if (!delayEndTime.containsKey(identifier)) delayEndTime.put(identifier, LocalDateTime.now().minusSeconds(1));
                    if (!delayEndTime.containsKey(identifier4user)) delayEndTime.put(identifier4user, LocalDateTime.now().minusSeconds(1));

                    if (LocalDateTime.now().isAfter(delayEndTime.get(identifier)) && LocalDateTime.now().isAfter(delayEndTime.get(identifier4user))) {
                        if (UserUtils.canAfford(event.getChannel().getName(), author, userCommand.getCost())) {
                            delayEndTime.put(identifier, LocalDateTime.now().plusSeconds(userCommand.getGlobalDelay()));
                            delayEndTime.put(identifier4user, LocalDateTime.now().plusSeconds(userCommand.getUserDelay()));

                            CommandExecutor executor = new CommandExecutor(event, userCommand, getClient());
                            for (UserCommandAction action : userCommand.getActions()) {
                                executor.executeAction(action.getAction());

                                if (userCommand.getCost() > 0) {
                                    dataService.addActivityPoints(getClient().getChannel(), author, -userCommand.getCost());
                                }
                            }
                        }
                        else {
                            event.sendReply("@" + author + " Du hast nicht genügend Aktivitätspunkte (Punkte: " + UserUtils.getActivityPoints(event.getChannel().getName(), author) + " | Benötigt: " + userCommand.getCost() + ")");
                        }
                    }
                }
            }
        }
    }
}
//93