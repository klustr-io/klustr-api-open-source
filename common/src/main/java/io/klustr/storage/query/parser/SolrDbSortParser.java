package io.klustr.storage.query.parser;

import io.klustr.storage.query.DbSort;

import java.util.ArrayList;
import java.util.List;

public class SolrDbSortParser {

    public static List<DbSort> parse(String input) {
        if (input == null || input.isBlank()) {
            return List.of();
        }

        String[] parts = input.split(",");
        List<DbSort> sorts = new ArrayList<>();

        for (String part : parts) {
            String[] tokens = part.trim().split("\\s+");
            if (tokens.length == 0) continue;

            String field = tokens[0].trim();
            boolean asc = true; // default
            if (tokens.length > 1) {
                String dir = tokens[1].toLowerCase();
                asc = dir.equals("asc");
            }

            if (asc) {
                sorts.add(DbSort.asc(field));
            } else {
                sorts.add(DbSort.dsc(field));
            }
        }

        return sorts;
    }
}
