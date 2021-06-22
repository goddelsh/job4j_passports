package ru.job4j.mail_service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MailServiceApplication {


    @KafkaListener(topics= {"passports"})
    public void msgListener(ConsumerRecord<String, String> input){
        System.out.println("accepting " + input.key());
        System.out.println(input.value());
    }


    public static void main(String[] args) {
        SpringApplication.run(MailServiceApplication.class, args);
    }

}
