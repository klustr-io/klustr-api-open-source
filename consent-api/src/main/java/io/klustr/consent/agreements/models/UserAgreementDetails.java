package io.klustr.consent.agreements.models;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.google.common.collect.Maps;
import io.klustr.schemas.console.agreements.Agreement;
import io.klustr.schemas.console.consent.types.UserAgreementConsent;
import io.swagger.v3.oas.annotations.media.Schema;
import io.klustr.schemas.console.EntityReference;

import java.util.List;
import java.util.Map;

public class UserAgreementDetails {

    @JsonPropertyDescription("Organizations referenced by the agreements.")
    @Schema(description = "Organizations referenced by the agreements.")
    public Map<String, EntityReference> orgs = Maps.newConcurrentMap();

    @JsonPropertyDescription("Agreements the user has made.")
    @Schema(description = "Agreements the user has made.")
    public List<UserAgreementConsent> agreements;

    @JsonPropertyDescription("Reference data about the agreement for lookup.")
    @Schema(description = "Reference data about the agreement for lookup.")
    public Map<String, Agreement> details = Maps.newConcurrentMap();
}
