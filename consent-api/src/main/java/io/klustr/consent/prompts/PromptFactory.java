package io.klustr.consent.prompts;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.klustr.consent.prompts.api.ConsentPromptRequest;
import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.klustr.utils.U;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class PromptFactory {

    private final List<PromptProvider> filters;

    public PromptFactory(List<PromptProvider> filters, ObjectMapper objectMapper) {
        this.filters = filters;
    }

    public List<AbstractPrompt> create(ConsentPromptRequest request) {

        List<AbstractPrompt> results = new ArrayList<>();
        this.filters.sort(new Comparator<PromptProvider>() {
            @Override
            public int compare(PromptProvider o1, PromptProvider o2) {
                return o1.getRank().compareTo(o2.getRank());
            }
        });
        this.filters.forEach(f -> {
            List<AbstractPrompt> prompts = f.create(request);
            results.addAll(prompts);
        });
        return results;
    }
}
