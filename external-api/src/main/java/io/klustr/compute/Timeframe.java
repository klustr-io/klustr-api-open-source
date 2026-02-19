package io.klustr.compute;

public class Timeframe {

    public String start = "NOW-1HOUR";

    public String stop = "NOW";

    public StepInterval step;

    public static class Prior {
        private int time;
        private Prior(int time) {
            this.time = time;
        }
        public Timeframe Minutes() {
            Timeframe t = new Timeframe();
            t.start = "NOW-" + time + "MINUTES";
            t.stop = "NOW";
            t.step = StepInterval.seconds(1);
            return t;
        }
        public Timeframe Hours() {
            Timeframe t = new Timeframe();
            t.start = "NOW-" + time + "HOURS";
            t.stop = "NOW";
            t.step = StepInterval.minutes(1);
            return t;
        }
        public Timeframe Days() {
            Timeframe t = new Timeframe();
            t.start = "NOW-" + time + "DAYS";
            t.stop = "NOW";
            t.step = StepInterval.hours(1);
            return t;
        }
    }

    public static Prior Prior(int v) {
        Prior t = new Prior(v);
        return t;
    }

    public static class StepInterval {
        public int value;
        public Step step;

        public StepInterval() {}

        public StepInterval(Step step, int i) {
            this.step = step;
            this.value = i;
        }

        public static StepInterval hours(int i) {
            return new StepInterval(Step.hnours, i);
        }

        public static StepInterval days(int i) {
            return new StepInterval(Step.days, i);
        }

        public static StepInterval minutes(int i) {
            return new StepInterval(Step.minutes, i);
        }
        public static StepInterval seconds(int i) {
            return new StepInterval(Step.seconds, i);
        }
    }

    public static enum Step {
        seconds,
        minutes,
        hnours,
        days
    }
}
