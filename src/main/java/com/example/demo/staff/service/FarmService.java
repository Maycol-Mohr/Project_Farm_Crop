package com.example.demo.staff.service;

import com.example.demo.staff.dtos.FarmDto;
import com.example.demo.staff.entity.Crop;
import com.example.demo.staff.entity.Farm;
import com.example.demo.staff.repository.CropRepository;
import com.example.demo.staff.repository.FarmRepository;
import com.example.demo.staff.service.exception.FarmNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class FarmService.
 */
@Service
public class FarmService {

  @Autowired
  private FarmRepository farmRepository;

  @Autowired
  private CropRepository cropRepository;

  /**
   * insertFarm.
   */
  @Transactional
  public FarmDto insertFarm(FarmDto dto) {
    Farm entity = new Farm();
    entity.setName(dto.getName());
    entity.setSize(dto.getSize());
    entity.setCreatedBy(dto.getCreatedBy());
    entity.setModifiedBy(dto.getModifiedBy());
    entity = farmRepository.save(entity);
    return new FarmDto(entity);
  }

  /**
   * getAllFarms.
   */
  @Transactional(readOnly = true)
  public List<FarmDto> findAll() {
    List<Farm> list = farmRepository.findAll();
    return list.stream().map(farm -> new FarmDto(farm)).collect(Collectors.toList());
  }

  /**
   * getFarmsById.
   */
  @Transactional(readOnly = true)
  public FarmDto findById(Integer id) {
    Optional<Farm> obj = farmRepository.findById(id);
    Farm entity = obj.orElseThrow(() -> new FarmNotFoundException(
            "Nao foi encontrada a Fazenda com este Id"));
    return new FarmDto(entity);
  }

  /**
   * getFarmAndCropsById.
   */
  @Transactional
  public Crop insertCrop(Integer farmId, Crop crop) {
    Optional<Farm> optionalFarm = farmRepository.findById(farmId);

    if (optionalFarm.isPresent()) {
      Farm farm = optionalFarm.get();
      crop.setFarm(farm);
      Crop createdCrop = cropRepository.save(crop);
      return createdCrop;
    } else {
      throw new FarmNotFoundException("Nao foi encontrada a Fazenda com este Id");
    }
  }

  /**
   * getFarmAndCropsById.
   */
  @Transactional(readOnly = true)
  public List<Crop> getFarmAndCropsById(Integer farmId) {

    Optional<Farm> optionalFarm = farmRepository.findById(farmId);

    if (optionalFarm.isPresent()) {
      return optionalFarm.get().getCrops();
    } else {
      throw new FarmNotFoundException("Nao foi encontrada a Fazenda com este Id");
    }
  }

  @Transactional(readOnly = true)
  public List<String> findAllFarmsAndCropsJpql() {
    return farmRepository.findAllFarmsAndCropsjpql();
  }
}