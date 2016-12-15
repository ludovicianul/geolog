package com.insidecoding.geolog.log.os;

import org.springframework.stereotype.Component;

import com.insidecoding.geolog.log.LogDescriptor;

@Component
public class UbuntuLogDescriptor implements LogDescriptor {

	@Override
	public String fileLocation() {
		return "/var/log/auth.log";
	}

	@Override
	public String failedPattern() {
		return "authentication failure;";
	}

	@Override
	public String successPattern() {
		return "Accepted password";
	}

}
