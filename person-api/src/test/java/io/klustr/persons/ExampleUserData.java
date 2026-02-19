package io.klustr.persons;

import io.klustr.schemas.integrations.ory.Identity;
import io.klustr.utils.U;
import org.junit.platform.commons.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Function;

public class ExampleUserData {


    public void read(Function<Identity, Boolean> callback) {
        BufferedReader reader;

        try {
            InputStream stream = U.getResourceAsStream("users.json", this);
            reader = new BufferedReader(new InputStreamReader(stream));
            String line = reader.readLine();
            int i = 0;

            while (line != null) {
                i++;
                // read next line
                line = reader.readLine();
                if (StringUtils.isBlank(line)) continue;
                Identity p = U.fromJson(line, Identity.class);
                callback.apply(p);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
