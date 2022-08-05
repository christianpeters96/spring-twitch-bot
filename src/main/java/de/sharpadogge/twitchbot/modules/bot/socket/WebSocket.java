package de.sharpadogge.twitchbot.modules.bot.socket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.sharpadogge.twitchbot.modules.bot.socket.model.ListenResponse;
import de.sharpadogge.twitchbot.modules.bot.socket.model.ListenableTopic;
import de.sharpadogge.twitchbot.modules.bot.socket.event.ChannelEvent;
import de.sharpadogge.twitchbot.modules.user.User;
import de.sharpadogge.twitchbot.modules.utils.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class WebSocket extends Endpoint {

    private final Logger log = LoggerFactory.getLogger(WebSocket.class);

    private final String server;

    private Consumer<ChannelEvent> eventHandler;

    private User user;

    private String authToken;

    private Session session;

    private ObjectMapper mapper = new ObjectMapper();

    Map<String, ListenableTopic> topicMap = new HashMap<>();

    public WebSocket(String server) {
        this.server = server;
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        this.session = session;
        session.addMessageHandler(String.class, this::onMessage);
        listenAll();
    }

    public void onMessage(final String message) {
        parseMessage(message);
        try {
            if (mapper.readTree(message).get("type").asText().equalsIgnoreCase("response")) {
                ListenResponse response = mapper.readValue(message, ListenResponse.class);
                if (response.getError().length() == 0) {
                    for (Map.Entry<String, ListenableTopic> entry : topicMap.entrySet()) {
                        if (entry.getValue().getNonce().equalsIgnoreCase(response.getNonce()) && entry.getValue().getPending()) {
                            entry.getValue().setNonce("");
                            entry.getValue().setPending(false);
                            entry.getValue().setListening(true);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
//        log.info(message.trim());
    }

    public void connect() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            ClientEndpointConfig config = ClientEndpointConfig.Builder.create()
                    .configurator(new ClientEndpointConfig.Configurator())
                    .build();
            container.connectToServer(this, config, new URI(server));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        try {
            if (this.session != null) this.session.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listenAll() {
        // TODO: use real twitch_user_id
        listen("chat_moderator_actions." + user.getTwitchUserId());
        listen("channel-subscribe-events-v1." + user.getTwitchUserId());
        // listen("channel-points-channel-v1." + user.getTwitchUserId());
        // listen("channel-bits-badge-unlocks." + user.getTwitchUserId());
        listen("channel-bits-events-v2." + user.getTwitchUserId());
        // listen("channel-bits-events-v1." + user.getTwitchUserId());
    }

    public void listen(String topic) {
        try {
            if (topicMap.containsKey(topic)) {
                topicMap.get(topic).setNonce(StringUtils.createUniqueHash());
                topicMap.get(topic).setPending(true);
            }
            else {
                ListenableTopic listenableTopic = new ListenableTopic(topic, StringUtils.createUniqueHash());
                listenableTopic.setPending(true);
                topicMap.put(topic, listenableTopic);
            }
            ListenableTopic listenableTopic = topicMap.get(topic);

            ObjectNode json = mapper.createObjectNode();
            ObjectNode data = mapper.createObjectNode();

            data.putArray("topics");
            ((ArrayNode) data.get("topics")).add(listenableTopic.getTopic());
            data.put("auth_token", this.authToken);

            json.put("type", "LISTEN");
            json.put("nonce", listenableTopic.getNonce());
            json.set("data", data);

            session.getBasicRemote().sendText(json.toString());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void unlistenAll() {
        // TODO: use real twitch_user_id
        unlisten("chat_moderator_actions." + user.getTwitchUserId());
        unlisten("channel-subscribe-events-v1." + user.getTwitchUserId());
        // unlisten("channel-points-channel-v1." + user.getTwitchUserId());
        // unlisten("channel-bits-badge-unlocks." + user.getTwitchUserId());
        unlisten("channel-bits-events-v2." + user.getTwitchUserId());
        // unlisten("channel-bits-events-v1." + user.getTwitchUserId());
    }

    public void unlisten(String topic) {
        try {
            topicMap.remove(topic);

            ObjectNode json = mapper.createObjectNode();
            ObjectNode data = mapper.createObjectNode();

            data.putArray("topics");
            ((ArrayNode) data.get("topics")).add(topic);
            data.put("auth_token", this.authToken); // TODO: set token / and refresh

            json.put("type", "UNLISTEN");
            json.set("data", data);

            session.getBasicRemote().sendText(json.toString());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void parseMessage(final String jsonResponse) {
        log.info(jsonResponse);
        try {
            JsonNode node = mapper.readTree(jsonResponse);
            if (node.has("type") && node.get("type").textValue().equalsIgnoreCase("message") &&
                node.has("data") && node.get("data").isObject()) {

                if (node.get("data").has("topic")) {
                    String topic = node.get("data").get("topic").textValue().replaceAll("\\..+$", "");
                    String message = node.get("data").get("message").textValue();

                    ChannelEvent event = new ChannelEvent();
                    event.setCreatedAt(LocalDateTime.now());
                    event.setUser(getUser());
                    event.setTopic(topic);
                    event.setJson(message);

                    eventHandler.accept(event);
                }
            }
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Map<String, ListenableTopic> getTopicMap() {
        return topicMap;
    }

    public void setTopicMap(Map<String, ListenableTopic> topicMap) {
        this.topicMap = topicMap;
    }

    public String getServer() {
        return server;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Logger getLog() {
        return this.log;
    }


    public Consumer<ChannelEvent> getEventHandler() {
        return this.eventHandler;
    }

    public void setEventHandler(Consumer<ChannelEvent> eventHandler) {
        this.eventHandler = eventHandler;
    }

    public ObjectMapper getMapper() {
        return this.mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
}
