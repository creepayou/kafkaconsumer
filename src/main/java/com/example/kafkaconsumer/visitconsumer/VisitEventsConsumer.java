package com.example.kafkaconsumer.visitconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class VisitEventsConsumer {

	@KafkaListener(topics = { "encounter-post"})
	public void onMessage(ConsumerRecord<Integer, String> consumerRecord)
	{
		Gson gson = new Gson();
		VisitDto visit = gson.fromJson(consumerRecord.value(), VisitDto.class);
		log.info("VisitDto: " + visit);
		log.info("Consumer Record: {}", consumerRecord);
	}
}
