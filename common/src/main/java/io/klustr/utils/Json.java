package io.klustr.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.*;
import io.klustr.json.DynamicSubtypeRegistrar;
import io.klustr.json.HackDateJson;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Standard functions for handling JSON in Java that will enable rapid
 * development.
 */
public class Json {

    private static ObjectMapper mapper = JsonFactory.build();

    public static ObjectMapper mapperInstance() {
        return JsonFactory.build();
    }

    /**
     * Returns the json string as a {@link JsonNode} which can be processed
     * by various tools and sent to various buffers.
     *
     * @param json The json string you want to convert.
     * @return A {@link JsonNode} or a {@link RuntimeException}
     */
    public static JsonNode toJsonNode(String json) {
        try {
            return mapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonObject toJsonObject(String json) {

        JsonObject jsonObjectAlt = HackDateJson.toJsonObject(json);//JsonParser.parseString(json).getAsJsonObject();
        return jsonObjectAlt;
    }


    /**
     * Convert the specified object to JSON, keeping any polymorphic properties
     * and configuration settings.
     *
     * @param obj The object to convert
     * @return The JSON string for the specified object.
     */
    public static String toJson(final Object obj) {
        if (obj == null) {
            return null;
        }
        // default serializer
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convert the specified object to JSON, keeping any polymorphic properties
     * and configuration settings.
     *
     * @param obj The object to convert
     * @return The JSON string for the specified object.
     */
    public static String toJsonPrettyFormat(final Object obj) {
        if (obj == null)
            throw new IllegalArgumentException("obj is null");
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException( e);
        }
    }

    /**
     * Convert the specified JSON to an object, keeping any polymorphic
     * properties and configuration settings so that polymorphic classes can be
     * used.
     *
     * @param json The json string
     * @param type The type to which to deserialize
     * @return The JSON string for the specified object.
     */
    public static <T> T parse(final String json, final Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to json", ex);
        }
    }

    /**
     * Convert the specified JSON to an object, keeping any polymorphic
     * properties and configuration settings so that polymorphic classes can be
     * used.
     *
     * @param json The json string
     * @param type The type to which to deserialize
     * @return The JSON string for the specified object.
     */
    public static <T> T parse(final JsonNode json, final Class<T> type) {
        try {
            return mapper.readValue(Json.toJson(json), type);
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to json", ex);
        }
    }

    /**
     * Convert the specified JSON to an object, keeping any polymorphic
     * properties and configuration settings so that polymorphic classes can be
     * used.
     *
     * @param json The json string
     * @param type The type to which to deserialize
     * @return The JSON string for the specified object.
     */
    public static <T> T parse(final String json, final JavaType type) {
        try {
            return mapper.readValue(json, type);
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to json", ex);
        }
    }

    /**
     * Convert the specified JSON to an object, keeping any polymorphic
     * properties and configuration settings so that polymorphic classes can be
     * used.
     *
     * @param json The json string
     * @param type The type to which to deserialize
     * @return The JSON string for the specified object.
     */
    public static <T> T parse(final String json, final TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to json", ex);
        }
    }

    /**
     * Will convert a generic json object to a parameterized object.
     *
     * @param json The json string of the parametric type.
     * @param baseClass The base class such as ArrayList<T>
     * @param type The type associated to the base class. For example ArrayList<T> would be T
     * @return Returns the generic result.
     * @param <T> The type of result.
     */
    public static <T> T parseGenericType(final String json, Class<?> baseClass, Class<?>... type) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(baseClass, type);
        try {
            return mapper.readValue(json, javaType);
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to json", ex);
        }
    }

    /**
     * Parses the json as a generic array. Useful if the response is simply [{}, {}] tyhpe.
     * @param json The json returned by the server
     * @param type The item type
     * @return The list of the items returned
     * @param <T> The type of item
     */
    public static <T> List<T> parseArray(final String json, Class<?>... type) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, type);
        try {
            return mapper.readValue(json, javaType);
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to json", ex);
        }
    }


    private static class LocalDateTypeAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public JsonElement serialize(final LocalDate date, final Type typeOfSrc,
                                     final JsonSerializationContext context) {
            return new JsonPrimitive(date.format(formatter));
        }

        @Override
        public LocalDate deserialize(final JsonElement json, final Type typeOfT,
                                     final JsonDeserializationContext context) throws JsonParseException {
            return LocalDate.parse(json.getAsString(), formatter);
        }
    }

    private static class JsonFactory {
        public static ObjectMapper build() {
            ObjectMapper o = new ObjectMapper();
            // ISO3 format for JSON documents
            o = o.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
            // serialization defaults
            o = o.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            // ignore unknown
            o = o.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // polymorphic serializer
            o = o.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            // joda date time serializer
            o = o.registerModule(new JodaModule());
            // Optional.of()
            o = o.registerModule(new Jdk8Module());
            o = o.registerModule(new JavaTimeModule());

            // ✅ no strings, no packages, no config
            DynamicSubtypeRegistrar.register(o);

            return o;
        }
    }
}
