package dev.mkuwan.spring.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
    private static final Logger logger = LoggerFactory.getLogger(KafkaController.class);

    private static KafkaTemplate<String, String> kafkaTemplate;

    public KafkaController(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping(path = "/send")
    public String sendKafkaTopic(@RequestParam("message") String message){
        kafkaTemplate.send("general-topic",message);

        return "Kafka send";
    }

    @PostMapping(path = "/test/")
    public String testKafka(@RequestParam("value2") String value){
        return "ok, ok" + value;
    }

}
