package io.klustr.setup;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component
public class SetupInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final String TOKEN_FILE = "setup.token";
    private String token;

    public String getToken() {
        return this.token;
    }

    public void init() throws Exception {
        Path path = Path.of(TOKEN_FILE);

        if (Files.exists(path)) {
            // Read existing token
            token = Files.readString(path).trim();
        } else {
            // Generate new token
            token = UUID.randomUUID().toString();
            Files.writeString(path, token);
        }

        // Print token for the user
        System.out.println("================================================");
        System.out.println(" First-time setup token: " + token);
        System.out.println(" Use this endpoint: /setup/" + token);
        System.out.println("================================================");
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return false;
    }
}
