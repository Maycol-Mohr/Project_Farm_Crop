package com.example.demo.staff.dtos;

import com.example.demo.staff.entity.Fertilizer;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Class FertilizerDTO.
 */
public class FertilizerDto implements Serializable {

  private Integer id;

  private String name;

  private String brand;

  private String composition;

  @JsonProperty("create_date")
  private LocalDate createDate;

  @JsonProperty("last_modified_date")
  private LocalDate lastModifiedDate;

  public FertilizerDto() {
  }

  /**
   * Constructor FertilizerDTO with id - name - brand - composition.
   */
  public FertilizerDto(Integer id, String name, String brand, String composition, LocalDate createDate, LocalDate lastModifiedDate) {
    this.id = id;
    this.name = name;
    this.brand = brand;
    this.composition = composition;
    this.createDate = createDate;
    this.lastModifiedDate = lastModifiedDate;
  }

  /**
   * Constructor FertilizerDTO with entity.
   */
  public FertilizerDto(Fertilizer entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.brand = entity.getBrand();
    this.composition = entity.getComposition();
    this.createDate = entity.getCreateDate();
    this.lastModifiedDate = entity.getLastModifiedDate();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getComposition() {
    return composition;
  }

  public void setComposition(String composition) {
    this.composition = composition;
  }

  public LocalDate getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDate createDate) {
    this.createDate = createDate;
  }

  public LocalDate getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(LocalDate lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }
}