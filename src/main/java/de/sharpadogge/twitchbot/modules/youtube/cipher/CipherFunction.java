package de.sharpadogge.twitchbot.modules.youtube.cipher;

public interface CipherFunction {
    
    char[] apply(char[] array, String argument);
}
