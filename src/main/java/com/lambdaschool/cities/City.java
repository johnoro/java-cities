package com.lambdaschool.cities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class City {
  private @Id @GeneratedValue Long id;
  private int medianHomePrice;
  private int affordabilityIndex;

  public City() {}

  public City(int medianHomePrice, int affordabilityIndex) {
    this.medianHomePrice = medianHomePrice;
    this.affordabilityIndex = affordabilityIndex;
  }
}
