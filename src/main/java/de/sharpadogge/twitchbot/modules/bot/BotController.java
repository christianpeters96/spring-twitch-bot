package de.sharpadogge.twitchbot.modules.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.sharpadogge.twitchbot.modules.utils.StringUtils;

@Controller
@RequestMapping("/api/twitch/bot")
public class BotController {

    private final BotService botService;

    @Autowired
    public BotController(BotService botService) {
        this.botService = botService;
    }

    @RequestMapping(path = "/start", method = RequestMethod.GET)
    public ResponseEntity<Boolean> startBot(@RequestHeader("Authorization") final String authorization) {
        String token = StringUtils.getTokenFromHeader(authorization);
        return new ResponseEntity<>(botService.start(token), HttpStatus.OK);
    }

    @RequestMapping(path = "/stop", method = RequestMethod.GET)
    public ResponseEntity<Boolean> stopBot(@RequestHeader("Authorization") final String authorization) {
        String token = StringUtils.getTokenFromHeader(authorization);
        return new ResponseEntity<>(botService.stop(token), HttpStatus.OK);
    }

    @RequestMapping(path = "/status", method = RequestMethod.GET)
    public ResponseEntity<Boolean> requestStatus(@RequestHeader("Authorization") final String authorization) {
        String token = StringUtils.getTokenFromHeader(authorization);
        return new ResponseEntity<>(botService.isRunning(token), HttpStatus.OK);
    }
}