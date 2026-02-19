package io.klustr.json;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import io.github.classgraph.ClassGraph;

import java.util.*;

public final class DynamicSubtypeRegistrar {

    private DynamicSubtypeRegistrar() {}

    public static void register(ObjectMapper mapper) {
        // Scan classpath once. This will only pick up your annotated generated classes.
        try (var scan = new ClassGraph()
                .enableClassInfo()
                .enableAnnotationInfo()
                .scan()) {

            // Bases that opted in
            var baseInfos = scan.getClassesWithAnnotation(DynamicJson.class.getName());

            // Subtypes with stable names
            var subtypeInfos = scan.getClassesWithAnnotation(JsonTypeName.class.getName());

            // Load bases
            List<Class<?>> bases = new ArrayList<>(baseInfos.size());
            for (var bi : baseInfos) {
                bases.add(bi.loadClass());
            }

            // Load subtypes and register ones assignable to any base
            List<NamedType> named = new ArrayList<>(subtypeInfos.size());
            for (var si : subtypeInfos) {
                Class<?> subtype = si.loadClass();
                JsonTypeName tn = subtype.getAnnotation(JsonTypeName.class);
                if (tn == null) continue;

                String name = tn.value();
                if (name == null || name.isBlank()) continue;

                // Only register if subtype belongs to at least one @DynamicJson base
                boolean matches = false;
                for (Class<?> base : bases) {
                    if (base.isAssignableFrom(subtype)) {
                        matches = true;
                        break;
                    }
                }

                if (matches) {
                    named.add(new NamedType(subtype, name));
                }
            }

            if (!named.isEmpty()) {
                mapper.registerSubtypes(named.toArray(new NamedType[0]));
            }
        }
    }
}
