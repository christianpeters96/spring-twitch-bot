package de.sharpadogge.twitchbot.modules.command;

import de.sharpadogge.twitchbot.modules.command.entity.UserCommand;
import de.sharpadogge.twitchbot.modules.command.entity.UserCommandAlias;
import de.sharpadogge.twitchbot.modules.user.User;
import de.sharpadogge.twitchbot.modules.user.UserRepository;
import de.sharpadogge.twitchbot.modules.utils.StringUtils;
import de.sharpadogge.twitchbot.modules.utils.ToastNotification;
import de.sharpadogge.twitchbot.modules.utils.ToastResponse;
import de.sharpadogge.twitchbot.modules.utils.ToastUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/commands")
public class CommandController {

    private final UserRepository userRepository;

    private final CommandService commandService;

    @Autowired
    public CommandController(final UserRepository userRepository,
                             final CommandService commandService) {
        this.userRepository = userRepository;
        this.commandService = commandService;
    }

    @GetMapping
    @SuppressWarnings("OptionalIsPresent")
    public ResponseEntity<List<UserCommand>> getCommands(@RequestHeader("Authorization") final String authorization) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            return new ResponseEntity<>(commandService.getCommands(userOpt.get().getId()), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{commandId}")
    public ResponseEntity<UserCommand> getCommand(@RequestHeader("Authorization") final String authorization, @PathVariable("commandId") final Long commandId) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            UserCommand command = commandService.getCommand(user, commandId);
            if (command != null) {
                return new ResponseEntity<>(command, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/{commandId}")
    public ResponseEntity<ToastResponse<Object>> updateCommand(@RequestHeader("Authorization") final String authorization, @PathVariable("commandId") final Long commandId, @RequestBody final UserCommand command) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (command.getId() == null) command.setId(commandId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Pattern validPattern = Pattern.compile("^!?[öäüßA-z0-9]+$");
            // check name
            if (command.getName().trim().length() == 0) {
                return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Bitte gebe dem Befehl einen Namen"));
            }
            // add "no description"
            if (command.getDescription().trim().length() == 0) {
                command.setDescription("-");
            }
            // check main command string
            if (command.getCmd().trim().length() == 0 || !validPattern.matcher(command.getCmd().trim()).matches()) {
                return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Bitte gebe einen gültigen Befehl ein"));
            }
            // delete invalid aliases (empty gets deleted) and add "!" if missing
            List<String> cmds = new ArrayList<>();
            cmds.add(command.getCmd());
            for (UserCommandAlias alias : command.getAliases()) {
                if (!validPattern.matcher(alias.getAlias()).matches()) {
                    alias.setAlias("");
                } else {
                    if (!alias.getAlias().startsWith("!")) {
                        alias.setAlias("!" + alias.getAlias());
                    }
                    if (!cmds.contains(alias.getAlias())) {
                        cmds.add(alias.getAlias());
                    } else {
                        alias.setAlias("");
                    }
                }
            }
            // add "!" if missing
            command.setCmd(command.getCmd().trim());
            if (!command.getCmd().startsWith("!")) {
                command.setCmd("!" + command.getCmd());
            }

            command.setUserId(user.getId());
            int res = commandService.update(command);
            if (res == -1) {
                return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Dieser Befehl existiert nicht"));
            }
            return ToastUtils.createResponse(true, HttpStatus.OK, new ToastNotification("success", "Erfolg", "Ein neuer Befehl wurde erstellt"));
        }
        return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Du bist nicht autorisiert"));
    }

    @DeleteMapping("/{commandId}")
    public ResponseEntity<ToastResponse<Object>> deleteCommand(@RequestHeader("Authorization") final String authorization, @PathVariable("commandId") final Long commandId) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent() && commandService.hasAccess(userOpt.get().getId(), commandId)) {
            int res = commandService.delete(userOpt.get().getId(), commandId);
            if (res == 0) {
                return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Der Befehl konnte nicht gelöscht werden"));
            }
            return ToastUtils.createResponse(true, HttpStatus.OK, new ToastNotification("success", "Erfolg", "Der Befehl wurde gelöscht"));
        }
        return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Du bist nicht autorisiert"));
    }

    @PutMapping
    public ResponseEntity<ToastResponse<Object>> createNewCommand(@RequestHeader("Authorization") final String authorization, @RequestBody final UserCommand command) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Pattern validPattern = Pattern.compile("^!?[öäüßA-z0-9]+$");
            // check name
            if (command.getName().trim().length() == 0) {
                return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Bitte gebe dem Befehl einen Namen"));
            }
            // add "no description"
            if (command.getDescription().trim().length() == 0) {
                command.setDescription("-");
            }
            // check main command string
            if (command.getCmd().trim().length() == 0 || !validPattern.matcher(command.getCmd().trim()).matches()) {
                return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Bitte gebe einen gültigen Befehl ein"));
            }
            // delete invalid aliases (empty gets deleted) and add "!" if missing
            List<String> cmds = new ArrayList<>();
            cmds.add(command.getCmd());
            for (UserCommandAlias alias : command.getAliases()) {
                if (!validPattern.matcher(alias.getAlias()).matches()) {
                    alias.setAlias("");
                } else {
                    if (!alias.getAlias().startsWith("!")) {
                        alias.setAlias("!" + alias.getAlias());
                    }
                    if (!cmds.contains(alias.getAlias())) {
                        cmds.add(alias.getAlias());
                    } else {
                        alias.setAlias("");
                    }
                }
            }
            // add "!" if missing
            command.setCmd(command.getCmd().trim());
            if (!command.getCmd().startsWith("!")) {
                command.setCmd("!" + command.getCmd());
            }

            command.setUserId(user.getId());
            int res = commandService.create(command);
            if (res == -1) {
                return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Dieser Befehl existiert bereits"));
            }
            return ToastUtils.createResponse(true, HttpStatus.OK, new ToastNotification("success", "Erfolg", "Ein neuer Befehl wurde erstellt"));
        }
        return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Du bist nicht autorisiert"));
    }

    @PostMapping("/{commandId}/status")
    public ResponseEntity<ToastResponse<Object>> updateCommandStatus(@RequestHeader("Authorization") final String authorization, @PathVariable("commandId") final Long commandId, @RequestBody Map<String, Object> body) {
        String _token = StringUtils.getTokenFromHeader(authorization);
        Optional<User> userOpt = userRepository.getUserByToken(_token);
        if (userOpt.isPresent()) {
            if (body.containsKey("active") && body.get("active") instanceof Boolean) {
                Boolean status = (Boolean) body.get("active");
                int res = commandService.updateStatus(commandId, status);
                if (res == 0) {
                    return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Der Befehl konnte nicht aktualisiert werden"));
                }
                return ToastUtils.createResponse(true, HttpStatus.OK, new ToastNotification("success", "Erfolg", "Der Befehl wurde " + (status ? "aktiviert" : "deaktiviert")));
            }
            return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Bei der Anfrage ist etwas schiefgelaufen"));
        }
        return ToastUtils.createResponse(false, HttpStatus.OK, new ToastNotification("error", "Fehlschlag", "Du bist nicht autorisiert"));
    }
}
