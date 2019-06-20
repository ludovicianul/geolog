package com.insidecoding.geolog.event;

public class SuccessLoginEvent extends LogEvent {
    private static final String FROM = "from ";

    public SuccessLoginEvent(String line) {
        super(line);
    }

    @Override
    public Type type() {
        return Type.SUCCESS;
    }

    @Override
    public String splitBy() {
        return FROM;
    }


}
