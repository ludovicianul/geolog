package com.insidecoding.geolog.event.consumer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.insidecoding.geolog.jpa.entity.LogFailedEntity;

import reactor.bus.Event;
import reactor.fn.Function;

@Component
public class GeoLocationFunction implements Function<Event<String>, LogFailedEntity> {
	private static final Logger LOG = LoggerFactory.getLogger(GeoLocationFunction.class);

	private static AtomicInteger requests = new AtomicInteger(0);

	private long t0 = System.currentTimeMillis();

	@Value("${geo.limit}")
	private int limit;

	@Value("${geo.address}")
	private String address;

	@Value("${geo.timeFrame}")
	private int timeFrame;

	@Autowired
	private CountDownLatch latch;

	@Override
	public LogFailedEntity apply(Event<String> ip) {
		if (!this.conditionsMet()) {
			try {
				waitAndReset();
			} catch (InterruptedException e) {
				LOG.error("Something went wrong while waiting");
			}
		}

		RestTemplate temp = new RestTemplate();
		LogFailedEntity entity = temp.getForObject(address + ip.getData(), LogFailedEntity.class);
		entity.setIp(ip.getData());
		requests.incrementAndGet();

		return entity;
	}

	private void waitAndReset() throws InterruptedException {
		latch.await(timeFrame - (System.currentTimeMillis() - t0) / 1000, TimeUnit.SECONDS);
		LOG.info("Wait is over... moving ahead");
		t0 = System.currentTimeMillis();
		requests.set(0);
	}

	private boolean conditionsMet() {
		LOG.info("Geo requests: " + requests);
		if (((System.currentTimeMillis() - t0) / 1000 < timeFrame) && (requests.get() >= limit)) {
			LOG.info("Limit exceeded. Waiting...");
			return false;
		}

		return true;
	}
}
