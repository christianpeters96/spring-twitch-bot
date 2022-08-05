package de.sharpadogge.twitchbot.modules.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class StringUtils {
    public static String responseString(final String string) {
        return "{\"content\":\"" + string + "\"}";
    }
    public static String getTokenFromHeader(final String authorizationString) {
        return authorizationString.split(" ").length == 2 ? authorizationString.split(" ")[1] : null;
    }
    public static String createUniqueHash(final String extraSeasoning) {
        try {
            final String salt = "YCA8CFgQI47He8kIt344";
            final String pepper = "Cdi7SMQ2qsSXMVmKazgi";
            final String clean = salt + System.nanoTime() + pepper + extraSeasoning;
            return DigestUtils.sha256Hex(clean);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String createUniqueHash() {
        return StringUtils.createUniqueHash("");
    }
}