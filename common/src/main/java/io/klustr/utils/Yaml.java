package io.klustr.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.List;

public class Yaml {

    public static <T> T fromYaml(final String yaml, final Class<T> type) {
        try {
            ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
            return yamlReader.readValue(yaml, type);
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to json", ex);
        }
    }

    public static <T> List<T> fromYamlGenericList(final String yaml, final Class<T> type) {
        JavaType javaType = Json.mapperInstance().getTypeFactory().constructParametricType(List.class, type);
        try {
            ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
            Object o = yamlReader.readValue(yaml, javaType);
            return (List<T>)o;
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to json", ex);
        }
    }
}
