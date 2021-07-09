package me.moriya.labs.consumer.domain.service;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import me.moriya.labs.consumer.domain.model.ProducerDto;

@Service
public class ConsumerService {

    private static final Logger log = LoggerFactory.getLogger(ConsumerService.class);

    private final ObjectMapper mapper;

    public ConsumerService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @SendTo
	@KafkaListener(topics = "${kafka.request.topic}", groupId = "${kafka.group.id}")
    public String consume(final ConsumerRecord<String, String> consumer) throws JsonMappingException, JsonProcessingException {
        log.info("key: " + consumer.key());
        log.info("Headers: " + consumer.headers());
        log.info("Partion: " + consumer.partition());
        log.info("Order: " + consumer.value());

        ProducerDto dto = mapper.readValue(consumer.value(), ProducerDto.class);
        dto.setId(UUID.randomUUID().toString());

        return mapper.writeValueAsString(dto);
    }
    
}
