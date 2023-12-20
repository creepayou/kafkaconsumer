package com.example.kafkaconsumer.visitconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.converter.ConversionException;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.handler.invocation.MethodArgumentResolutionException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import com.example.kafkaconsumer.util.JsonUtil;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class VisitEventsConsumer {

	@RetryableTopic(kafkaTemplate = "kafkaTemplate",
        exclude = {DeserializationException.class,
                MessageConversionException.class,
                ConversionException.class,
                MethodArgumentResolutionException.class,
                NoSuchMethodException.class,
                ClassCastException.class,
				MismatchedInputException.class
			},
        attempts = "3",
        backoff = @Backoff(delay = 3000, multiplier = 1.5, maxDelay = 15000)
	)

	@KafkaListener(topics = { "encounter-post"})
	public void onMessage(ConsumerRecord<Integer, String> consumerRecord) throws Exception
	{
		VisitDto visit = JsonUtil.fromJson(consumerRecord.value(), VisitDto.class);
		if(visit.getVisitId().equals(124l)) throw new Exception("Testing With Retries.");
		log.info("VisitDto: " + visit);
		log.info("Consumer Record: {}", consumerRecord);
	}

	@DltHandler
	public void processMessage(Object message) {
		log.error("DltHandler processMessage = {}", message);
	}
}
