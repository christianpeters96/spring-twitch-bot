package de.sharpadogge.twitchbot.modules.user;

import de.sharpadogge.twitchbot.modules.integration.AvailableIntegration;
import de.sharpadogge.twitchbot.modules.integration.IntegrationService;
import de.sharpadogge.twitchbot.modules.integration.model.Integration;
import de.sharpadogge.twitchbot.modules.twitch.TwitchAPI;
import de.sharpadogge.twitchbot.modules.utils.StringUtils;
import de.sharpadogge.twitchbot.modules.vars.VariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    private final IntegrationService integrationService;

    private final VariableService variableService;

    private final TwitchAPI twitchAPI;

    @Autowired
    public UserController(final UserRepository userRepository,
                          final IntegrationService integrationService,
                          final VariableService variableService,
                          final TwitchAPI twitchAPI) {
        this.userRepository = userRepository;
        this.integrationService = integrationService;
        this.variableService = variableService;
        this.twitchAPI = twitchAPI;
    }

    @GetMapping
    public ResponseEntity<User> getUser(@RequestHeader("Authorization") final String authorization) {
        String token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(token);
        if (userOpt.isPresent()) {
            Integration integration = integrationService.getUserIntegration(userOpt.get().getId(), AvailableIntegration.TWITCH.getKey(), true);
            if (integration.getAuth() != null) {
                User user = userOpt.get();

                String tsString = variableService.get(user.getId(), "twitch_user_ts").getValue();
                if (tsString == null || tsString.length() == 0) tsString = "0";
                if ((System.currentTimeMillis() - Long.parseLong(tsString)) > 30 * 60_000) {
                    User twitchUser = twitchAPI.getUser(integration.getAuth().getAccessToken());
                    user.setEmail(twitchUser.getEmail());
                    user.setLogo(twitchUser.getLogo());
                    user.setName(twitchUser.getName());
                    user.setPartner(twitchUser.getPartner());
                    user.setType(twitchUser.getType());
                    user.setTwitchUserId(twitchUser.getTwitchUserId());
                    userRepository.insertOrUpdate(user);
                    variableService.set(user.getId(), "twitch_user_ts", System.currentTimeMillis()+"");
                }

                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/tmi")
    public ResponseEntity<Object> setBotToken(@RequestParam("token") final String token, @RequestHeader("Authorization") final String authorization) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setBotToken(token);
            userRepository.insertOrUpdate(user);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
