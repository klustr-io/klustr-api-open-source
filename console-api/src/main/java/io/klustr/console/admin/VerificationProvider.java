package io.klustr.console.admin;

import io.klustr.schemas.console.identity.IdentityTraits;
import io.klustr.schemas.console.projects.Project;

/**
 * Will submit the verification request.
 */
public interface VerificationProvider {
    void verification(Project project, IdentityTraits traits);

    void trust(Project project, IdentityTraits traits);
}
