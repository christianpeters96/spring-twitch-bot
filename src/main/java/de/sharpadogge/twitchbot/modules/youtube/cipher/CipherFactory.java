package de.sharpadogge.twitchbot.modules.youtube.cipher;

import de.sharpadogge.twitchbot.modules.youtube.YoutubeException;

public interface CipherFactory {
    
    Cipher createCipher(String jsUrl) throws YoutubeException;

    void addInitialFunctionPattern(int priority, String regex);

    void addFunctionEquivalent(String regex, CipherFunction function);
}
