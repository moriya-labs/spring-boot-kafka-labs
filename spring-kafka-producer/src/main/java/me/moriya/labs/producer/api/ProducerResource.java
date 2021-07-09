package me.moriya.labs.producer.api;

import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.moriya.labs.producer.domain.model.ProducerDto;
import me.moriya.labs.producer.domain.service.ProducerService;

@RestController
@RequestMapping("/producer")
public class ProducerResource {

    private final ProducerService service;

    public ProducerResource(ProducerService service) {
        this.service = service;
    }
    
    @PostMapping
    public ProducerDto send(@RequestBody ProducerDto dto) throws TimeoutException, JsonProcessingException, InterruptedException, ExecutionException {
        return service.send(dto);
    }
}
