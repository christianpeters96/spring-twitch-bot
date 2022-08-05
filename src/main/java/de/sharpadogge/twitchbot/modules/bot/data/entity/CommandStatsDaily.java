package de.sharpadogge.twitchbot.modules.bot.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "twitch_command_stats_daily")
public class CommandStatsDaily {

    @Id
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "channel")
    private String channel;

    @Column(name = "cmd")
    private String cmd;

    @Column(name = "call_count")
    private Long callCount = 0L;

    public CommandStatsOverall toOverall() {
        return new CommandStatsOverall(0L, channel, cmd, callCount);
    }

    public CommandStatsDaily() {
    }

    public CommandStatsDaily(LocalDate date, String channel, String cmd, Long callCount) {
        this.date = date;
        this.channel = channel;
        this.cmd = cmd;
        this.callCount = callCount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Long getCallCount() {
        return callCount;
    }

    public void setCallCount(Long callCount) {
        this.callCount = callCount;
    }
}

