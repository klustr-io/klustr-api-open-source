package io.klustr.json;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import org.jsonschema2pojo.AbstractAnnotator;
import org.jsonschema2pojo.GenerationConfig;

public class JsonSchemaFeatureAnnotator extends AbstractAnnotator {

    public JsonSchemaFeatureAnnotator(GenerationConfig generationConfig) {
        super(generationConfig);
    }

    @Override
    public void typeInfo(JDefinedClass clazz, JsonNode schemaNode) {
        enrichBase(clazz, schemaNode);
        enrichSubclass(clazz, schemaNode);
    }

    private void enrichSubclass(JDefinedClass clazz, JsonNode schemaNode) {
        // Apply @JsonTypeName ONLY to schemas that declare x-type-name
        if (schemaNode.has("x-type-name")) {
            clazz.annotate(JsonTypeName.class)
                    .param("value", schemaNode.get("x-type-name").asText());
        }
    }

    private void enrichBase(JDefinedClass clazz, JsonNode schemaNode) {
        if (!schemaNode.has("x-polymorphic")) {
            return;
        }
        if (!schemaNode.get("x-polymorphic").asBoolean(false)) {
            return;
        }
            String discriminator = schemaNode.has("x-discriminator")
                    ? schemaNode.get("x-discriminator").asText()
                    : "type";


            clazz.annotate(JsonTypeInfo.class)
                    .param("use", JsonTypeInfo.Id.NAME)
                    .param("include", JsonTypeInfo.As.PROPERTY)
                    .param("property", discriminator)
                    .param("visible", true);

            // mark with special annoation for runtime detection

        clazz.annotate(DynamicJson.class)
                .param("property", discriminator);
    }
}
