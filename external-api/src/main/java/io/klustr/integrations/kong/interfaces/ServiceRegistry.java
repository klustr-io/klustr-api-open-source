package io.klustr.integrations.kong.interfaces;

import io.klustr.schemas.console.EntityReference;
import io.klustr.schemas.services.ApiServiceSpecification;

public interface ServiceRegistry {
    EntityReference create(ApiServiceSpecification obj);

    void delete(ApiServiceSpecification obj);

    EntityReference update(ApiServiceSpecification obj);
}
