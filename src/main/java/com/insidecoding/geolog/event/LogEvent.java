package com.insidecoding.geolog.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class LogEvent {
    private static final Logger LOG = LoggerFactory.getLogger(LogEvent.class);
    private static final String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    private Pattern ipPattern = Pattern.compile(IPADDRESS_PATTERN);

    private String user;

    private String ip;

    public String user() {
        return user;
    }

    public String ip() {
        return ip;
    }

    public LogEvent(String line) {
        this.fromLogLine(line);
    }


    public abstract Type type();

    public abstract String splitBy();

    @Override
    public String toString() {
        return "LogEvent [user()=" + user() + ", ip()=" + ip() + ", type()=" + type() + "]";
    }

    public LogEvent fromLogLine(String line) {
        LOG.info("parsing line {}", line);
        Matcher matcher = ipPattern.matcher(line);
        if (matcher.find()) {
            this.ip = matcher.group();
        }
        this.user = getUser(line, splitBy());
        return this;
    }

    static String getUser(String line, String splitBy) {
        if (line.contains(splitBy)) {
            int indexOf = line.indexOf(splitBy) + splitBy.length();
            return line.substring(indexOf).split(" ")[0].trim();
        }
        return null;
    }

}

enum Type {
    FAILED, SUCCESS;
}