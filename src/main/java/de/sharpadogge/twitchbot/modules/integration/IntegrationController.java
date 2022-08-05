package de.sharpadogge.twitchbot.modules.integration;

import de.sharpadogge.twitchbot.config.ApplicationConfiguration;
import de.sharpadogge.twitchbot.modules.integration.entity.IntegrationAuth;
import de.sharpadogge.twitchbot.modules.integration.model.IntegrationSettings;
import de.sharpadogge.twitchbot.modules.twitch.TwitchAPI;
import de.sharpadogge.twitchbot.modules.user.User;
import de.sharpadogge.twitchbot.modules.user.UserRepository;
import de.sharpadogge.twitchbot.modules.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/api/integration")
public class IntegrationController {

    private final ApplicationConfiguration applicationConfiguration;

    private final IntegrationRepository integrationRepository;

    private final IntegrationService integrationService;

    private final UserRepository userRepository;

    private final TwitchAPI twitchAPI;

    @Autowired
    public IntegrationController(final ApplicationConfiguration applicationConfiguration,
                                 final IntegrationRepository integrationRepository,
                                 final IntegrationService integrationService,
                                 final UserRepository userRepository,
                                 final TwitchAPI twitchAPI) {

        this.applicationConfiguration = applicationConfiguration;
        this.integrationRepository = integrationRepository;
        this.integrationService = integrationService;
        this.userRepository = userRepository;
        this.twitchAPI = twitchAPI;
    }

    @GetMapping
    public ResponseEntity<Map<String, IntegrationSettings>> getIntegrations(@RequestHeader("Authorization") final String authorization) {
        String token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Map<String, IntegrationSettings> output = integrationService.validateAndReturnUserIntegrations(user.getId());
            output.remove(AvailableIntegration.TWITCH.getKey());
            return new ResponseEntity<>(output, HttpStatus.OK);
        }
        Map<String, IntegrationSettings> twitch = new HashMap<>();
        twitch.put(AvailableIntegration.TWITCH.getKey(), integrationService.getIntegration(AvailableIntegration.TWITCH));
        return new ResponseEntity<>(twitch, HttpStatus.OK);
    }

    @GetMapping("/{provider}")
    public ResponseEntity<IntegrationSettings> getIntegration(@RequestHeader("Authorization") final String authorization, @PathVariable final String provider) {
        String token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return new ResponseEntity<>(integrationService.getUserIntegration(user.getId(), provider, true).getSettings(), HttpStatus.OK);
        }
        return new ResponseEntity<>(integrationService.getIntegration(AvailableIntegration.TWITCH), HttpStatus.OK);
    }

    @DeleteMapping("/{provider}")
    public ResponseEntity<Boolean> removeUserIntegration(@RequestHeader("Authorization") final String authorization, @PathVariable final String provider) {
        String token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(token);
        if (userOpt.isPresent()) {
            boolean revoked = integrationService.revokeToken(provider, userOpt.get());
            if (revoked) {
                int res = integrationService.unbind(provider, userOpt.get().getId());
                if (res != 0) {
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }
            }
            else return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{provider}/auth-url")
    public ResponseEntity<String> getAuthorizationUrl(@PathVariable final String provider) {
        IntegrationSettings integrationSettings = integrationService.getIntegration(provider);
        if (integrationSettings != null) {
            String url = integrationService.createAuthorizationUrl(provider, integrationSettings);
            return new ResponseEntity<>(url, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{provider}/authenticate")
    public void getTokenFromAuthorizationCode(@PathVariable final String provider, @RequestParam final String code, HttpServletRequest request, HttpServletResponse response) {
        if (provider.equalsIgnoreCase(AvailableIntegration.TWITCH.getKey())) {
            IntegrationAuth twitchAuth = integrationService.authorizeWithCode(null, provider, code);
            User user = twitchAPI.getUser(twitchAuth.getAccessToken());
            // String token = StringUtils.createUniqueHash(user.getName());
            String token = twitchAuth.getAccessToken();
            user.setToken(token);
            userRepository.insertOrUpdate(user);
            Optional<IntegrationAuth> authOpt = integrationRepository.findByAccessToken(twitchAuth.getAccessToken());
            if (authOpt.isPresent()) {
                Optional<User> userOpt = userRepository.getUserByName(user.getName());
                userOpt.ifPresent(u -> {
                    integrationService.unbind(AvailableIntegration.TWITCH.getKey(), u.getId());
                    integrationService.bind(authOpt.get().getId(), u.getId());
                });
                Cookie cookie = new Cookie("token", token);
                cookie.setDomain("");
                cookie.setPath("/");
                response.addCookie(cookie);
            }

            response.setStatus(302);
            response.setHeader("Location", applicationConfiguration.getHost() + "/");
            return;
        }
        else {
            String token = null;
            Cookie[] cookies = request.getCookies();

            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
            if (token != null) {
                Optional<User> userOpt = userRepository.getUserByToken(token);
                if (userOpt.isPresent()) {
                    User user = userOpt.get();
                    integrationService.authorizeWithCode(user, provider, code);
                }
            }
        }

        response.setStatus(302);
        response.setHeader("Location", applicationConfiguration.getHost() + "/#/user/settings/auth");
    }
}
