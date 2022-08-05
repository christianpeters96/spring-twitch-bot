package de.sharpadogge.twitchbot.modules.youtube.model.subtitles;

public interface OnSubtitlesDownloadListener {

    void onFinished(String subtitles);

    void onError(Throwable throwable);
}