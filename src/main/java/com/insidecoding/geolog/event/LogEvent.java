package com.insidecoding.geolog.event;

import javax.persistence.Id;

public abstract class LogEvent {
	protected String user;

	@Id
	protected String ip;

	public String user() {
		return user;
	}

	public String ip() {
		return ip;
	}

	public abstract Type type();

	@Override
	public String toString() {
		return "LogEvent [user()=" + user() + ", ip()=" + ip() + ", type()=" + type() + "]";
	}

}

enum Type {
	FAILED, SUCCESS;
}