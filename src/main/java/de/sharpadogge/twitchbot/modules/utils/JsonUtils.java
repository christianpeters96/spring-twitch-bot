package de.sharpadogge.twitchbot.modules.utils;

import java.util.Optional;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonUtils {
    
    private static final String CHECK_ARRAY = "([A-Za-z0-9_]+)\\[(\\d+?)\\]";

    public static JsonNode get(JsonNode node, String path) {
        JsonNode output = node;
        String[] parts = path.split("\\.");
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            Boolean failed = false;
            if (Pattern.compile(CHECK_ARRAY).matcher(part).matches()) {
                String tempP = part.replaceAll(CHECK_ARRAY, "$1");
                String tempI = part.replaceAll(CHECK_ARRAY, "$2");
                if (output.has(tempP) && output.get(tempP).isArray() && output.get(tempP).has(Integer.parseInt(tempI))) {
                    output = output.get(tempP).get(tempI);
                }
                else failed = true;
            }
            else {
                if (output.has(part)) {
                    output = output.get(part);
                }
                else failed = true;
            }
            if (failed) {
                output = null;
                break;
            }
        }
        return output;
    }

    public static Optional<Long> getLong(JsonNode node, String path) {
        JsonNode json = get(node, path);
        return json == null ? Optional.empty() : Optional.ofNullable(json.longValue());
    }

    public static Optional<String> getString(JsonNode node, String path) {
        JsonNode json = get(node, path);
        return json == null ? Optional.empty() : Optional.ofNullable(json.textValue());
    }

    public static Optional<Boolean> getBoolean(JsonNode node, String path) {
        JsonNode json = get(node, path);
        return json == null ? Optional.empty() : Optional.ofNullable(json.booleanValue());
    }

    public static Optional<Integer> getInt(JsonNode node, String path) {
        JsonNode json = get(node, path);
        return json == null ? Optional.empty() : Optional.ofNullable(json.intValue());
    }
}
