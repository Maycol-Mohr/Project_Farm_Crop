package com.example.demo.staff.dtos;

import com.example.demo.staff.entity.Crop;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Class CropDTO.
 */
public class CropDto implements Serializable {

  private Integer id;

  private String name;

  @JsonProperty("planted_area")
  private Double plantedArea;

  @JsonProperty("farm_id")
  private Integer farmId;

  @JsonProperty("planted_date")
  private LocalDate plantedDate;

  @JsonProperty("harvest_date")
  private LocalDate harvestDate;

  public CropDto() {
  }

  /**
   * Constructor CropDTO with id - name - plantedArea.
   */
  public CropDto(Integer id, String name,
                 Double plantedArea, Integer farmId,
                 LocalDate plantedDate, LocalDate harvestDate) {
    this.id = id;
    this.name = name;
    this.plantedArea = plantedArea;
    this.farmId = farmId;
    this.plantedDate = plantedDate;
    this.harvestDate = harvestDate;
  }

  /**
   * Constructor CropDTO with entity.
   */
  public CropDto(Crop entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.plantedArea = entity.getPlantedArea();
    this.farmId = entity.getFarm().getId();
    this.plantedDate = entity.getPlantedDate();
    this.harvestDate = entity.getHarvestDate();
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

  public Double getPlantedArea() {
    return plantedArea;
  }

  public void setPlantedArea(Double plantedArea) {
    this.plantedArea = plantedArea;
  }

  public Integer getFarmId() {
    return farmId;
  }

  public void setFarmId(Integer farmId) {
    this.farmId = farmId;
  }

  public LocalDate getPlantedDate() {
    return plantedDate;
  }

  public void setPlantedDate(LocalDate plantedDate) {
    this.plantedDate = plantedDate;
  }

  public LocalDate getHarvestDate() {
    return harvestDate;
  }

  public void setHarvestDate(LocalDate harvestDate) {
    this.harvestDate = harvestDate;
  }
}