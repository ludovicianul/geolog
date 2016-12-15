package com.insidecoding.geolog.event.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.insidecoding.geolog.event.SuccessLoginEvent;

import reactor.bus.Event;
import reactor.fn.Consumer;

@reactor.spring.context.annotation.Consumer

public class SuccessLoginConsumer implements Consumer<Event<SuccessLoginEvent>> {
	private static final Logger LOG = LoggerFactory.getLogger(SuccessLoginEvent.class);

	@Autowired
	private MailSender sender;

	@Value("${notify.to}")
	private String notifyTo;

	@Override
	public void accept(Event<SuccessLoginEvent> event) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(notifyTo);
		message.setSubject("Possible breach!");
		message.setText(event.getData().ip());

		sender.send(message);

		LOG.info("being notified success");
	}

}
