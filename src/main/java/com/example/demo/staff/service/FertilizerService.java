package com.example.demo.staff.service;

import com.example.demo.staff.dtos.FertilizerDto;
import com.example.demo.staff.entity.Fertilizer;
import com.example.demo.staff.repository.FertilizerRepository;
import com.example.demo.staff.service.exception.FertilizerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class FertilizerService.
 */
@Service
public class FertilizerService {

  @Autowired
  private FertilizerRepository fertilizerRepository;

  /**
   * insertFertilizer.
   */
  @Transactional
  public FertilizerDto insertFertilizer(FertilizerDto dto) {
    Fertilizer entity = new Fertilizer();
    entity.setName(dto.getName());
    entity.setBrand(dto.getBrand());
    entity.setComposition(dto.getComposition());
    entity = fertilizerRepository.save(entity);
    return new FertilizerDto(entity);
  }

  /**
   * getAllFertilizers.
   */
  @Transactional(readOnly = true)
  public List<FertilizerDto> findAll() {
    List<Fertilizer> list = fertilizerRepository.findAll();
    return list.stream().map(fertilizer -> new FertilizerDto(fertilizer))
            .collect(Collectors.toList());
  }

  /**
   * getFertilizerById.
   */
  @Transactional(readOnly = true)
  public FertilizerDto findById(Integer id) {
    Optional<Fertilizer> obj = fertilizerRepository.findById(id);
    Fertilizer entity = obj.orElseThrow(() -> new FertilizerNotFoundException(
            "Nao foi encontrada o Fertilizante com este Id"));
    return new FertilizerDto(entity);
  }

  @Transactional
  public FertilizerDto updateFertilizer(Integer id, FertilizerDto dto) {
    try {
      Fertilizer entity = fertilizerRepository.getById(id);
      copyDtoToEntity(dto, entity);
      fertilizerRepository.save(entity);
      return new FertilizerDto(entity);
    } catch (FertilizerNotFoundException e) {
      throw new FertilizerNotFoundException("Id not found " + id);
    }
  }

  @Transactional
  public void deleteFertilizer(Integer id) {
    try {
      fertilizerRepository.deleteById(id);
    } catch (FertilizerNotFoundException e) {
      throw new FertilizerNotFoundException("Id not found " + id);
    }
  }

  private void copyDtoToEntity(FertilizerDto dto, Fertilizer entity) {
    entity.setName(dto.getName());
    entity.setBrand(dto.getBrand());
    entity.setComposition(dto.getComposition());
  }
}