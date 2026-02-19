package io.klustr.storage.collections;

import org.codehaus.jackson.JsonNode;

import java.util.Optional;

/**
 * Wraps the generic klustr collection API and enables you to make
 * nice document collections at the start with full event backing.
 */
public class KlustrCollection {

    private final String url;
    private final String token;

    public KlustrCollection(String url, String token) {
        this.url = url;
        this.token = token;
    }

    // CRUD & SEARCH
    // DB DELETE
    // TABLE DELETE
    // HEALTH CHECK
}
