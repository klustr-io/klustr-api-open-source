package io.klustr.consent.experiments;

import io.klustr.schemas.console.consent.types.UserExperimentConsent;

public class ExperimentEvent {
    public String subject_id;
    public UserExperimentConsent experiment;
    public String org_id;
    public String client_id;
    public ExperimentEventType type;

}
