package ru.job4j.passports_client.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.passports_client.models.Passport;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("passport")
public class PassportsMailController {


    final private KafkaTemplate<String, String> kafkaTemplate;
    final private PassportsAPIController passportsAPIController;


    public PassportsMailController(KafkaTemplate<String, String> kafkaTemplate, PassportsAPIController passportsAPIController) {
        this.kafkaTemplate = kafkaTemplate;
        this.passportsAPIController = passportsAPIController;
    }

    @Scheduled(fixedDelay = 10000)
    public void checkPassports() throws JsonProcessingException {
        System.out.println("Start checking");
        var passports = this.passportsAPIController.unavaliabe();
        if (!passports.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            UUID uuid = UUID.randomUUID();
            var message = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(passports);
            System.out.println("unavaliabe in message " + uuid.toString() + " sending");
            this.sendOrder(uuid.toString(), message);
        }
    }



    @PostMapping
    public void sendOrder(String msgId, String msg){
        kafkaTemplate.send("passports", msgId, msg);
    }
}
