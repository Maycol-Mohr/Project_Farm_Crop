package com.example.demo.staff.service;

import com.example.demo.staff.dtos.CropDto;
import com.example.demo.staff.entity.Crop;
import com.example.demo.staff.entity.Fertilizer;
import com.example.demo.staff.repository.CropRepository;
import com.example.demo.staff.repository.FertilizerRepository;
import com.example.demo.staff.service.exception.CropNotFoundException;
import com.example.demo.staff.service.exception.FertilizerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class CropService.
 */
@Service
public class CropService {

  @Autowired
  private CropRepository cropRepository;

  @Autowired
  private FertilizerRepository fertilizerRepository;

  /**
   * getAllCrops.
   */
  @Transactional(readOnly = true)
  public List<CropDto> findAll() {
    List<Crop> list = cropRepository.findAll();
    return list.stream().map(crop -> new CropDto(crop)).collect(Collectors.toList());
  }

  /**
   * getFarmsById.
   */
  @Transactional(readOnly = true)
  public CropDto findById(Integer id) {
    Optional<Crop> obj = cropRepository.findById(id);
    Crop entity = obj.orElseThrow(() -> new CropNotFoundException(
            "Nao foi encontrada a Plantação com este Id"));
    return new CropDto(entity);
  }

  /**
   * getCropsByDate.
   */
  @Transactional(readOnly = true)
  public List<CropDto> findCropsBetweenDates(LocalDate start, LocalDate end) {
    List<CropDto> crops = findAll();
    return crops.stream().filter(
            crop -> crop.getHarvestDate().isBefore(end) && crop.getHarvestDate()
                    .isAfter(start)).toList();
  }

  /**
   * setCropAndFertilizer.
   */
  @Transactional
  public Crop setCropAndFertilizer(Integer cropId, Integer fertilizerId) {

    Crop crop = cropRepository.findById(cropId).orElseThrow(
            () -> new CropNotFoundException(
                    "Nao foi encontrada a Plantação com este Id")
    );

    Fertilizer fertilizer = fertilizerRepository.findById(fertilizerId).orElseThrow(
            () -> new FertilizerNotFoundException(
                    "Nao foi encontrado o Fertilizante com este Id")
    );

    crop.getFertilizers().add(fertilizer);
    Crop newCrop = cropRepository.save(crop);

    return newCrop;
  }

  /**
   * getCropIdFertilizers.
   */
  @Transactional(readOnly = true)
  public List<Fertilizer> getCropIdFertilizers(Integer cropId) {

    Crop crop = cropRepository.findById(cropId).orElseThrow(
            () -> new CropNotFoundException(
                    "Nao foi encontrada a Plantação com este Id")
    );

    return crop.getFertilizers();
  }
}