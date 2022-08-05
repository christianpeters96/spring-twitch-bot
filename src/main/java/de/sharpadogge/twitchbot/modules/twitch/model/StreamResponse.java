package de.sharpadogge.twitchbot.modules.twitch.model;

public class StreamResponse {

    private Stream stream;

    public StreamResponse() {
    }

    public StreamResponse(Stream stream) {
        this.stream = stream;
    }

    public Stream getStream() {
        return stream;
    }

    public void setStream(Stream stream) {
        this.stream = stream;
    }
}
