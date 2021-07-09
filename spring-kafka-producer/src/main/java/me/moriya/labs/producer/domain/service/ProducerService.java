package me.moriya.labs.producer.domain.service;

import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import me.moriya.labs.producer.domain.model.ProducerDto;

@Service
public class ProducerService {

    private static final Logger log = LoggerFactory.getLogger(ProducerService.class);

    @Value("${kafka.request.topic}")
    private String requestTopic;

    private final ObjectMapper mapper;

    private final ReplyingKafkaTemplate<String, String, String> kafkaTemplate;
    
	public ProducerService(ObjectMapper mapper, ReplyingKafkaTemplate<String, String, String> kafkaTemplate) {
        this.mapper = mapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public ProducerDto send(final ProducerDto producer) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {
        ProducerRecord<String, String> record = new ProducerRecord<>(requestTopic, mapper.writeValueAsString(producer));
        RequestReplyFuture<String, String, String> reply = this.kafkaTemplate.sendAndReceive(record);
        ConsumerRecord<String, String> response = reply.get();

        ProducerDto dto = mapper.readValue(response.value(), ProducerDto.class);

        log.info("Recebendo resposta {}", dto);
        
        return dto;
    }
    
}
