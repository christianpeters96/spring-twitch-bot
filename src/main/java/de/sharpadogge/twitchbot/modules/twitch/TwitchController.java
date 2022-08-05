package de.sharpadogge.twitchbot.modules.twitch;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/twitch")
public class TwitchController {

    @GetMapping("/roles")
    public ResponseEntity<List<String>> getRoles() {
        return new ResponseEntity<>(Roles.webList(), HttpStatus.OK);
    }
}
