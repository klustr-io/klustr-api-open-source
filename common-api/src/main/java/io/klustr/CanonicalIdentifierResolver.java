package io.klustr;

/**
 * Manages the complexity of translating a canonical identifier.
 */
public interface CanonicalIdentifierResolver {

    /**
     * Returns the canonical ID of the user principle passed in.
     * @param subjectId The subjectId to get the canonical ID for.
     * @return The mapped subject ID given PPID
     */
    String getCanonicalId(String orgId, String subjectId);
}
