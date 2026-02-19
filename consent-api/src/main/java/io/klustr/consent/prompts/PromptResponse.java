package io.klustr.consent.prompts;

import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.klustr.schemas.console.consent.ConsentAuditAgent;
import org.joda.time.DateTime;

public class PromptResponse<T extends AbstractPrompt> {
    public String id;
    public String ip;
    public String session_id;
    public String subject_id;
    public String act;
    public String client_id;
    public Boolean skipped;
    public DateTime timestamp;
    public ConsentAuditAgent agent;

    public T prompt;
}
