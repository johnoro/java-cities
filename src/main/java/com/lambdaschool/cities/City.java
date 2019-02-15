package com.lambdaschool.cities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class City {
  private @Id @GeneratedValue Long id;
  private String name;
  private int medianHomePrice;
  private int affordabilityIndex;

  public City() {}

  public City(String name, int price, int index) {
    this.name = name;
    medianHomePrice = price;
    affordabilityIndex = index;
  }
}
