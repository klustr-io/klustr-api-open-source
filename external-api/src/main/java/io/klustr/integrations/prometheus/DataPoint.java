package io.klustr.integrations.prometheus;

import org.joda.time.DateTime;

public class DataPoint {
    private DateTime time;
    private double value;

    public DataPoint(DateTime time, double value) {
        this.time = time;
        this.value = value;
    }

    public DateTime getTime() {
        return time;
    }

    public double getValue() {
        return value;
    }
}
