package com.lambdaschool.cities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.Random;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class CitiesApplication {
  public static final String EXCHANGE_NAME = "CitiesServer";
  public static final String QUEUE_NAME_CITIES1 = "CitiesOneQueue";
  public static final String QUEUE_NAME_CITIES2 = "CitiesTwoQueue";
  public static final String QUEUE_NAME_SECRET = "SecretQueue";

  public static void sendMessages(CityRepository repository, RabbitTemplate template, CheckCity tester) {
    ArrayList<City> cities = new ArrayList<>();
    cities.addAll(repository.findAll());

    for (City city : cities) {
      int affordabilityIndex = new Random().nextInt(10);
      boolean secret = new Random().nextBoolean();
      final CityMessage message = new CityMessage(city.toString(), affordabilityIndex, secret);
      if (secret) {
        log.info("Sending SECRET message");
        template.convertAndSend(QUEUE_NAME_SECRET, message);
      } else if (tester.test(city)) {
        log.info("Sending CITIES1 message");
        template.convertAndSend(QUEUE_NAME_CITIES1, message);
      } else {
        log.info("Sending CITIES2 message");
        template.convertAndSend(QUEUE_NAME_CITIES2, message);
      }
    }
  }

  public static void main(String[] args) {
    SpringApplication.run(CitiesApplication.class, args);
  }

  @Bean
  public TopicExchange appExchange() {
    return new TopicExchange(EXCHANGE_NAME);
  }

  @Bean
  public Queue appQueueSecret() {
    return new Queue(QUEUE_NAME_SECRET);
  }

  @Bean
  public Binding declareBindingSecret() {
    return BindingBuilder.bind(appQueueSecret()).to(appExchange()).with(QUEUE_NAME_SECRET);
  }

  @Bean
  public Queue appQueueOne() {
    return new Queue(QUEUE_NAME_CITIES1);
  }

  @Bean
  public Binding declareBindingOne() {
    return BindingBuilder.bind(appQueueOne()).to(appExchange()).with(QUEUE_NAME_CITIES1);
  }

  @Bean
  public Queue appQueueTwo() {
    return new Queue(QUEUE_NAME_CITIES2);
  }

  @Bean
  public Binding declareBindingTwo() {
    return BindingBuilder.bind(appQueueTwo()).to(appExchange()).with(QUEUE_NAME_CITIES2);
  }
}
