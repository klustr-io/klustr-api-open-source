package io.klustr.persons.ekyc;

import com.google.common.collect.Maps;
import io.klustr.schemas.persons.verification.PersonVerificationSession;

import java.util.Map;

public class EkycUserSession {

    /**
     * The user ID who made this request.
     */
    public String id;

    public Map<String, PersonVerificationSession> sessions = Maps.newConcurrentMap();
}
