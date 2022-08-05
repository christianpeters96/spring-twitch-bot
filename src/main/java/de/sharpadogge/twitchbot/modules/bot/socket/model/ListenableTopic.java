package de.sharpadogge.twitchbot.modules.bot.socket.model;

public class ListenableTopic {

    private String topic;

    private String nonce;

    private Boolean pending = false;

    private Boolean listening = false;

    public ListenableTopic() {
    }

    public ListenableTopic(String topic, String nonce) {
        this.topic = topic;
        this.nonce = nonce;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public Boolean getPending() {
        return pending;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }

    public Boolean getListening() {
        return listening;
    }

    public void setListening(Boolean listening) {
        this.listening = listening;
    }
}
