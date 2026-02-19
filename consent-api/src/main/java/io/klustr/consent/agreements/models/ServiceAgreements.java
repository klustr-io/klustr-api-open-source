package io.klustr.consent.agreements.models;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.klustr.schemas.console.EntityReference;
import io.klustr.schemas.console.consent.types.UserAgreementConsent;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class ServiceAgreements {

    @JsonPropertyDescription("Organization that is the legal entity owning and operating the service.")
    @Schema(description = "Organization that is the legal entity owning and operating the service.")
    public EntityReference org;

    @JsonPropertyDescription("The product or service that an agreement was made with.")
    @Schema(description = "The product or service that an agreement was made with.")
    public EntityReference service;

    @JsonPropertyDescription("Agreements the user has made with the organization.")
    @Schema(description = "Agreements the user has made with the organization.")
    public List<UserAgreementConsent> agreements;
}
