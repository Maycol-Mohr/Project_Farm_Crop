package com.example.demo.staff.dtos;

import com.example.demo.staff.entity.Farm;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Class FarmDTO.
 */
public class FarmDto implements Serializable {

  private Integer id;

  private String name;

  private Double size;

  @JsonProperty("created_by")
  private String createdBy;

  @JsonProperty("modified_by")
  private String modifiedBy;

  public FarmDto() {
  }

  /**
   * Constructor FarmDTO with id - name - size.
   */
  public FarmDto(Integer id, String name, Double size, String createdBy, String modifiedBy) {
    this.id = id;
    this.name = name;
    this.size = size;
    this.createdBy = createdBy;
    this.modifiedBy = modifiedBy;
  }

  /**
   * Constructor FarmDTO with entity.
   */
  public FarmDto(Farm entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.size = entity.getSize();
    this.createdBy = entity.getCreatedBy();
    this.modifiedBy = entity.getModifiedBy();
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

  public Double getSize() {
    return size;
  }

  public void setSize(Double size) {
    this.size = size;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }
}