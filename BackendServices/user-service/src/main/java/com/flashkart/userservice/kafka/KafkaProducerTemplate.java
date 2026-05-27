package com.flashkart.userservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerTemplate {

	@KafkaListener(
			topics = "user-topic",
			groupId = "flashkart-group"
			)
	
	public void consume(String message) {
		System.out.println("Consumed Message : " + message);
	}
}
