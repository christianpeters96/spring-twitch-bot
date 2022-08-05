package de.sharpadogge.twitchbot.modules.spam;

import de.sharpadogge.twitchbot.modules.spam.entity.UserSpamFilter;
import de.sharpadogge.twitchbot.modules.twitch.Roles;
import de.sharpadogge.twitchbot.modules.user.User;
import de.sharpadogge.twitchbot.modules.user.UserRepository;
import de.sharpadogge.twitchbot.modules.utils.StringUtils;
import de.sharpadogge.twitchbot.modules.utils.ToastNotification;
import de.sharpadogge.twitchbot.modules.utils.ToastResponse;
import de.sharpadogge.twitchbot.modules.utils.ToastUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/spam")
public class UserSpamFilterController {

    private final UserRepository userRepository;

    private final UserSpamFilterService userSpamFilterService;

    @Autowired
    public UserSpamFilterController(UserRepository userRepository,
                                    UserSpamFilterService userSpamFilterService) {
        this.userRepository = userRepository;
        this.userSpamFilterService = userSpamFilterService;
    }

    @GetMapping("/settings/{filter}")
    public ResponseEntity<UserSpamFilter> getSettings(@RequestHeader("Authorization") final String authorization, @PathVariable final String filter) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (SupportedSpamFilter.byIdentifier(filter) != null) {
                return new ResponseEntity<>(userSpamFilterService.getFilter(user.getId(), filter.toLowerCase()), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/blacklist/word")
    public ResponseEntity<List<String>> getBlacklistedWords(@RequestHeader("Authorization") final String authorization) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return new ResponseEntity<>(userSpamFilterService.getBlacklistedWords(user.getId()), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/settings", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> saveSettings(@RequestHeader("Authorization") final String authorization, @RequestBody final UserSpamFilter settings) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            settings.setUserId(userOpt.get().getId());
            int result = userSpamFilterService.saveFilter(settings);
            if (result != 0) return new ResponseEntity<>(true, HttpStatus.OK);
            else return new ResponseEntity<>(false, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/blacklist/word", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ToastResponse<Object>> addBlacklistedWord(@RequestHeader("Authorization") final String authorization, @RequestBody final Map<String, String> map) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            if (map.containsKey("string")) {
                int res = userSpamFilterService.addBlacklistedWord(userOpt.get().getId(), map.get("string"));
                if (res == 1) {
                    return ToastUtils.createResponse(true, HttpStatus.OK, null);
                }
                else {
                    return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Das Wort konnte nicht hinzugefügt werden. Eventuell existiert es bereits."));
                }
            }
            else {
                return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Bei der Anfrage ist etwas schiefgelaufen"));
            }
        }
        return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Du bist nicht autorisiert"));
    }

    @RequestMapping(value = "/blacklist/word", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ToastResponse<Object>> removeBlacklistedWord(@RequestHeader("Authorization") final String authorization, @RequestBody final Map<String, String> map) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            if (map.containsKey("string")) {
                int res = userSpamFilterService.removeBlacklistedWord(userOpt.get().getId(), map.get("string"));
                if (res == 1) {
                    return ToastUtils.createResponse(true, HttpStatus.OK, null);
                }
                else {
                    return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Das Wort konnte nicht gelöscht werden"));
                }
            }
            else {
                return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Bei der Anfrage ist etwas schiefgelaufen"));
            }
        }
        return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Du bist nicht autorisiert"));
    }

    @GetMapping("/exemption/roles")
    public ResponseEntity<List<String>> getExemptibleRoles(@RequestHeader("Authorization") final String authorization) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            return new ResponseEntity<>(Roles.spamWebList(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/exemption/{filter}", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getExemptionsForFilter(@RequestHeader("Authorization") final String authorization, @PathVariable final String filter) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            if (SupportedSpamFilter.byIdentifier(filter) != null) {
                return new ResponseEntity<>(userSpamFilterService.getExemptions(userOpt.get().getId(), filter), HttpStatus.OK);
            }
            throw new RuntimeException("Unsupported filter type");
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/exemption/{filter}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ToastResponse<Object>> addExemptionForFilter(@RequestHeader("Authorization") final String authorization, @PathVariable final String filter, @RequestBody final Map<String, String> map) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            if (map.containsKey("string") && SupportedSpamFilter.byIdentifier(filter) != null) {
                if (map.get("string").split(":").length == 2 && map.get("string").split(":")[1].length() != 0) {
                    int res = userSpamFilterService.addExemption(userOpt.get().getId(), filter, map.get("string"));
                    if (res == 1) {
                        return ToastUtils.createResponse(true, HttpStatus.OK, null);
                    }
                    else {
                        return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Die Ausnahme konnte nicht hinzugefügt werden. Eventuell existiert sie bereits."));
                    }
                }
                else {
                    return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Es wurde kein Wert angegeben"));
                }
            }
            else {
                return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Bei der Anfrage ist etwas schiefgelaufen"));
            }

        }
        return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Du bist nicht autorisiert"));
    }

    @RequestMapping(value = "/exemption/{filter}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ToastResponse<Object>> removeExemptionForFilter(@RequestHeader("Authorization") final String authorization, @PathVariable final String filter, @RequestBody final Map<String, String> map) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            if (map.containsKey("string") && SupportedSpamFilter.byIdentifier(filter) != null) {
                int res = userSpamFilterService.removeExemption(userOpt.get().getId(), filter, map.get("string"));
                if (res == 1) {
                    return ToastUtils.createResponse(true, HttpStatus.OK, null);
                }
                else {
                    return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Die Ausnahme konnte nicht gelöscht werden"));
                }
            }
            else {
                return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Bei der Anfrage ist etwas schiefgelaufen"));
            }
        }
        return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Du bist nicht autorisiert"));
    }
}
