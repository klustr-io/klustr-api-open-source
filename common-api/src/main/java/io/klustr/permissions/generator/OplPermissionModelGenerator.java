package io.klustr.permissions.generator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OplPermissionModelGenerator {

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    /**
     * Generate OPL code from a YAML model provided as an InputStream.
     *
     * @param yamlStream input stream of the YAML model
     * @return OPL source as a string
     */
    public String generateFromYaml(InputStream yamlStream) throws IOException {
        JsonNode root = mapper.readTree(yamlStream);
        StringBuilder sb = new StringBuilder();

        sb.append("import { Namespace, Context } from \"@ory/keto-namespace-types\";\n\n");
        sb.append("class user implements Namespace {}\n\n");

        // Start recursion on top-level nodes
        for (Iterator<String> it = root.fieldNames(); it.hasNext();) {
            String topLevel = it.next();
            JsonNode children = root.get(topLevel);
            emitNamespace(sb, toPascalCase(topLevel), null, children);
        }

        return sb.toString();
    }

    private void emitNamespace(StringBuilder sb, String name, String parent, JsonNode children) {
        sb.append("class ").append(name).append(" implements Namespace {\n");

        // related block
        sb.append("  related: {\n");
        sb.append("    owners: user[]\n");
        sb.append("    admins: user[]\n");
        sb.append("    editors: user[]\n");
        sb.append("    viewers: user[]\n");
        if (parent != null) {
            sb.append("    parents: ").append(toPascalCase(parent)).append("[]\n");
        }



        // add explicit child relation lists
        if (children != null && children.isArray()) {
            for (JsonNode child : children) {
                if (child.isTextual()) {
                    emitChildRelations(sb, child.asText());
                } else if (child.isObject()) {
                    for (Iterator<String> it = child.fieldNames(); it.hasNext();) {
                        String childName = it.next();
                        emitChildRelations(sb, childName);
                    }
                }
            }
        }
        sb.append("  }\n\n");

        // permits block
        sb.append("  permits = {\n");
        emitCorePermits(sb, parent);

        if (children != null && children.isArray()) {
            for (JsonNode child : children) {
                if (child.isTextual()) {
                    emitChildPermits(sb, child.asText());
                } else if (child.isObject()) {
                    for (Iterator<String> it = child.fieldNames(); it.hasNext();) {
                        emitChildPermits(sb, it.next());
                    }
                }
            }
        }

        sb.append("  }\n}\n\n");

        // emit child classes after parent is closed
        if (children != null && children.isArray()) {
            for (JsonNode child : children) {
                if (child.isTextual()) {
                    emitNamespace(sb, toPascalCase(child.asText()), name, null);
                } else if (child.isObject()) {
                    for (Iterator<String> it = child.fieldNames(); it.hasNext();) {
                        String childName = it.next();
                        emitNamespace(sb, toPascalCase(childName), name, child.get(childName));
                    }
                }
            }
        }
    }

    private void emitCorePermits(StringBuilder sb, String parent) {
        sb.append("    admin: (ctx: Context): boolean =>\n");
        sb.append("      this.related.admins.includes(ctx.subject) ||\n");
        sb.append("      this.related.owners.includes(ctx.subject)");
        if (parent != null) {
            sb.append(" || this.related.parents.traverse(p => p.permits.admin(ctx))");
        }
        sb.append(",\n\n");

        sb.append("    read: (ctx: Context): boolean =>\n");
        sb.append("      this.related.viewers.includes(ctx.subject) ||\n");
        sb.append("      this.related.editors.includes(ctx.subject) ||\n");
        sb.append("      this.related.owners.includes(ctx.subject) ||\n");
        sb.append("      this.permits.admin(ctx)");
        if (parent != null) {
            sb.append(" || this.related.parents.traverse(p => p.permits.read(ctx))");
        }
        sb.append(",\n\n");

        sb.append("    update: (ctx: Context): boolean =>\n");
        sb.append("      this.related.editors.includes(ctx.subject) ||\n");
        sb.append("      this.related.owners.includes(ctx.subject) ||\n");
        sb.append("      this.permits.admin(ctx)");
        if (parent != null) {
            sb.append(" || this.related.parents.traverse(p => p.permits.update(ctx))");
        }
        sb.append(",\n\n");

        sb.append("    delete: (ctx: Context): boolean =>\n");
        sb.append("      this.related.owners.includes(ctx.subject) ||\n");
        sb.append("      this.permits.admin(ctx)");
        if (parent != null) {
            sb.append(" || this.related.parents.traverse(p => p.permits.delete(ctx))");
        }
        sb.append(",\n\n");

//        sb.append("    members: (ctx: Context): boolean =>\n");
//        sb.append("      this.related.owners.includes(ctx.subject) ||\n");
//        sb.append("      this.related.admins.includes(ctx.subject) ||\n");
//        sb.append("      this.related.editors.includes(ctx.subject) ||\n");
//        sb.append("      this.related.viewers.includes(ctx.subject)");
//
//        sb.append(",\n\n");
    }

    private void emitChildRelations(StringBuilder sb, String child) {
        sb.append("    ").append(child).append("_creators: user[]\n");
        sb.append("    ").append(child).append("_readers: user[]\n");
        sb.append("    ").append(child).append("_editors: user[]\n");
        sb.append("    ").append(child).append("_deleters: user[]\n");
    }

    private void emitChildPermits(StringBuilder sb, String child) {
        sb.append("    ").append(child).append("_read: (ctx: Context): boolean =>\n");
        sb.append("      this.related.").append(child).append("_readers.includes(ctx.subject) ||\n");
        sb.append("      this.permits.read(ctx) || this.permits.admin(ctx),\n\n");

        sb.append("    ").append(child).append("_create: (ctx: Context): boolean =>\n");
        sb.append("      this.related.").append(child).append("_creators.includes(ctx.subject) ||\n");
        sb.append("      this.permits.admin(ctx),\n\n");

        sb.append("    ").append(child).append("_update: (ctx: Context): boolean =>\n");
        sb.append("      this.related.").append(child).append("_editors.includes(ctx.subject) ||\n");
        sb.append("      this.permits.update(ctx) || this.permits.admin(ctx),\n\n");

        sb.append("    ").append(child).append("_delete: (ctx: Context): boolean =>\n");
        sb.append("      this.related.").append(child).append("_deleters.includes(ctx.subject) ||\n");
        sb.append("      this.permits.admin(ctx),\n\n");
    }

//    private String capitalize(String name) {
//        if (name == null || name.isEmpty()) return name;
//        return name.substring(0, 1).toUpperCase() + name.substring(1);
//    }

    private static String toPascalCase(String name) {
        return name;
//        StringBuilder result = new StringBuilder();
//        for (String part : name.split("_")) {
//            if (!part.isEmpty()) {
//                result.append(Character.toUpperCase(part.charAt(0)));
//                if (part.length() > 1) {
//                    result.append(part.substring(1));
//                }
//            }
//        }
//        return result.toString();
    }
}
