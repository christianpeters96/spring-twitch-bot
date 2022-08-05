package de.sharpadogge.twitchbot.modules.bot.data;

import java.util.*;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.client.library.feature.twitch.messagetag.Badges;
import org.kitteh.irc.client.library.feature.twitch.messagetag.Color;
import org.kitteh.irc.client.library.feature.twitch.messagetag.Emotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.sharpadogge.twitchbot.modules.user.User;
import de.sharpadogge.twitchbot.modules.user.UserRepository;
import de.sharpadogge.twitchbot.modules.utils.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/data")
public class TwitchBotDataController {

    private final UserRepository userRepository;

    private final TwitchBotDataRepository twitchBotDataRepository;

    @Autowired
    public TwitchBotDataController(UserRepository userRepository, final TwitchBotDataRepository twitchBotDataRepository) {
        this.userRepository = userRepository;
        this.twitchBotDataRepository = twitchBotDataRepository;
    }

    @RequestMapping(path = "/weekly", method = RequestMethod.GET)
    public ResponseEntity<Object> getWeeklyStats(@RequestHeader("Authorization") final String authorization) {
        String token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(token);
        if (userOpt.isPresent()) {
            return new ResponseEntity<>(twitchBotDataRepository.getWeeklyStats("#" + userOpt.get().getName()), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(path = "/top", method = RequestMethod.GET)
    public ResponseEntity<Object> getTopData(@RequestHeader("Authorization") final String authorization) {
        String token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(token);
        if (userOpt.isPresent()) {
            return new ResponseEntity<>(twitchBotDataRepository.getTopStats("#" + userOpt.get().getName()), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(path = "/chat", method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> getLatestChatEvents(@RequestParam final String channel) {
        final String channelName = "#" + channel;
        if (!TwitchBotDataHolder.latestMessages.containsKey(channelName)) {
            TwitchBotDataHolder.latestMessages.put(channelName, new ArrayList<>());
        }

        List<Map<String, Object>> output = new ArrayList<>();
        for (ChannelMessageEvent event : TwitchBotDataHolder.latestMessages.get(channelName)) {
            Map<String, Object> map = new HashMap<>();
            map.put("author", event.getActor().getNick());
            map.put("message", event.getMessage());

            event.getTag("color", Color.class).ifPresent(color -> map.put("color", color));
            event.getTag("tmi-sent-ts").ifPresent(ts -> map.put("ts", ts));
            event.getTag("room-id").ifPresent(ts -> map.put("room_id", ts));

            Optional<Badges> badgesOpt = event.getTag("badges", Badges.class);
            if (badgesOpt.isPresent()) {
                Badges badges = badgesOpt.get();
                map.put("badges", badges.getBadges());
            } else {
                map.put("badges", new ArrayList<>());
            }

            Optional<Emotes> emotesOpt = event.getTag("emotes", Emotes.class);
            if (emotesOpt.isPresent()) {
                Emotes emotes = emotesOpt.get();
                map.put("emotes", emotes.getEmotes());
            } else {
                map.put("emotes", new ArrayList<>());
            }

            output.add(map);
        }
        return new ResponseEntity<>(output, HttpStatus.OK);
    }
}
//select date, sum(message_count), sum(command_count), sum(timeout_count), sum(deleted_message_count) message_count from twitch_user_stats_daily where channel = '#sharpadogge' and date >= date(now()) - interval 7 day group by date
//select date, sum(message_count), sum(command_count), sum(timeout_count), sum(deleted_message_count) message_count from twitch_user_stats_daily where channel = '#sharpadogge' group by date order by date desc limit 7