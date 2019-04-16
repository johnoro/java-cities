package com.lambdaschool.cities;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cities")
public class CityController {
  private final CityRepository repository;
  private final RabbitTemplate template;

  private void sendMessages(CheckCity tester) {
    CitiesApplication.sendMessages(
      repository,
      template,
      tester
    );
  }

  public CityController(CityRepository repository, RabbitTemplate template) {
    this.repository = repository;
    this.template = template;
  }

  @GetMapping("/afford")
  public void afford() {
    sendMessages(
      city -> city.getAffordabilityIndex() < 6
    );
  }

  @GetMapping("/homes")
  public void homes() {
    sendMessages(
      city -> city.getMedianHomePrice() > 200000
    );
  }

  @GetMapping("/names")
  public void names() {
    sendMessages(
      city -> true
    );
  }
}
