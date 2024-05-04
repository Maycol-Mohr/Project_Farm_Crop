package com.example.demo.staff.controllers;

import com.example.demo.staff.dtos.FertilizerDto;
import com.example.demo.staff.service.FertilizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class FertilizerController.
 */
@RestController
@RequestMapping(value = "/fertilizers")
public class FertilizerController {

  @Autowired
  private FertilizerService fertilizerService;

  /**
   * createFertilizer.
   */
  @PostMapping()
  public ResponseEntity<FertilizerDto> createFertilizer(@RequestBody FertilizerDto dto) {
    dto = fertilizerService.insertFertilizer(dto);
    FertilizerDto fertilizerDto = new FertilizerDto(dto.getId(), dto.getName(),
            dto.getBrand(), dto.getComposition(), dto.getCreateDate(), dto.getLastModifiedDate());
    return ResponseEntity.status(HttpStatus.CREATED).body(fertilizerDto);
  }

  /**
   * getAllFertilizers.
   */
  @GetMapping
  @Secured("ADMIN")
  public ResponseEntity<List<FertilizerDto>> findAll() {
    List<FertilizerDto> list = fertilizerService.findAll();
    return ResponseEntity.ok().body(list);
  }

  /**
   * getFertilizerById.
   */
  @GetMapping(value = "/{id}")
  public ResponseEntity<FertilizerDto> findById(@PathVariable Integer id) {
    FertilizerDto dto = fertilizerService.findById(id);
    return ResponseEntity.ok().body(dto);
  }

  /**
   * updateFertilizerById.
   */
  @PutMapping(value = "/{id}")
  public ResponseEntity<FertilizerDto> update(@PathVariable Integer id, @RequestBody FertilizerDto dto) {
    dto = fertilizerService.updateFertilizer(id, dto);
    return ResponseEntity.ok().body(dto);
  }

  /**
   * deleteFertilizerById.
   */
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    fertilizerService.deleteFertilizer(id);
    return ResponseEntity.noContent().build();
  }
}