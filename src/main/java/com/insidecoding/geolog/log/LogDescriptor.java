package com.insidecoding.geolog.log;

public interface LogDescriptor {

    String fileLocation();

    String[] failedPatterns();

    String[] successPatterns();
}
