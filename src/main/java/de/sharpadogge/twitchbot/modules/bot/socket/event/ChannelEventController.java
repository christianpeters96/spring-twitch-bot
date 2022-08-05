package de.sharpadogge.twitchbot.modules.bot.socket.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.sharpadogge.twitchbot.modules.bot.socket.event.type.BitsEventV2;
import de.sharpadogge.twitchbot.modules.bot.socket.event.type.ModerationActionEvent;
import de.sharpadogge.twitchbot.modules.bot.socket.event.type.SubscriptionsEvent;
import de.sharpadogge.twitchbot.modules.twitch.TwitchAPI;
import de.sharpadogge.twitchbot.modules.twitch.model.Event;
import de.sharpadogge.twitchbot.modules.user.User;
import de.sharpadogge.twitchbot.modules.user.UserRepository;
import de.sharpadogge.twitchbot.modules.utils.StringUtils;

@RestController
@RequestMapping("/api/channel/events")
public class ChannelEventController {

    private final TwitchAPI twitchAPI;

    private final UserRepository userRepository;
    
    private final ChannelEventRepository channelEventRepository;

    @Autowired
    public ChannelEventController(TwitchAPI twitchAPI, UserRepository userRepository, ChannelEventRepository channelEventRepository) {
        this.twitchAPI = twitchAPI;
        this.userRepository = userRepository;
        this.channelEventRepository = channelEventRepository;
    }

    @GetMapping("/followers")
    public ResponseEntity<List<Event>> getFollowers(@RequestHeader("Authorization") final String authorization) throws Exception {
        
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<Event> followers = twitchAPI.latestFollowers(_token, user.getTwitchUserId());
            return new ResponseEntity<>(followers, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAll(@RequestHeader("Authorization") final String authorization) throws Exception {
        
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            ObjectMapper mapper = new ObjectMapper();

            //List<? extends Object> outputList = new ArrayList<>();
            List<Map<String, Object>> output = new ArrayList<>();

            List<ChannelEvent> events = channelEventRepository.getChannelEventsForUser(user.getId());
            for (ChannelEvent event : events) {
                Map<String, Object> entry = new HashMap<>();
                entry.put("id", event.getId());
                entry.put("topic", event.getTopic());
                if (event.getTopic().equalsIgnoreCase("chat_moderator_actions")) {
                    String jsonData = mapper.readTree(event.getJson()).get("data").toString();
                    entry.put("data", mapper.readValue(jsonData, ModerationActionEvent.class));
                }
                if (event.getTopic().equalsIgnoreCase("channel-subscribe-events-v1")) {
                    entry.put("data", mapper.readValue(event.getJson(), SubscriptionsEvent.class));
                }
                if (event.getTopic().equalsIgnoreCase("channel-bits-events-v2")) {
                    entry.put("data", mapper.readValue(event.getJson(), BitsEventV2.class));
                }
                output.add(entry);
            }

            return new ResponseEntity<>(output, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
    
}
