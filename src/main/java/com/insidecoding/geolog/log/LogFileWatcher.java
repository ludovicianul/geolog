package com.insidecoding.geolog.log;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.insidecoding.geolog.event.FailedAttemptEvent;
import com.insidecoding.geolog.event.SuccessLoginEvent;
import com.insidecoding.geolog.event.consumer.FailedAttemptComsumer;
import com.insidecoding.geolog.event.consumer.GeoLocationFunction;
import com.insidecoding.geolog.event.consumer.SuccessLoginConsumer;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;

@Component
public class LogFileWatcher implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(LogFileWatcher.class);

	@Autowired
	private LogDescriptor ld;

	@Autowired
	private EventBus bus;

	@Autowired
	private FailedAttemptComsumer fConsumer;

	@Autowired
	private SuccessLoginConsumer sConsumer;

	@Autowired
	private GeoLocationFunction gConsumer;

	@PostConstruct
	public void configureBus() {
		bus.on(Selectors.$("failure"), fConsumer);
		bus.on(Selectors.$("success"), sConsumer);
		bus.receive(Selectors.$("geocode"), gConsumer);
	}

	@Override
	public void run(String... args) throws Exception {
		String line = null;
		try (LineNumberReader lnr = new LineNumberReader(new FileReader(new File(ld.fileLocation())))) {
			while (true) {
				line = lnr.readLine();

				if (line == null) {
					Thread.sleep(1000);
					LOG.info("waiting...");
					continue;
				}

				this.process(line);
			}
		}
	}

	private void process(String line) {
		if (line.indexOf(ld.failedPattern()) != -1) {
			bus.notify("failure", Event.wrap(FailedAttemptEvent.fromLogLine(line)));
		} else if (line.indexOf(ld.successPattern()) != -1) {
			bus.notify("success", Event.wrap(SuccessLoginEvent.fromLogLine(line)));
		}
	}

}
