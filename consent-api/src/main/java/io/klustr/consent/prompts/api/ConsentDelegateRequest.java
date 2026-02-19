package io.klustr.consent.prompts.api;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.google.common.collect.Sets;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

public class ConsentDelegateRequest {
    @JsonPropertyDescription("If the request is being made by another actor or delegated person. Used for child and supervised account approvals.")
    @Schema(description = "If the request is being made by another actor or delegated person. Used for child and supervised account approvals.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public String subject_id;

    @JsonPropertyDescription("If the request is being made by another actor or delegated person. Used for child and supervised account approvals.")
    @Schema(description = "If the request is being made by another actor or delegated person. Used for child and supervised account approvals.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public String client_id;

    @JsonPropertyDescription("If the request is being made by another actor or delegated person. Used for the original scopes requested.")
    @Schema(description = "If the request is being made by another actor or delegated person.  Used for the original scopes requested", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public Set<String> scopes = Sets.newHashSet();
}
