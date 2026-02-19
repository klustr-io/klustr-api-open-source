package io.klustr.consent.agreements.models;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.klustr.schemas.console.EntityReference;
import io.klustr.schemas.console.consent.types.UserAgreementConsent;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class OrganizationAgreements {

    @JsonPropertyDescription("Organization that owns this agreement.")
    @Schema(description = "Organization that owns this agreement.")
    public EntityReference org;

    @JsonPropertyDescription("Agreements the user has made with the organization.")
    @Schema(description = "Agreements the user has made with the organization.")
    public List<UserAgreementConsent> agreements;
}
