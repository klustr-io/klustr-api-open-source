package io.klustr.setup;

public interface SetupTaskRunnable {
    void setup();
    boolean repeat();
}
