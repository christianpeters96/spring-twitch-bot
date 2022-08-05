package de.sharpadogge.twitchbot.modules.bot;

import org.kitteh.irc.client.library.event.channel.ChannelJoinEvent;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.client.library.event.channel.ChannelPartEvent;
import org.kitteh.irc.client.library.event.user.PrivateMessageEvent;

import net.engio.mbassy.listener.Handler;

public abstract class EventListener {

    private BotClient client;

    public EventListener() {}

    public void setClient(final BotClient client) {
        this.client = client;
    }

    public BotClient getClient() {
        return this.client;
    }
    
    @Handler
    public void onMessage(ChannelMessageEvent event) {
        /*
        final String sender = event.getActor().getNick();
        final String message = event.getMessage();
        if (sender.equals("sharpadogge") && message.equalsIgnoreCase("!shutdown")) {
            client.disconnect();
        }
        */
    }

    @Handler
    public void onWhisper(PrivateMessageEvent event) {
        /*
        System.out.println("2:FROM>>> " + event.getActor().getNick());
        System.out.println("2:MSG >>> " + event.getMessage());
        */
    }

    @Handler
    public void onJoin(ChannelJoinEvent event) {
        /*
        Channel channel = event.getChannel();
        channel.sendMessage("Hi " + event.getUser().getNick() + "!");
        */
    }

    @Handler
    public void onPart(ChannelPartEvent event) {
        /*
        Channel channel = event.getChannel();
        channel.sendMessage("Bye " + event.getUser().getNick() + "!");
        */
    }
}