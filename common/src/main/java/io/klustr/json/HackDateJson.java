package io.klustr.json;

import com.google.gson.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;

/**
 * This is for webhooks serialization, as the datetime object is serialized
 * to garbage, and we use this to cleanup the serialization. Its a total hack
 * to fix this, and should be removed
 */
public class HackDateJson {
    private static final DateTimeFormatter UTC_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .withZone(ZoneOffset.UTC);

    public static JsonObject toJsonObject(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        return processJsonObject(jsonObject);
    }

    private static JsonObject processJsonObject(JsonObject jsonObject) {
        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            if (value.isJsonPrimitive()) {
                JsonPrimitive primitive = value.getAsJsonPrimitive();
                if (primitive.isString()) {
                    String stringValue = primitive.getAsString();
                    if (isValidDateTime(stringValue)) {
                        jsonObject.addProperty(key, convertToUTCZ(stringValue));
                    }
                }
            } else if (value.isJsonObject()) {
                jsonObject.add(key, processJsonObject(value.getAsJsonObject()));  // Recursive call for nested objects
            } else if (value.isJsonArray()) {
                jsonObject.add(key, processJsonArray(value.getAsJsonArray()));  // Recursive call for arrays
            }
        }
        return jsonObject;
    }

    private static JsonArray processJsonArray(JsonArray jsonArray) {
        JsonArray newArray = new JsonArray();
        for (JsonElement element : jsonArray) {
            if (element.isJsonObject()) {
                newArray.add(processJsonObject(element.getAsJsonObject()));  // Recursive call for objects
            } else if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
                String stringValue = element.getAsString();
                if (isValidDateTime(stringValue)) {
                    newArray.add(convertToUTCZ(stringValue));  // Convert datetime inside arrays
                } else {
                    newArray.add(stringValue);
                }
            } else {
                newArray.add(element);
            }
        }
        return newArray;
    }

    private static boolean isValidDateTime(String dateStr) {
        try {
            Instant.parse(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static String convertToUTCZ(String dateStr) {
        try {
            Instant instant = Instant.parse(dateStr);
            return UTC_FORMATTER.format(instant);
        } catch (Exception e) {
            return dateStr; // If parsing fails, return original value
        }
    }
}

