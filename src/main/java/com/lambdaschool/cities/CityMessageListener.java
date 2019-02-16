package com.lambdaschool.cities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CityMessageListener {
  @RabbitListener(queues = {
    CitiesApplication.QUEUE_NAME_SECRET,
    CitiesApplication.QUEUE_NAME_CITIES1,
    CitiesApplication.QUEUE_NAME_CITIES2
  })
  public void receiveMessage(CityMessage message) {
    log.info("Received message: {}", message.toString());
  }
}
