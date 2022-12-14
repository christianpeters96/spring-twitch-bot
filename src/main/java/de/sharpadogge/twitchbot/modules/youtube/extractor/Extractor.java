package de.sharpadogge.twitchbot.modules.youtube.extractor;

import de.sharpadogge.twitchbot.modules.youtube.YoutubeException;

public interface Extractor {

    void setRequestProperty(String key, String value);

    void setRetryOnFailure(int retryOnFailure);

    String extractYtPlayerConfig(String html) throws YoutubeException;

    String extractYtInitialData(String html) throws YoutubeException;

    String loadUrl(String url) throws YoutubeException;

    String loadUrl(String url, String data, String method) throws YoutubeException;
}
