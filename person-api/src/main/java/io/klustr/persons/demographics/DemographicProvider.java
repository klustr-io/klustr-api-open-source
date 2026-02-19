package io.klustr.persons.demographics;

import io.klustr.schemas.persons.demographics.Demographic;

public interface DemographicProvider {

    Demographic getDemographics(String personId);
}
