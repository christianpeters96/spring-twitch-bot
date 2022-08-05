/*
 * This file is generated by jOOQ.
 */
package de.sharpadogge.twitchbot.jooq.tables.records;


import de.sharpadogge.twitchbot.jooq.tables.MusicYoutubeRequests;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MusicYoutubeRequestsRecord extends UpdatableRecordImpl<MusicYoutubeRequestsRecord> implements Record9<Long, Long, String, Integer, String, String, LocalDateTime, String, Byte> {

    private static final long serialVersionUID = 490981414;

    /**
     * Setter for <code>twitchbot.music_youtube_requests.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>twitchbot.music_youtube_requests.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>twitchbot.music_youtube_requests.user_id</code>.
     */
    public void setUserId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>twitchbot.music_youtube_requests.user_id</code>.
     */
    public Long getUserId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>twitchbot.music_youtube_requests.video_id</code>.
     */
    public void setVideoId(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>twitchbot.music_youtube_requests.video_id</code>.
     */
    public String getVideoId() {
        return (String) get(2);
    }

    /**
     * Setter for <code>twitchbot.music_youtube_requests.video_duration_sec</code>.
     */
    public void setVideoDurationSec(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>twitchbot.music_youtube_requests.video_duration_sec</code>.
     */
    public Integer getVideoDurationSec() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>twitchbot.music_youtube_requests.video_title</code>.
     */
    public void setVideoTitle(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>twitchbot.music_youtube_requests.video_title</code>.
     */
    public String getVideoTitle() {
        return (String) get(4);
    }

    /**
     * Setter for <code>twitchbot.music_youtube_requests.video_author</code>.
     */
    public void setVideoAuthor(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>twitchbot.music_youtube_requests.video_author</code>.
     */
    public String getVideoAuthor() {
        return (String) get(5);
    }

    /**
     * Setter for <code>twitchbot.music_youtube_requests.requested_ts</code>.
     */
    public void setRequestedTs(LocalDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>twitchbot.music_youtube_requests.requested_ts</code>.
     */
    public LocalDateTime getRequestedTs() {
        return (LocalDateTime) get(6);
    }

    /**
     * Setter for <code>twitchbot.music_youtube_requests.requested_by</code>.
     */
    public void setRequestedBy(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>twitchbot.music_youtube_requests.requested_by</code>.
     */
    public String getRequestedBy() {
        return (String) get(7);
    }

    /**
     * Setter for <code>twitchbot.music_youtube_requests.finished</code>.
     */
    public void setFinished(Byte value) {
        set(8, value);
    }

    /**
     * Getter for <code>twitchbot.music_youtube_requests.finished</code>.
     */
    public Byte getFinished() {
        return (Byte) get(8);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row9<Long, Long, String, Integer, String, String, LocalDateTime, String, Byte> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    @Override
    public Row9<Long, Long, String, Integer, String, String, LocalDateTime, String, Byte> valuesRow() {
        return (Row9) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return MusicYoutubeRequests.MUSIC_YOUTUBE_REQUESTS.ID;
    }

    @Override
    public Field<Long> field2() {
        return MusicYoutubeRequests.MUSIC_YOUTUBE_REQUESTS.USER_ID;
    }

    @Override
    public Field<String> field3() {
        return MusicYoutubeRequests.MUSIC_YOUTUBE_REQUESTS.VIDEO_ID;
    }

    @Override
    public Field<Integer> field4() {
        return MusicYoutubeRequests.MUSIC_YOUTUBE_REQUESTS.VIDEO_DURATION_SEC;
    }

    @Override
    public Field<String> field5() {
        return MusicYoutubeRequests.MUSIC_YOUTUBE_REQUESTS.VIDEO_TITLE;
    }

    @Override
    public Field<String> field6() {
        return MusicYoutubeRequests.MUSIC_YOUTUBE_REQUESTS.VIDEO_AUTHOR;
    }

    @Override
    public Field<LocalDateTime> field7() {
        return MusicYoutubeRequests.MUSIC_YOUTUBE_REQUESTS.REQUESTED_TS;
    }

    @Override
    public Field<String> field8() {
        return MusicYoutubeRequests.MUSIC_YOUTUBE_REQUESTS.REQUESTED_BY;
    }

    @Override
    public Field<Byte> field9() {
        return MusicYoutubeRequests.MUSIC_YOUTUBE_REQUESTS.FINISHED;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Long component2() {
        return getUserId();
    }

    @Override
    public String component3() {
        return getVideoId();
    }

    @Override
    public Integer component4() {
        return getVideoDurationSec();
    }

    @Override
    public String component5() {
        return getVideoTitle();
    }

    @Override
    public String component6() {
        return getVideoAuthor();
    }

    @Override
    public LocalDateTime component7() {
        return getRequestedTs();
    }

    @Override
    public String component8() {
        return getRequestedBy();
    }

    @Override
    public Byte component9() {
        return getFinished();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Long value2() {
        return getUserId();
    }

    @Override
    public String value3() {
        return getVideoId();
    }

    @Override
    public Integer value4() {
        return getVideoDurationSec();
    }

    @Override
    public String value5() {
        return getVideoTitle();
    }

    @Override
    public String value6() {
        return getVideoAuthor();
    }

    @Override
    public LocalDateTime value7() {
        return getRequestedTs();
    }

    @Override
    public String value8() {
        return getRequestedBy();
    }

    @Override
    public Byte value9() {
        return getFinished();
    }

    @Override
    public MusicYoutubeRequestsRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public MusicYoutubeRequestsRecord value2(Long value) {
        setUserId(value);
        return this;
    }

    @Override
    public MusicYoutubeRequestsRecord value3(String value) {
        setVideoId(value);
        return this;
    }

    @Override
    public MusicYoutubeRequestsRecord value4(Integer value) {
        setVideoDurationSec(value);
        return this;
    }

    @Override
    public MusicYoutubeRequestsRecord value5(String value) {
        setVideoTitle(value);
        return this;
    }

    @Override
    public MusicYoutubeRequestsRecord value6(String value) {
        setVideoAuthor(value);
        return this;
    }

    @Override
    public MusicYoutubeRequestsRecord value7(LocalDateTime value) {
        setRequestedTs(value);
        return this;
    }

    @Override
    public MusicYoutubeRequestsRecord value8(String value) {
        setRequestedBy(value);
        return this;
    }

    @Override
    public MusicYoutubeRequestsRecord value9(Byte value) {
        setFinished(value);
        return this;
    }

    @Override
    public MusicYoutubeRequestsRecord values(Long value1, Long value2, String value3, Integer value4, String value5, String value6, LocalDateTime value7, String value8, Byte value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached MusicYoutubeRequestsRecord
     */
    public MusicYoutubeRequestsRecord() {
        super(MusicYoutubeRequests.MUSIC_YOUTUBE_REQUESTS);
    }

    /**
     * Create a detached, initialised MusicYoutubeRequestsRecord
     */
    public MusicYoutubeRequestsRecord(Long id, Long userId, String videoId, Integer videoDurationSec, String videoTitle, String videoAuthor, LocalDateTime requestedTs, String requestedBy, Byte finished) {
        super(MusicYoutubeRequests.MUSIC_YOUTUBE_REQUESTS);

        set(0, id);
        set(1, userId);
        set(2, videoId);
        set(3, videoDurationSec);
        set(4, videoTitle);
        set(5, videoAuthor);
        set(6, requestedTs);
        set(7, requestedBy);
        set(8, finished);
    }
}
