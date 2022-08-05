package de.sharpadogge.twitchbot.modules.bot.socket.model;

public class PingResponse {

    private String type;

    public PingResponse() {
    }

    public PingResponse(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
