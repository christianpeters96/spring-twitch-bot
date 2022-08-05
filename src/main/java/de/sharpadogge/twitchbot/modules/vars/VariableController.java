package de.sharpadogge.twitchbot.modules.vars;

import de.sharpadogge.twitchbot.modules.user.User;
import de.sharpadogge.twitchbot.modules.user.UserRepository;
import de.sharpadogge.twitchbot.modules.utils.StringUtils;
import de.sharpadogge.twitchbot.modules.vars.model.UserVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/vars")
public class VariableController {

    private final UserRepository userRepository;

    private final VariableService variableService;

    @Autowired
    public VariableController(final UserRepository userRepository,
                              final VariableService variableService) {
        this.userRepository = userRepository;
        this.variableService = variableService;
    }

    @GetMapping("/{variable}")
    public ResponseEntity<UserVariable> getVar(@RequestHeader("Authorization") final String authorization, @PathVariable String variable) {
        String token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(token);
        //noinspection OptionalIsPresent
        if (userOpt.isPresent()) {
            return new ResponseEntity<>(variableService.get(userOpt.get().getId(), variable), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(value = "/{variable}", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Boolean> deleteVar(@RequestHeader("Authorization") final String authorization, @PathVariable String variable, @RequestBody String value) {
        String token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(token);
        if (userOpt.isPresent()) {
            int res = variableService.set(userOpt.get().getId(), variable, value);
            return new ResponseEntity<>(res != 0, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{variable}")
    public ResponseEntity<Boolean> deleteVar(@RequestHeader("Authorization") final String authorization, @PathVariable String variable) {
        String token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(token);
        if (userOpt.isPresent()) {
            int res = variableService.remove(userOpt.get().getId(), variable);
            return new ResponseEntity<>(res != 0, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
