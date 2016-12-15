package com.insidecoding.geolog.event;

public class FailedAttemptEvent extends LogEvent {
	private static final int RHOST = 6;
	private static final int USER = 5;

	@Override
	public Type type() {
		return Type.FAILED;
	}

	public static FailedAttemptEvent fromLogLine(String line) {
		FailedAttemptEvent ev = new FailedAttemptEvent();
		String[] userAndIp = line.substring(line.indexOf("rhost")).split("\\s+");
		ev.ip = userAndIp[0].substring(RHOST);
		if (userAndIp.length > 1) {
			ev.user = userAndIp[1].substring(USER);
		}

		return ev;
	}

}
