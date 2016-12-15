package com.insidecoding.geolog.event;

public class SuccessLoginEvent extends LogEvent {

	@Override
	public Type type() {
		return Type.SUCCESS;
	}

	public static SuccessLoginEvent fromLogLine(String line) {
		SuccessLoginEvent ev = new SuccessLoginEvent();
		String[] userAndIp = line.substring(line.indexOf("for")).split("\\s+");
		ev.user = userAndIp[1];
		ev.ip = userAndIp[3];

		return ev;
	}

}
