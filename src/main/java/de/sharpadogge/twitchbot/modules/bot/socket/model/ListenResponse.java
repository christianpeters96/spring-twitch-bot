package de.sharpadogge.twitchbot.modules.bot.socket.model;

public class ListenResponse {

    private String type;

    private String error;

    private String nonce;

    public ListenResponse() {
    }

    public ListenResponse(String type, String error, String nonce) {
        this.type = type;
        this.error = error;
        this.nonce = nonce;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}
