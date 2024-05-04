package com.example.demo.service;

import com.example.demo.staff.dtos.FarmDto;
import com.example.demo.staff.entity.Crop;
import com.example.demo.staff.entity.Farm;
import com.example.demo.staff.repository.CropRepository;
import com.example.demo.staff.repository.FarmRepository;
import com.example.demo.staff.service.FarmService;
import com.example.demo.staff.service.exception.FarmNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
public class FarmServiceTest {

  @Autowired
  FarmService farmService;

  @MockBean
  FarmRepository farmRepository;

  @MockBean
  CropRepository cropRepository;

  @Test
  public void testFarmCreation() {
    Farm farm = new Farm();
    farm.setId(1);
    farm.setName("Teste Fazenda");
    farm.setSize(100.10);
    farm.setCreatedBy("novo teste");
    farm.setModifiedBy("novo teste");

    FarmDto farmToReturnDto = new FarmDto();
    farmToReturnDto.setId(1);
    farmToReturnDto.setName(farm.getName());
    farmToReturnDto.setSize(farm.getSize());
    farmToReturnDto.setCreatedBy(farm.getCreatedBy());
    farmToReturnDto.setModifiedBy(farm.getModifiedBy());

    Mockito.when(farmRepository.save(any())).thenReturn(farm);

    FarmDto savedFarmDto = farmService.insertFarm(new FarmDto(farm));

    Mockito.verify(farmRepository).save(any());

    assertEquals(1, savedFarmDto.getId());
    assertEquals(farm.getName(), savedFarmDto.getName());
    assertEquals(farm.getSize(), savedFarmDto.getSize());
    assertEquals(farm.getCreatedBy(), savedFarmDto.getCreatedBy());
    assertEquals(farm.getModifiedBy(), savedFarmDto.getModifiedBy());
  }

  @Test
  public void testFarmRetrievalFindById() {
    Farm farm = new Farm();
    farm.setName("Teste Fazenda");
    farm.setSize(100.10);

    FarmDto farmToReturnDto = new FarmDto();
    farmToReturnDto.setId(1);
    farmToReturnDto.setName(farm.getName());
    farmToReturnDto.setSize(farm.getSize());

    Mockito.when(farmRepository.findById(eq(1)))
            .thenReturn(Optional.of(farm));

    farmToReturnDto = farmService.findById(1);

    Mockito.verify(farmRepository).findById(eq(1));

    assertEquals(farmToReturnDto.getId(), farm.getId());
    assertEquals(farmToReturnDto.getName(), farm.getName());
    assertEquals(farmToReturnDto.getSize(), farm.getSize());
  }

  @Test
  public void testFarmRetrievalByIdNotFound() {
    Mockito.when(farmRepository.findById(any()))
            .thenReturn(Optional.empty());

    assertThrows(FarmNotFoundException.class, () -> farmService.findById(999));

    Mockito.verify(farmRepository).findById(eq(999));
  }

  @Test
  public void testFarmGetAll() {
    Farm farm = new Farm();
    farm.setName("Teste Fazenda");
    farm.setSize(100.10);

    Farm farm2 = new Farm();
    farm2.setName("Teste Fazenda 2");
    farm2.setSize(200.20);

    List<Farm> list = new ArrayList<>();
    list.add(farm);
    list.add(farm2);

    FarmDto farmDto = new FarmDto();

    Mockito.when(farmRepository.findAll()).thenReturn(list);

    Mockito.when(farmService.findAll()).thenReturn(List.of(farmDto));
  }

  @Test
  public void testInsertCropWichACorrectFarmId() {
    Farm farm = new Farm();
    farm.setId(1);
    farm.setName("Teste Fazenda");
    farm.setSize(100.10);

    Crop crop = new Crop();
    crop.setName("Nome Plantacao");
    crop.setPlantedArea(100.10);
    crop.setFarm(farm);

    crop.setFarm(farm);
    Crop createdCrop = cropRepository.save(crop);

    Mockito.when(farmRepository.findById(eq(farm.getId()))).thenReturn(Optional.of(farm));

    Mockito.when(cropRepository.save(createdCrop)).thenReturn(createdCrop);

    assertEquals(createdCrop.getId(), crop.getId());
    assertEquals(createdCrop.getName(), crop.getName());
    assertEquals(createdCrop.getPlantedArea(), crop.getPlantedArea());
    assertEquals(createdCrop.getFarm(), crop.getFarm());
  }

  @Test
  public void getFarmAndCropsByIdTest() {
    Farm farm = new Farm();
    farm.setId(1);
    farm.setName("Teste Fazenda");
    farm.setSize(100.10);

    Farm createdFarm = farmRepository.save(farm);

    Crop crop = new Crop();
    crop.setId(1);
    crop.setName("Nome Plantacao");
    crop.setPlantedArea(100.10);
    crop.setFarm(createdFarm);

    List<Crop> listCrop = new ArrayList<>();
    listCrop.add(crop);

    //crop.setFarm(farm);
    Crop createdCrop = cropRepository.save(crop);
    crop.setFarm(createdFarm);

    //Mockito.when(farmRepository.findById(eq(1))).thenReturn(Optional.of(farm.getCrops()));

    Mockito.when(farmRepository.save(any())).thenReturn(farm);

    Mockito.when(farmService.getFarmAndCropsById(any())).thenReturn(listCrop);

    //FarmDto savedFarmDto = farmService.insertFarm(new FarmDto(farm));

    //Mockito.verify(farmRepository).save(any());

    assertEquals(createdCrop.getId(), crop.getId());
    assertEquals(createdCrop.getName(), crop.getName());
    assertEquals(createdCrop.getPlantedArea(), crop.getPlantedArea());
    assertEquals(createdCrop.getFarm(), crop.getFarm());
  }
}