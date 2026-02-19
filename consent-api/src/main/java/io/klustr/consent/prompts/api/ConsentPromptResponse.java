package io.klustr.consent.prompts.api;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.klustr.schemas.console.apps.App;
import io.klustr.schemas.integrations.ory.IdentityProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import io.klustr.schemas.console.EntityReference;

import java.util.List;
import java.util.Set;

public class ConsentPromptResponse {

    @JsonPropertyDescription("Consent is supervisor and someone is acting on-behalf of the user.")
    @Schema(description = "Consent is supervisor and someone is acting on-behalf of the user.")
    public IdentityProfile supervisor;

    @JsonPropertyDescription("The organization related to this consent request.")
    @Schema(description = "The organization related to this consent request.")
    public EntityReference organization;

    @JsonPropertyDescription("The prompts to give the user relating to consent.")
    @Schema(description = "The prompts to give the user relating to consent.")
    public List<AbstractPrompt> prompts = Lists.newArrayList();

    @JsonPropertyDescription("Information on the user and their current status.")
    @Schema(description = "Information on the user and their current status.")
    public IdentityProfile identity = new IdentityProfile();

    @JsonPropertyDescription("The scopes the user has already approved.")
    @Schema(description = "The scopes the user has already approved.")
    public Set<String> existing_scopes = Sets.newHashSet();

    @JsonPropertyDescription("The experiments that are active.")
    @Schema(description = "The experiments that are active.")
    public Set<String> existing_experiments = Sets.newHashSet();

    @JsonPropertyDescription("The metadata regarding the client being issued.")
    @Schema(description = "The metadata regarding the client being issued.")
    public ConsentPromptClientMetadata client;
}
