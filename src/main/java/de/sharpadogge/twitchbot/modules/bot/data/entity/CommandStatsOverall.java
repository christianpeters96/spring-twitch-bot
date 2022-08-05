package de.sharpadogge.twitchbot.modules.bot.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "twitch_command_stats_overall")
public class CommandStatsOverall {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "channel")
    private String channel;

    @Column(name = "cmd")
    private String cmd;

    @Column(name = "call_count")
    private Long callCount = 0L;

    public CommandStatsOverall() {
    }

    public CommandStatsOverall(Long id, String channel, String cmd, Long callCount) {
        this.id = id;
        this.channel = channel;
        this.cmd = cmd;
        this.callCount = callCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

