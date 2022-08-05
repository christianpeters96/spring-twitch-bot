package de.sharpadogge.twitchbot.modules.youtube.cipher;

public interface Cipher {
    
    String getSignature(String cipheredSignature);
}
