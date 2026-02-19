package io.klustr.exceptions;

import com.google.common.collect.Maps;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

public class StandardResponseException extends ResponseStatusException {
    private String errorId;

    private Map<String, Object> meta = Maps.newConcurrentMap();

    public StandardResponseException(HttpStatus status) {
        super(status);
    }

    public StandardResponseException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public StandardResponseException(HttpStatus status, String reason, String errorId) {
        super(status, reason);
        this.errorId = errorId;
    }

    public StandardResponseException withErrorId(String errorId) {
        this.errorId = errorId;
        return this;
    }

    public String getErrorId() {
        return errorId;
    }

    public StandardResponseException withMeta(String key, Object value) {
        this.meta.put(key, value);
        return this;
    }

    public Map<String, Object> getMetadata() {
        return this.meta;
    }
}
