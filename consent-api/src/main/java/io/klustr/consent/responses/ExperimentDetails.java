package io.klustr.consent.responses;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.klustr.schemas.console.consent.types.UserExperimentConsent;

public class ExperimentDetails {
    @JsonPropertyDescription("The experiment details")
    public ExperimentReference details;
    @JsonPropertyDescription("The consent details")
    public UserExperimentConsent consent;
}