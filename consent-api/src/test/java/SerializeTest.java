import io.klustr.consent.prompts.impl.consent.AppConsentPrompt;
import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.klustr.consent.prompts.impl.consent.ExperimentConsentPrompt;
import io.klustr.utils.U;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SerializeTest {

    @Test
    public void we_can_serialize_prompts() {
        ListOfPrompts p = new ListOfPrompts();
        p.prompts.add(new AppConsentPrompt());
        p.prompts.add(new ExperimentConsentPrompt());

        System.out.println(U.toJsonPrettyFormat(p));

        ListOfPrompts listOfPrompts = U.fromJson(U.toJsonPrettyFormat(p), ListOfPrompts.class);

    }

    public static class ListOfPrompts {
        public List<AbstractPrompt> prompts = new ArrayList<>();
    }
}
