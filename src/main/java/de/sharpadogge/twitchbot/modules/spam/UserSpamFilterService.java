package de.sharpadogge.twitchbot.modules.spam;

import de.sharpadogge.twitchbot.modules.spam.entity.UserSpamFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserSpamFilterService {

    private final UserSpamFilterRepository userSpamFilterRepository;

    @Autowired
    public UserSpamFilterService(UserSpamFilterRepository userSpamFilterRepository) {
        this.userSpamFilterRepository = userSpamFilterRepository;
    }

    public UserSpamFilter getFilter(final long userId, final String filter) {
        Optional<UserSpamFilter> filterOpt = userSpamFilterRepository.getForUserAndFilter(userId, filter);
        if (filterOpt.isPresent()) {
            return filterOpt.get();
        }
        userSpamFilterRepository.createForUserAndFilter(userId, filter);
        return userSpamFilterRepository.getForUserAndFilter(userId, filter).orElse(null);
    }

    public Map<String, UserSpamFilter> getFiltersForUser(final long userId) {
        Map<String, UserSpamFilter> output = new HashMap<>();
        List<UserSpamFilter> filters = userSpamFilterRepository.getAllFilterSettings(userId);
        for (UserSpamFilter filter : filters) {
            output.put(filter.getFilter(), filter);
        }
        return output;
    }

    public Map<String, List<String>> getExemptions(final long userId) {
        return userSpamFilterRepository.getAllExemptions(userId);
    }

    public int saveFilter(final UserSpamFilter filter) {
        if (filter.getUserId() == null) throw new RuntimeException("No userId provided");
        return saveFilter(filter.getUserId(), filter);
    }

    public int saveFilter(final long userId, final UserSpamFilter filter) {
        filter.setUserId(userId);
        return userSpamFilterRepository.updateFilter(filter);
    }

    public int addBlacklistedWord(final long userId, final String word) {
        return userSpamFilterRepository.addBlacklistedWord(userId, word);
    }

    public int removeBlacklistedWord(final long userId, final String word) {
        return userSpamFilterRepository.removeBlacklistedWord(userId, word);
    }

    public List<String> getBlacklistedWords(final long userId) {
        return userSpamFilterRepository.getBlacklistedWords(userId);
    }

    public int addExemption(final long userId, final String filter, final String query) {
        return userSpamFilterRepository.addExemption(userId, filter, query);
    }

    public int removeExemption(final long userId, final String filter, final String query) {
        return userSpamFilterRepository.removeExemption(userId, filter, query);
    }

    public List<String> getExemptions(final long userId, final String filter) {
        return userSpamFilterRepository.getExemptions(userId, filter);
    }
}
