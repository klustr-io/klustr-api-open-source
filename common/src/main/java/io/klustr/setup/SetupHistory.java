package io.klustr.setup;

import org.joda.time.DateTime;

public class SetupHistory {
    public String id;
    public DateTime timestamp = DateTime.now();
    public SetupHistory(String id) {
        this.id = id;
    }
    @Deprecated
    public SetupHistory() {
    }
}
