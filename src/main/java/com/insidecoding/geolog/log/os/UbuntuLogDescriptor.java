package com.insidecoding.geolog.log.os;

import com.insidecoding.geolog.log.LogDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Component
public class UbuntuLogDescriptor implements LogDescriptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(UbuntuLogDescriptor.class);
    private static final String CONFIG_FILE_LOCATION = "/opt/geolog/geolog.config";
    private static String[] FAILED_PATTERNS = {"authentication failure", "disconnected from invalid user", "disconnected from authenticating", "connection closed by"};
    private static String[] SUCCESS_PATTERNS = {"accepted password", "accepted publickey"};

    @PostConstruct
    public void setupPatterns() {
        File file = new File(CONFIG_FILE_LOCATION);
        if (file.exists()) {
            try {
                Properties properties = new Properties();
                properties.load(new FileReader(file));
                FAILED_PATTERNS = properties.getProperty("failed_patterns").split(",");
                SUCCESS_PATTERNS = properties.getProperty("success_patterns").split(",");
            } catch (IOException e) {
                LOGGER.error("Exception while loading config file {}, details {}", CONFIG_FILE_LOCATION, e.getMessage());
            }
        }
    }

    @Override
    public String fileLocation() {
        return "/var/log/auth.log";
    }

    @Override
    public String[] failedPatterns() {
        return FAILED_PATTERNS;
    }

    @Override
    public String[] successPatterns() {
        return SUCCESS_PATTERNS;
    }

}
