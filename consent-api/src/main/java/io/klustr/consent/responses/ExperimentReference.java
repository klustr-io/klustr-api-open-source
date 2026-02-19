package io.klustr.consent.responses;

import io.klustr.schemas.console.experiments.Experiment;
import io.klustr.schemas.console.experiments.ExperimentMedia;
import org.joda.time.DateTime;

public class ExperimentReference {
    public String id;
    public String name;
    public String description;
    public ExperimentMedia media;
    public String privacyUrl;
    public String infoUrl;
    public String tosUrl;
    public DateTime start_date;
    public DateTime stop_date;
    public ExperimentReference() {
    }
    public ExperimentReference(Experiment ex) {
        this.id = ex.getId();
        this.name = ex.getName();
        this.description = ex.getDescription();
        this.media = ex.getMedia();
        this.privacyUrl = ex.getPrivacyUrl();
        this.tosUrl = ex.getTosUrl();
        this.infoUrl = ex.getInfoUrl();
        this.start_date = ex.getStartDate();
        this.stop_date = ex.getStopDate();
    }
}
