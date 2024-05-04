package com.example.demo.staff.controllers;

import com.example.demo.staff.dtos.CropDto;
import com.example.demo.staff.dtos.FertilizerDto;
import com.example.demo.staff.entity.Fertilizer;
import com.example.demo.staff.service.CropService;
import com.example.demo.staff.service.FertilizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class CropController.
 */
@RestController
@RequestMapping(value = "/crops")
public class CropController {

  @Autowired
  private CropService cropService;

  @Autowired
  private FertilizerService fertilizerService;

  /**
   * getAllCrops.teste
   */
  @GetMapping
  @Secured({"ADMIN", "MANAGER"})
  public ResponseEntity<List<CropDto>> findAll() {
    List<CropDto> list = cropService.findAll();
    return ResponseEntity.ok().body(list);
  }

  /**
   * getCropsById.
   */
  @GetMapping(value = "/{id}")
  public ResponseEntity<CropDto> findById(@PathVariable Integer id) {
    CropDto dto = cropService.findById(id);
    return ResponseEntity.ok().body(dto);
  }

  /**
   * getCropsById.
   */
  @GetMapping("/search")
  public List<CropDto> findByBetweenDatesCrops(@RequestParam LocalDate start,
                                               @RequestParam LocalDate end) {
    return cropService.findCropsBetweenDates(start, end);
  }

  /**
   * postCropIdAndFertilizerId.
   */
  @PostMapping("/{cropId}/fertilizers/{fertilizerId}")
  public ResponseEntity<String> setCropsAndFertilizers(@PathVariable Integer cropId,
                                                       @PathVariable Integer fertilizerId) {

    cropService.setCropAndFertilizer(cropId, fertilizerId);

    return ResponseEntity.status(HttpStatus.CREATED)
            .body("Fertilizante e plantação associados com sucesso!");
  }

  /**
   * getCropIdFertilizers.
   */
  @GetMapping("/{cropId}/fertilizers")
  public ResponseEntity<List<FertilizerDto>> getCropIdFertilizers(@PathVariable Integer cropId) {

    List<Fertilizer> list = cropService.getCropIdFertilizers(cropId);

    List<FertilizerDto> listDto = list.stream().map(fertilizer -> new FertilizerDto(
                    fertilizer.getId(),
                    fertilizer.getName(),
                    fertilizer.getBrand(),
                    fertilizer.getComposition(),
                    fertilizer.getCreateDate(),
                    fertilizer.getLastModifiedDate()))
            .collect(Collectors.toList());

    return ResponseEntity.ok().body(listDto);
  }
}