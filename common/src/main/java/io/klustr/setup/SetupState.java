package io.klustr.setup;

import org.joda.time.DateTime;

public class SetupState {
    public String id;
    public SetupState() {}

    public SetupState(String id) {
        this.id = id;
        this.timestamp = DateTime.now();
    }

    public DateTime timestamp;
}
