package de.sharpadogge.twitchbot.modules.bot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

import de.sharpadogge.twitchbot.modules.bot.socket.WebSocket;
import de.sharpadogge.twitchbot.modules.bot.socket.event.ChannelEventRepository;
import de.sharpadogge.twitchbot.modules.user.User;
import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.feature.twitch.TwitchSupport;
import org.springframework.context.ApplicationContext;

public class BotClient {

    public static ApplicationContext context;
    private final Client client;
    private final String channel;
    private User user;
    private WebSocket socket;

    private Consumer<String> outputHandler;

    public BotClient(final String channelName, final String authToken, final Consumer<String> outputHandler) {
        this(channelName, authToken, outputHandler, false);
    }

    public BotClient(final String channelName, final String authToken, final Consumer<String> outputHandler, final boolean debug) {
        this.channel = "#" + channelName;

        Client.Builder builder = Client.builder();
        
        if (debug) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            builder.listeners().input(line -> {
                System.out.println(sdf.format(new Date()) + ' ' + "[I] " + line);
                outputHandler.accept(line);
            });
            builder.listeners().output(line -> System.out.println(sdf.format(new Date()) + ' ' + "[O] " + line));
            builder.listeners().exception(Throwable::printStackTrace);
        }
        else {
            builder.listeners().input(outputHandler);
        }

        this.client = builder
            .server().host("irc.chat.twitch.tv").port(6697)
            .password(authToken).then()
            .nick("plmnmwhsmtngsfll9913")
            .build();

        TwitchSupport.addSupport(this.client);
    }

    public void addEventListener(EventListener listener) {
        listener.setClient(this);
        this.client.getEventManager().registerEventListener(listener);
    }

    public void connect() {
        this.client.connect();
        this.client.addChannel(this.channel);
        this.client.sendMessage(this.channel, "/color OrangeRed");
        //this.client.sendMessage(this.channel, "Connected");
    }

    public void disconnect() {
        //this.client.sendMessage(this.channel, "Bye");
        this.socket.disconnect();
        this.client.removeChannel(this.channel);
        this.client.shutdown("Disconnect");
    }

    public void connectWebsocket(long channelId, String authToken) {
        this.socket = new WebSocket("wss://pubsub-edge.twitch.tv");
        this.socket.setAuthToken(authToken);
        this.socket.setUser(user);
        this.socket.setEventHandler(event -> {
            System.out.println(event);
            context.getBean(ChannelEventRepository.class)
                .insertChannelEvent(event);
        });
        this.socket.connect();
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext context) {
        BotClient.context = context;
    }

    public Client getClient() {
        return client;
    }

    public String getChannel() {
        return channel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WebSocket getSocket() {
        return socket;
    }

    public void setSocket(WebSocket socket) {
        this.socket = socket;
    }

    public Consumer<String> getOutputHandler() {
        return outputHandler;
    }

    public void setOutputHandler(Consumer<String> outputHandler) {
        this.outputHandler = outputHandler;
    }
}