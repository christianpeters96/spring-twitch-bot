package de.sharpadogge.twitchbot.modules.user;

import java.util.Optional;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.sharpadogge.twitchbot.jooq.Tables;

@Repository
public class UserRepository {

    private final DSLContext create;

    @Autowired
    public UserRepository(final DSLContext create) {

        this.create = create;
    }

    public void insertOrUpdate(final User user) {
        if (create.selectCount().from(Tables.USERS).where(Tables.USERS.TTV_ID.eq(user.getTwitchUserId())).fetchOneInto(Integer.class) != 0) {
            if (user.getId() == null) {
                User foundUser = create.selectFrom(Tables.USERS).where(Tables.USERS.TTV_ID.eq(user.getTwitchUserId())).fetchOneInto(User.class);
                foundUser.setToken(user.getToken());
                create.update(Tables.USERS)
                        .set(create.newRecord(Tables.USERS, foundUser))
                        .where(Tables.USERS.TTV_ID.eq(user.getTwitchUserId()))
                        .execute();
            }
            else {
                create.update(Tables.USERS)
                        .set(create.newRecord(Tables.USERS, user))
                        .where(Tables.USERS.TTV_ID.eq(user.getTwitchUserId()))
                        .execute();
            }
        }
        else {
            create.newRecord(Tables.USERS, user).insert();
        }
    }

    public Optional<User> getUserByToken(final String token) {
        User foundUser = create
                .selectFrom(Tables.USERS)
                .where(Tables.USERS.TOKEN.eq(token))
            .limit(1)
            .fetchOptionalInto(User.class).orElse(null);

        return Optional.ofNullable(foundUser);
    }

    public Optional<User> getUserByName(final String name) {
        User foundUser = create.select(Tables.USERS.fields())
                .from(Tables.USERS)
                .where(Tables.USERS.NAME.eq(name))
                .limit(1)
                .fetchOptionalInto(User.class).orElse(null);

        return Optional.ofNullable(foundUser);
    }
}