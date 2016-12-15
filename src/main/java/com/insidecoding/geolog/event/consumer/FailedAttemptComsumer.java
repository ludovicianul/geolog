package com.insidecoding.geolog.event.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insidecoding.geolog.event.FailedAttemptEvent;
import com.insidecoding.geolog.jpa.LogEventJpaRepo;
import com.insidecoding.geolog.jpa.entity.LogFailedEntity;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

@Service
public class FailedAttemptComsumer implements Consumer<Event<FailedAttemptEvent>> {
	private static final Logger LOG = LoggerFactory.getLogger(FailedAttemptEvent.class);

	@Autowired
	private LogEventJpaRepo repo;

	@Autowired
	private EventBus bus;

	@Override
	public void accept(Event<FailedAttemptEvent> event) {
		String ip = event.getData().ip();
		LogFailedEntity entity = repo.findByIp(ip);
		if (entity != null) {
			LOG.info(ip + " :: already in. increasing attempts!");
			entity.increaseAttempts();
			repo.saveAndFlush(entity);
		} else {
			bus.sendAndReceive("geocode", Event.wrap(ip), ev -> {
				LogFailedEntity logEntity = (LogFailedEntity) ev.getData();
				LogFailedEntity existing = repo.findByIp(logEntity.getIp());
				if (existing != null) {
					existing.increaseAttempts();
					repo.saveAndFlush(existing);
				} else {
					repo.saveAndFlush(logEntity);
				}
				LOG.info(ip + " :: geocode and persist!");
			});
		}
	}

}
