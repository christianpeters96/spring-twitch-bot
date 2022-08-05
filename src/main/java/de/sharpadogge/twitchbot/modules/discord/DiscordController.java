package de.sharpadogge.twitchbot.modules.discord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/discord")
public class DiscordController {

    @Autowired
    public DiscordController() {
    }

    /*@GetMapping("/live/push")
    public ResponseEntity<String> livePush(@RequestHeader("Authorization") final String authorization) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            boolean success = discordService.pushLiveWebhook(userOpt.get(), WEBHOOK);
            return new ResponseEntity<>(success ? "success" : "failure (not live)", HttpStatus.OK);
        }
        return new ResponseEntity<>("failure", HttpStatus.UNAUTHORIZED);
    }*/
}
