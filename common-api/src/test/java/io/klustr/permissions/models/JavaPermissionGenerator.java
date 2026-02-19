package io.klustr.permissions.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

public class JavaPermissionGenerator {

    private static final String[] VERBS = {"Create", "Get", "List", "Update", "Delete", "View"};
    private static final String[] RELATIONS = {"Owners", "Admins", "Editors", "Viewers"};

    public static String generate(InputStream yaml) throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        JsonNode root = mapper.readTree(yaml);

        StringBuilder sb = new StringBuilder();
        sb.append("public final class IAM {\n");

        for (Iterator<Map.Entry<String, JsonNode>> it = root.fields(); it.hasNext();) {
            Map.Entry<String, JsonNode> entry = it.next();
            generateNamespace(sb, entry.getKey(), entry.getValue(), "", 1);
        }

        sb.append("    private IAM() {}\n");
        sb.append("}\n");

        return sb.toString();
    }

    private static void generateNamespace(StringBuilder sb, String name, JsonNode node,
                                          String parentPath, int indent) {
        String fqPath = parentPath.isEmpty()
                ? name.toLowerCase()
                : parentPath + "." + name.toLowerCase();
        String pad = "    ".repeat(indent);
        String className = toClassName(name);

        // Start class
        sb.append(pad).append("public static final class ").append(className);
        if (!parentPath.isEmpty()) sb.append(" implements IAMChild");
        sb.append(" {\n");

        // IAMChild impl
        if (!parentPath.isEmpty()) {
            sb.append(pad).append("    @Override public String namespace() { return \"")
                    .append(fqPath).append("\"; }\n\n");
        }

        // Permissions block
        sb.append(pad).append("    public static final class Permissions {\n");
        for (String verb : VERBS) {
            sb.append(pad).append("        public static IAMPermission ").append(verb)
                    .append("() { return () -> \"").append(fqPath).append(".")
                    .append(verb.toLowerCase()).append("\"; }\n");
        }
        sb.append(pad).append("    }\n\n");

        // Relations block
        sb.append(pad).append("    public static final class Relations {\n");
        for (String rel : RELATIONS) {
            sb.append(pad).append("        public static IAMRelation ").append(rel)
                    .append("() { return () -> \"").append(fqPath).append(".")
                    .append(rel.toLowerCase()).append("\"; }\n");
        }
        sb.append(pad).append("    }\n\n");

        // Id helpers
        sb.append(pad).append("    public static IdTarget Id(String id) { return new IdTarget(\"")
                .append(name.toLowerCase()).append("\", id); }\n");
        if (parentPath.isEmpty()) {
            sb.append(pad).append("    public static IdTarget Any() { return new IdTarget(\"")
                    .append(name.toLowerCase()).append("\", \"*\"); }\n");
        } else {
            sb.append(pad).append("    public static IdTarget In(String parentId) { return new IdTarget(\"")
                    .append(name.toLowerCase()).append("\", parentId); }\n");
        }
        sb.append("\n");

        // Children
        if (node != null) {
            if (node.isArray()) {
                for (JsonNode child : node) {
                    if (child.isTextual()) {
                        generateNamespace(sb, child.asText(), null, fqPath, indent + 1);
                    } else if (child.isObject()) {
                        for (Iterator<Map.Entry<String, JsonNode>> it = child.fields(); it.hasNext();) {
                            Map.Entry<String, JsonNode> c = it.next();
                            generateNamespace(sb, c.getKey(), c.getValue(), fqPath, indent + 1);
                        }
                    }
                }
            }
        }

        sb.append(pad).append("}\n");
    }

    private static String toClassName(String name) {
        String[] parts = name.split("_");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            sb.append(p.substring(0,1).toUpperCase()).append(p.substring(1).toLowerCase());
        }
        return sb.toString();
    }
}
