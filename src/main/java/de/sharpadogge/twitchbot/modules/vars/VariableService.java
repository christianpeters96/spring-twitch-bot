package de.sharpadogge.twitchbot.modules.vars;

import de.sharpadogge.twitchbot.modules.vars.model.UserVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VariableService {

    private final VariableRepository variableRepository;

    @Autowired
    public VariableService(VariableRepository variableRepository) {
        this.variableRepository = variableRepository;
    }

    public int set(Long userId, String name, String value) {
        Optional<UserVariable> variableOpt = variableRepository.getVariable(userId, name);
        if (variableOpt.isPresent()) {
            return variableRepository.updateVariable(userId, name, value);
        }
        else return variableRepository.insertVariable(userId, name, value);
    }

    public UserVariable get(Long userId, String name) {
        return variableRepository.getVariable(userId, name).orElse(new UserVariable(userId, name, null));
    }

    public int remove(Long userId, String name) {
        return variableRepository.deleteVariable(userId, name);
    }
}
