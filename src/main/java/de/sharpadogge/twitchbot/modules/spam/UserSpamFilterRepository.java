package de.sharpadogge.twitchbot.modules.spam;

import de.sharpadogge.twitchbot.jooq.Tables;
import de.sharpadogge.twitchbot.modules.spam.entity.UserSpamException;
import de.sharpadogge.twitchbot.modules.spam.entity.UserSpamFilter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserSpamFilterRepository {

    private final DSLContext create;

    @Autowired
    public UserSpamFilterRepository(DSLContext create) {
        this.create = create;
    }

    public int createForUserAndFilter(final Long userId, final String filter) {
        return create
                .insertInto(Tables.USER_SPAM_FILTERS, Tables.USER_SPAM_FILTERS.USER_ID, Tables.USER_SPAM_FILTERS.FILTER)
                .values(userId, filter)
                .execute();
    }

    public int updateFilter(final UserSpamFilter filter) {
        return create.update(Tables.USER_SPAM_FILTERS)
                .set(create.newRecord(Tables.USER_SPAM_FILTERS, filter))
                .where(Tables.USER_SPAM_FILTERS.USER_ID.eq(filter.getUserId()))
                .and(Tables.USER_SPAM_FILTERS.FILTER.eq(filter.getFilter()))
                .execute();
    }

    public Optional<UserSpamFilter> getForUserAndFilter(final Long userId, final String filter) {
        return create
                .selectFrom(Tables.USER_SPAM_FILTERS)
                .where(Tables.USER_SPAM_FILTERS.USER_ID.eq(userId))
                .and(Tables.USER_SPAM_FILTERS.FILTER.eq(filter))
                .fetchOptionalInto(UserSpamFilter.class);
    }

    public List<UserSpamFilter> getAllFilterSettings(final Long userId) {
        return create
                .selectFrom(Tables.USER_SPAM_FILTERS)
                .where(Tables.USER_SPAM_FILTERS.USER_ID.eq(userId))
                .fetchInto(UserSpamFilter.class);
    }

    public int addBlacklistedWord(final long userId, final String word) {
        if (!blacklistedWordExists(userId, word)) {
            return create
                    .insertInto(Tables.USER_STRING_BLACKLIST, Tables.USER_STRING_BLACKLIST.USER_ID, Tables.USER_STRING_BLACKLIST.STRING)
                    .values(userId, word.toLowerCase())
                    .execute();
        }
        return 0;
    }

    public int removeBlacklistedWord(final long userId, final String word) {
        if (blacklistedWordExists(userId, word)) {
            return create
                    .deleteFrom(Tables.USER_STRING_BLACKLIST)
                    .where(Tables.USER_STRING_BLACKLIST.USER_ID.eq(userId))
                    .and(Tables.USER_STRING_BLACKLIST.STRING.eq(word))
                    .execute();
        }
        return 0;
    }

    public boolean blacklistedWordExists(final long userId, final String word) {
        return create
                .selectCount()
                .from(Tables.USER_STRING_BLACKLIST)
                .where(Tables.USER_STRING_BLACKLIST.USER_ID.eq(userId))
                .and(Tables.USER_STRING_BLACKLIST.STRING.eq(word))
                .fetchOneInto(Integer.class) != 0;
    }

    public List<String> getBlacklistedWords(final long userId) {
        return create
                .select(Tables.USER_STRING_BLACKLIST.STRING)
                .from(Tables.USER_STRING_BLACKLIST)
                .where(Tables.USER_STRING_BLACKLIST.USER_ID.eq(userId))
                .fetchInto(String.class);
    }

    public int addExemption(final long userId, final String filter, final String query) {
        if (!exemptionExists(userId, filter, query)) {
            return create
                    .insertInto(Tables.USER_SPAM_EXCEPTIONS, Tables.USER_SPAM_EXCEPTIONS.USER_ID, Tables.USER_SPAM_EXCEPTIONS.FILTER, Tables.USER_SPAM_EXCEPTIONS.EXCEPTION)
                    .values(userId, filter.toLowerCase(), query.toLowerCase())
                    .execute();
        }
        return 0;
    }

    public int removeExemption(final long userId, final String filter, final String query) {
        if (exemptionExists(userId, filter, query)) {
            return create
                    .deleteFrom(Tables.USER_SPAM_EXCEPTIONS)
                    .where(Tables.USER_SPAM_EXCEPTIONS.USER_ID.eq(userId))
                    .and(Tables.USER_SPAM_EXCEPTIONS.FILTER.eq(filter))
                    .and(Tables.USER_SPAM_EXCEPTIONS.EXCEPTION.eq(query))
                    .execute();
        }
        return 0;
    }

    public boolean exemptionExists(final long userId, final String filter, final String query) {
        return create
                .selectCount()
                .from(Tables.USER_SPAM_EXCEPTIONS)
                .where(Tables.USER_SPAM_EXCEPTIONS.USER_ID.eq(userId))
                .and(Tables.USER_SPAM_EXCEPTIONS.FILTER.eq(filter.toLowerCase()))
                .and(Tables.USER_SPAM_EXCEPTIONS.EXCEPTION.eq(query.toLowerCase()))
                .fetchOneInto(Integer.class) != 0;
    }

    public List<String> getExemptions(final long userId, final String filter) {
        return create
                .select(Tables.USER_SPAM_EXCEPTIONS.EXCEPTION)
                .from(Tables.USER_SPAM_EXCEPTIONS)
                .where(Tables.USER_SPAM_EXCEPTIONS.USER_ID.eq(userId))
                .and(Tables.USER_SPAM_EXCEPTIONS.FILTER.eq(filter))
                .fetchInto(String.class);
    }

    public Map<String, List<String>> getAllExemptions(final Long userId) {
        Map<String, List<String>> output = new HashMap<>();
        List<UserSpamException> all = create
                .selectFrom(Tables.USER_SPAM_EXCEPTIONS)
                .where(Tables.USER_SPAM_EXCEPTIONS.USER_ID.eq(userId))
                .fetchInto(UserSpamException.class);

        for (UserSpamException exception : all) {
            if(!output.containsKey(exception.getFilter())) {
                output.put(exception.getFilter(), new ArrayList<>());
            }
            output.get(exception.getFilter()).add(exception.getException());
        }
        return output;
    }
}
