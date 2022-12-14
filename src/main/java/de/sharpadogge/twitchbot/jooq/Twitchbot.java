/*
 * This file is generated by jOOQ.
 */
package de.sharpadogge.twitchbot.jooq;


import de.sharpadogge.twitchbot.jooq.tables.IntegrationAuth;
import de.sharpadogge.twitchbot.jooq.tables.MusicSettings;
import de.sharpadogge.twitchbot.jooq.tables.MusicYoutubePlaylist;
import de.sharpadogge.twitchbot.jooq.tables.MusicYoutubeRequests;
import de.sharpadogge.twitchbot.jooq.tables.MusicYoutubeSettings;
import de.sharpadogge.twitchbot.jooq.tables.TwitchCommandStatsDaily;
import de.sharpadogge.twitchbot.jooq.tables.TwitchCommandStatsOverall;
import de.sharpadogge.twitchbot.jooq.tables.TwitchUserStatsDaily;
import de.sharpadogge.twitchbot.jooq.tables.TwitchUserStatsOverall;
import de.sharpadogge.twitchbot.jooq.tables.UserChannelEvents;
import de.sharpadogge.twitchbot.jooq.tables.UserCommand;
import de.sharpadogge.twitchbot.jooq.tables.UserCommandAction;
import de.sharpadogge.twitchbot.jooq.tables.UserCommandAlias;
import de.sharpadogge.twitchbot.jooq.tables.UserCommandPermission;
import de.sharpadogge.twitchbot.jooq.tables.UserIntegration;
import de.sharpadogge.twitchbot.jooq.tables.UserLinkWhitelist;
import de.sharpadogge.twitchbot.jooq.tables.UserSpamExceptions;
import de.sharpadogge.twitchbot.jooq.tables.UserSpamFilters;
import de.sharpadogge.twitchbot.jooq.tables.UserStringBlacklist;
import de.sharpadogge.twitchbot.jooq.tables.UserUrlWhitelist;
import de.sharpadogge.twitchbot.jooq.tables.UserVars;
import de.sharpadogge.twitchbot.jooq.tables.Users;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Twitchbot extends SchemaImpl {

    private static final long serialVersionUID = 1675952407;

    /**
     * The reference instance of <code>twitchbot</code>
     */
    public static final Twitchbot TWITCHBOT = new Twitchbot();

    /**
     * The table <code>twitchbot.integration_auth</code>.
     */
    public final IntegrationAuth INTEGRATION_AUTH = IntegrationAuth.INTEGRATION_AUTH;

    /**
     * The table <code>twitchbot.music_settings</code>.
     */
    public final MusicSettings MUSIC_SETTINGS = MusicSettings.MUSIC_SETTINGS;

    /**
     * The table <code>twitchbot.music_youtube_playlist</code>.
     */
    public final MusicYoutubePlaylist MUSIC_YOUTUBE_PLAYLIST = MusicYoutubePlaylist.MUSIC_YOUTUBE_PLAYLIST;

    /**
     * The table <code>twitchbot.music_youtube_requests</code>.
     */
    public final MusicYoutubeRequests MUSIC_YOUTUBE_REQUESTS = MusicYoutubeRequests.MUSIC_YOUTUBE_REQUESTS;

    /**
     * The table <code>twitchbot.music_youtube_settings</code>.
     */
    public final MusicYoutubeSettings MUSIC_YOUTUBE_SETTINGS = MusicYoutubeSettings.MUSIC_YOUTUBE_SETTINGS;

    /**
     * The table <code>twitchbot.twitch_command_stats_daily</code>.
     */
    public final TwitchCommandStatsDaily TWITCH_COMMAND_STATS_DAILY = TwitchCommandStatsDaily.TWITCH_COMMAND_STATS_DAILY;

    /**
     * The table <code>twitchbot.twitch_command_stats_overall</code>.
     */
    public final TwitchCommandStatsOverall TWITCH_COMMAND_STATS_OVERALL = TwitchCommandStatsOverall.TWITCH_COMMAND_STATS_OVERALL;

    /**
     * The table <code>twitchbot.twitch_user_stats_daily</code>.
     */
    public final TwitchUserStatsDaily TWITCH_USER_STATS_DAILY = TwitchUserStatsDaily.TWITCH_USER_STATS_DAILY;

    /**
     * The table <code>twitchbot.twitch_user_stats_overall</code>.
     */
    public final TwitchUserStatsOverall TWITCH_USER_STATS_OVERALL = TwitchUserStatsOverall.TWITCH_USER_STATS_OVERALL;

    /**
     * The table <code>twitchbot.user_channel_events</code>.
     */
    public final UserChannelEvents USER_CHANNEL_EVENTS = UserChannelEvents.USER_CHANNEL_EVENTS;

    /**
     * The table <code>twitchbot.user_command</code>.
     */
    public final UserCommand USER_COMMAND = UserCommand.USER_COMMAND;

    /**
     * The table <code>twitchbot.user_command_action</code>.
     */
    public final UserCommandAction USER_COMMAND_ACTION = UserCommandAction.USER_COMMAND_ACTION;

    /**
     * The table <code>twitchbot.user_command_alias</code>.
     */
    public final UserCommandAlias USER_COMMAND_ALIAS = UserCommandAlias.USER_COMMAND_ALIAS;

    /**
     * The table <code>twitchbot.user_command_permission</code>.
     */
    public final UserCommandPermission USER_COMMAND_PERMISSION = UserCommandPermission.USER_COMMAND_PERMISSION;

    /**
     * The table <code>twitchbot.user_integration</code>.
     */
    public final UserIntegration USER_INTEGRATION = UserIntegration.USER_INTEGRATION;

    /**
     * The table <code>twitchbot.user_link_whitelist</code>.
     */
    public final UserLinkWhitelist USER_LINK_WHITELIST = UserLinkWhitelist.USER_LINK_WHITELIST;

    /**
     * The table <code>twitchbot.user_spam_exceptions</code>.
     */
    public final UserSpamExceptions USER_SPAM_EXCEPTIONS = UserSpamExceptions.USER_SPAM_EXCEPTIONS;

    /**
     * The table <code>twitchbot.user_spam_filters</code>.
     */
    public final UserSpamFilters USER_SPAM_FILTERS = UserSpamFilters.USER_SPAM_FILTERS;

    /**
     * The table <code>twitchbot.user_string_blacklist</code>.
     */
    public final UserStringBlacklist USER_STRING_BLACKLIST = UserStringBlacklist.USER_STRING_BLACKLIST;

    /**
     * The table <code>twitchbot.user_url_whitelist</code>.
     */
    public final UserUrlWhitelist USER_URL_WHITELIST = UserUrlWhitelist.USER_URL_WHITELIST;

    /**
     * The table <code>twitchbot.user_vars</code>.
     */
    public final UserVars USER_VARS = UserVars.USER_VARS;

    /**
     * The table <code>twitchbot.users</code>.
     */
    public final Users USERS = Users.USERS;

    /**
     * No further instances allowed
     */
    private Twitchbot() {
        super("twitchbot", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            IntegrationAuth.INTEGRATION_AUTH,
            MusicSettings.MUSIC_SETTINGS,
            MusicYoutubePlaylist.MUSIC_YOUTUBE_PLAYLIST,
            MusicYoutubeRequests.MUSIC_YOUTUBE_REQUESTS,
            MusicYoutubeSettings.MUSIC_YOUTUBE_SETTINGS,
            TwitchCommandStatsDaily.TWITCH_COMMAND_STATS_DAILY,
            TwitchCommandStatsOverall.TWITCH_COMMAND_STATS_OVERALL,
            TwitchUserStatsDaily.TWITCH_USER_STATS_DAILY,
            TwitchUserStatsOverall.TWITCH_USER_STATS_OVERALL,
            UserChannelEvents.USER_CHANNEL_EVENTS,
            UserCommand.USER_COMMAND,
            UserCommandAction.USER_COMMAND_ACTION,
            UserCommandAlias.USER_COMMAND_ALIAS,
            UserCommandPermission.USER_COMMAND_PERMISSION,
            UserIntegration.USER_INTEGRATION,
            UserLinkWhitelist.USER_LINK_WHITELIST,
            UserSpamExceptions.USER_SPAM_EXCEPTIONS,
            UserSpamFilters.USER_SPAM_FILTERS,
            UserStringBlacklist.USER_STRING_BLACKLIST,
            UserUrlWhitelist.USER_URL_WHITELIST,
            UserVars.USER_VARS,
            Users.USERS);
    }
}
