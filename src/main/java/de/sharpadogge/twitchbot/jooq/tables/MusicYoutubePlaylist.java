/*
 * This file is generated by jOOQ.
 */
package de.sharpadogge.twitchbot.jooq.tables;


import de.sharpadogge.twitchbot.jooq.Keys;
import de.sharpadogge.twitchbot.jooq.Twitchbot;
import de.sharpadogge.twitchbot.jooq.tables.records.MusicYoutubePlaylistRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row6;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MusicYoutubePlaylist extends TableImpl<MusicYoutubePlaylistRecord> {

    private static final long serialVersionUID = -1917253048;

    /**
     * The reference instance of <code>twitchbot.music_youtube_playlist</code>
     */
    public static final MusicYoutubePlaylist MUSIC_YOUTUBE_PLAYLIST = new MusicYoutubePlaylist();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MusicYoutubePlaylistRecord> getRecordType() {
        return MusicYoutubePlaylistRecord.class;
    }

    /**
     * The column <code>twitchbot.music_youtube_playlist.id</code>.
     */
    public final TableField<MusicYoutubePlaylistRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>twitchbot.music_youtube_playlist.user_id</code>.
     */
    public final TableField<MusicYoutubePlaylistRecord, Long> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>twitchbot.music_youtube_playlist.video_id</code>.
     */
    public final TableField<MusicYoutubePlaylistRecord, String> VIDEO_ID = createField(DSL.name("video_id"), org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>twitchbot.music_youtube_playlist.video_duration_sec</code>.
     */
    public final TableField<MusicYoutubePlaylistRecord, Integer> VIDEO_DURATION_SEC = createField(DSL.name("video_duration_sec"), org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>twitchbot.music_youtube_playlist.video_title</code>.
     */
    public final TableField<MusicYoutubePlaylistRecord, String> VIDEO_TITLE = createField(DSL.name("video_title"), org.jooq.impl.SQLDataType.VARCHAR(512).defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>twitchbot.music_youtube_playlist.video_author</code>.
     */
    public final TableField<MusicYoutubePlaylistRecord, String> VIDEO_AUTHOR = createField(DSL.name("video_author"), org.jooq.impl.SQLDataType.VARCHAR(256).defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * Create a <code>twitchbot.music_youtube_playlist</code> table reference
     */
    public MusicYoutubePlaylist() {
        this(DSL.name("music_youtube_playlist"), null);
    }

    /**
     * Create an aliased <code>twitchbot.music_youtube_playlist</code> table reference
     */
    public MusicYoutubePlaylist(String alias) {
        this(DSL.name(alias), MUSIC_YOUTUBE_PLAYLIST);
    }

    /**
     * Create an aliased <code>twitchbot.music_youtube_playlist</code> table reference
     */
    public MusicYoutubePlaylist(Name alias) {
        this(alias, MUSIC_YOUTUBE_PLAYLIST);
    }

    private MusicYoutubePlaylist(Name alias, Table<MusicYoutubePlaylistRecord> aliased) {
        this(alias, aliased, null);
    }

    private MusicYoutubePlaylist(Name alias, Table<MusicYoutubePlaylistRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> MusicYoutubePlaylist(Table<O> child, ForeignKey<O, MusicYoutubePlaylistRecord> key) {
        super(child, key, MUSIC_YOUTUBE_PLAYLIST);
    }

    @Override
    public Schema getSchema() {
        return Twitchbot.TWITCHBOT;
    }

    @Override
    public Identity<MusicYoutubePlaylistRecord, Long> getIdentity() {
        return Keys.IDENTITY_MUSIC_YOUTUBE_PLAYLIST;
    }

    @Override
    public UniqueKey<MusicYoutubePlaylistRecord> getPrimaryKey() {
        return Keys.KEY_MUSIC_YOUTUBE_PLAYLIST_PRIMARY;
    }

    @Override
    public List<UniqueKey<MusicYoutubePlaylistRecord>> getKeys() {
        return Arrays.<UniqueKey<MusicYoutubePlaylistRecord>>asList(Keys.KEY_MUSIC_YOUTUBE_PLAYLIST_PRIMARY);
    }

    @Override
    public List<ForeignKey<MusicYoutubePlaylistRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<MusicYoutubePlaylistRecord, ?>>asList(Keys.FK_MUSIC_YOUTUBE_PLAYLIST_USER_ID);
    }

    public Users users() {
        return new Users(this, Keys.FK_MUSIC_YOUTUBE_PLAYLIST_USER_ID);
    }

    @Override
    public MusicYoutubePlaylist as(String alias) {
        return new MusicYoutubePlaylist(DSL.name(alias), this);
    }

    @Override
    public MusicYoutubePlaylist as(Name alias) {
        return new MusicYoutubePlaylist(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MusicYoutubePlaylist rename(String name) {
        return new MusicYoutubePlaylist(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MusicYoutubePlaylist rename(Name name) {
        return new MusicYoutubePlaylist(name, null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<Long, Long, String, Integer, String, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }
}