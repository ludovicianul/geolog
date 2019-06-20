package com.insidecoding.geolog.event;

public class FailedAttemptEvent extends LogEvent {
    private static final String USER = "user ";

    public FailedAttemptEvent(String line) {
        super(line);
    }

    @Override
    public Type type() {
        return Type.FAILED;
    }

    public String splitBy() {
        return USER;
    }

}
