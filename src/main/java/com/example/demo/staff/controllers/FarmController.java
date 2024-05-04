package com.example.demo.staff.controllers;

import com.example.demo.staff.dtos.CropDto;
import com.example.demo.staff.dtos.FarmDto;
import com.example.demo.staff.entity.Crop;
import com.example.demo.staff.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class FormController.
 */
@RestController
@RequestMapping(value = "/farms")
public class FarmController {

  @Autowired
  private FarmService farmService;

  /**
   * createFarm.
   */
  @PostMapping()
  public ResponseEntity<FarmDto> createFarm(@RequestBody FarmDto dto) {
    dto = farmService.insertFarm(dto);
    FarmDto farmDto = new FarmDto(dto.getId(), dto.getName(), dto.getSize(), dto.getCreatedBy(), dto.getModifiedBy());
    return ResponseEntity.status(HttpStatus.CREATED).body(farmDto);
  }

  /**
   * createCrop.
   */
  @PostMapping("/{farmId}/crops")
  public ResponseEntity<CropDto> createCropsInFarm(@PathVariable Integer farmId,
                                                   @RequestBody Crop newCrop) {
    Crop cropEntity = farmService.insertCrop(farmId, newCrop);
    CropDto cropDto = new CropDto(cropEntity.getId(), cropEntity.getName(),
            cropEntity.getPlantedArea(), cropEntity.getFarm().getId(),
            cropEntity.getPlantedDate(), cropEntity.getHarvestDate());
    return ResponseEntity.status(HttpStatus.CREATED).body(cropDto);
  }

  /**
   * getAllFarms.
   */
  @GetMapping
  @Secured({"ADMIN", "MANAGER", "USER"})
  public ResponseEntity<List<FarmDto>> findAll() {
    List<FarmDto> list = farmService.findAll();
    return ResponseEntity.ok().body(list);
  }

  /**
   * getFarmAndCropsById.
   */
  @GetMapping("/{farmId}/crops")
  public ResponseEntity<List<CropDto>> getFarmAndCropsById(@PathVariable Integer farmId) {
    List<Crop> getAllCrops = farmService.getFarmAndCropsById(farmId);
    List<CropDto> cropsDtoInFarm = getAllCrops.stream().map((crop) -> new CropDto(
                    crop.getId(),
                    crop.getName(),
                    crop.getPlantedArea(),
                    crop.getFarm().getId(),
                    crop.getPlantedDate(),
                    crop.getHarvestDate()))
            .collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.OK).body(cropsDtoInFarm);
  }

  /**
   * getFarmsById.
   */
  @GetMapping(value = "/{id}")
  public ResponseEntity<FarmDto> findById(@PathVariable Integer id) {
    FarmDto dto = farmService.findById(id);
    return ResponseEntity.ok().body(dto);
  }

  /**
   * getAllFarmsAndCropsJpql.
   */
  @GetMapping("/farmscropsjpql")
  public ResponseEntity<List<String>> getAllFarmsAndCropsJpql() {
    List<String> list = farmService.findAllFarmsAndCropsJpql();
    return ResponseEntity.ok().body(list);
  }
}