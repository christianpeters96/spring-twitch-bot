package de.sharpadogge.twitchbot.modules.bot.socket.event;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.sharpadogge.twitchbot.jooq.Tables;

@Repository
public class ChannelEventRepository {
    
    private final DSLContext create;

    @Autowired
    public ChannelEventRepository(DSLContext create) {
        this.create = create;
    }

    public List<ChannelEvent> getChannelEventsForUser(final Long userId) {
        return create
            .selectFrom(Tables.USER_CHANNEL_EVENTS)
            .where(Tables.USER_CHANNEL_EVENTS.USER_ID.eq(userId))
            .orderBy(Tables.USER_CHANNEL_EVENTS.CREATED_AT.desc())
            .limit(50)
            .fetchInto(ChannelEvent.class);
    }

    public void insertChannelEvent(final ChannelEvent event) {
        create.newRecord(Tables.USER_CHANNEL_EVENTS, event).insert();
    }
}
