package de.sharpadogge.twitchbot.modules.youtube.cipher;

public class ReverseFunction implements CipherFunction {
    
    @Override
    public char[] apply(char[] array, String argument) {
        StringBuilder sb = new StringBuilder().append(array);
        return sb.reverse().toString().toCharArray();
    }
}
