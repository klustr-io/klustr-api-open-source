package io.klustr.console.setup.steps;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

@com.fasterxml.jackson.annotation.JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME,
        include = com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY,
        visible = true,
        property = "type")
@com.fasterxml.jackson.annotation.JsonSubTypes({
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = AccountSetupPrompt.class, name = "account-setup"),
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = OrgSetupPrompt.class, name = "domain-setup")
})
public abstract class SetupPrompt {

    /**
     * The date that this setup prompt was issued to
     * a particular account.
     */
    public DateTime issue_date;

    /**
     * If this setup prompt can be skipped.
     */
    @JsonProperty("skippable")
    public abstract boolean canSkip();

    /**
     * The due date for this setup prompt.
     */
    public DateTime due_date;

    /**
     * The date that this setup step was completed.
     */
    public DateTime complete_date;

    /**
     * Returns the current status of this setup given the various
     * dates.
     *
     * @return Returns the status of this setup.
     */
    public SetupStatus getStatus() {
        if (this.complete_date != null) {
            return SetupStatus.COMPLETE;
        }
        if (this.due_date != null && this.due_date.isBeforeNow()) {
            return SetupStatus.EXPIRED;
        }
        return SetupStatus.PENDING;
    }

}
