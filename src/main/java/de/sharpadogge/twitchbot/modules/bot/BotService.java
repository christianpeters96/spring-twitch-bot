package de.sharpadogge.twitchbot.modules.bot;

import de.sharpadogge.twitchbot.modules.bot.data.TwitchBotDataHolder;
import de.sharpadogge.twitchbot.modules.bot.listeners.AnalyticsListener;
import de.sharpadogge.twitchbot.modules.bot.listeners.AntiSpamListener;
import de.sharpadogge.twitchbot.modules.bot.listeners.ChatListener;
import de.sharpadogge.twitchbot.modules.bot.listeners.CommandListener;
import de.sharpadogge.twitchbot.modules.discord.DiscordService;
import de.sharpadogge.twitchbot.modules.integration.AvailableIntegration;
import de.sharpadogge.twitchbot.modules.integration.IntegrationService;
import de.sharpadogge.twitchbot.modules.integration.model.Integration;
import de.sharpadogge.twitchbot.modules.twitch.TwitchAPI;
import de.sharpadogge.twitchbot.modules.twitch.model.Stream;
import de.sharpadogge.twitchbot.modules.user.User;
import de.sharpadogge.twitchbot.modules.user.UserRepository;
import de.sharpadogge.twitchbot.modules.vars.VariableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BotService {
    
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final Map<String, BotClient> runningBots = new HashMap<>();

    private final UserRepository userRepository;

    private final VariableService variableService;

    private final DiscordService discordService;

    private final IntegrationService integrationService;

    private final TwitchAPI twitchAPI;

    @Autowired
    public BotService(final UserRepository userRepository,
                      final VariableService variableService,
                      final DiscordService discordService,
                      final IntegrationService integrationService,
                      final TwitchAPI twitchAPI) {

        this.userRepository = userRepository;
        this.variableService = variableService;
        this.discordService = discordService;
        this.integrationService = integrationService;
        this.twitchAPI = twitchAPI;
    }

    public boolean start(final String token) {
        Optional<User> userOpt = userRepository.getUserByToken(token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Boolean running = isRunning(token);
            if (running != null && !running) {
                BotClient bot = new BotClient(user.getName(), user.getBotToken(), line -> {
                    // invalid auth format -> ":tmi.twitch.tv NOTICE * :Improperly formatted auth"
                    // auth failure -> ":tmi.twitch.tv NOTICE * :Login authentication failed"
                    log.info(line);
                    boolean emptyToken = user.getBotToken() == null || user.getBotToken().length() == 0;
                    boolean invalidToken = line.contains("NOTICE") && (line.contains("Improperly formatted auth") || line.contains("Login authentication failed"));
                    if (emptyToken || invalidToken) {
                        stop(token);
                    }
                }, false);

                bot.setUser(user);

                bot.addEventListener(new AnalyticsListener());
                bot.addEventListener(new AntiSpamListener());
                bot.addEventListener(new CommandListener());
                bot.addEventListener(new ChatListener());

                runningBots.put(user.getName(), bot);
                bot.connect();
                
                Integration integration = integrationService.getUserIntegration(user.getId(), "twitch", true);
                bot.connectWebsocket(user.getTwitchUserId(), integration.getAuth().getAccessToken());
                return true;
            }
            else log.warn("Trying to start a twitch-bot which is already running");
        }
        else log.warn("Trying to start a bot with an invalid access token (unauthorized)");
        return false;
    }

    public boolean stop(final String token) {
        Optional<User> userOpt = userRepository.getUserByToken(token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Boolean running = isRunning(token);
            if (running != null && running) {
                runningBots.get(user.getName()).disconnect();
                runningBots.put(user.getName(), null);
                return true;
            }
            else log.warn("Trying to stop a twitch-bot which is not running");
        }
        else log.warn("Trying to stop a bot with an invalid access token (401 unauthorized)");
        return false;
    }

    public Boolean isRunning(final String token) {
        Optional<User> userOpt = userRepository.getUserByToken(token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return runningBots.containsKey(user.getName()) && runningBots.get(user.getName()) != null;
        }
        //else log.warn("Trying to request a bot status with an invalid access token (401 unauthorized)");
        return null;
    }

    public static Map<String, BotClient> getRunningBots() {
        return runningBots;
    }

    private void sendLiveNotification(User user, Stream stream) {
        String webhook = variableService.get(user.getId(), "discord_webhook").getValue();
        String ts = variableService.get(user.getId(), "discord_webhook_ts").getValue();
        if (webhook != null) {
            if (ts == null) ts = "0";
            long tsLong = Long.parseLong(ts);
            if ((System.currentTimeMillis() - tsLong) > 30_000) {
                boolean success = discordService.pushLiveWebhook(stream, webhook);
                if (success) {
                    variableService.set(user.getId(), "discord_webhook_ts", System.currentTimeMillis()+"");
                }
            }
        }
    }

    @Scheduled(initialDelay = 60_000, fixedDelay = 60_000 * 3)
    public void pubsubPing() {
        for (BotClient client : runningBots.values()) {
            Integration integration = integrationService.getUserIntegration(client.getUser().getId(), "twitch", true);
            client.getSocket().setAuthToken(integration.getAuth().getAccessToken());
        }

//        for (BotClient client : runningBots.values()) {
//            // TODO: make this work somehow
//            try {
//                client.getSocket().getSession().getBasicRemote().sendText("{\"type\":\"PING\"}");
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Scheduled(initialDelay = 60_000, fixedDelay = 60_000)
    public void checkStreamStatus() {
        for (BotClient client : runningBots.values()) {
            if (client != null && isRunning(client.getUser().getToken())) {
                Integration twitchIntegration = integrationService.getUserIntegration(client.getUser().getId(), AvailableIntegration.TWITCH.getKey(), true);
                Integration discordIntegration = integrationService.getUserIntegration(client.getUser().getId(), AvailableIntegration.DISCORD.getKey(), true);
                if (discordIntegration.getAuth() != null && twitchIntegration.getAuth() != null) {
                    // Stream liveStream = twitchAPI.getStream(client.getUser());
                    // if (TwitchBotDataHolder.streamOnline.containsKey(client.getChannel())) {
                    //     if (liveStream != null && !TwitchBotDataHolder.streamOnline.get(client.getChannel())) {
                    //         sendLiveNotification(client.getUser(), liveStream);
                    //     }
                    // }
                    // else {
                    //     sendLiveNotification(client.getUser(), liveStream);
                    // }
                    // TwitchBotDataHolder.streamOnline.put(client.getChannel(), liveStream != null);
                }
            }
        }
    }
}