package com.example.demo.service;

import com.example.demo.staff.dtos.CropDto;
import com.example.demo.staff.entity.Crop;
import com.example.demo.staff.entity.Farm;
import com.example.demo.staff.entity.Fertilizer;
import com.example.demo.staff.repository.CropRepository;
import com.example.demo.staff.repository.FertilizerRepository;
import com.example.demo.staff.service.CropService;
import com.example.demo.staff.service.exception.CropNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
public class CropServiceTest {

  @Autowired
  CropService cropService;

  @MockBean
  CropRepository cropRepository;

  @MockBean
  FertilizerRepository fertilizerRepository;

  @Test
  public void testCropRetrievalFindById() {
    Farm farm = new Farm();
    farm.setName("Fazenda nome");
    farm.setSize(100.10);

    Crop crop = new Crop();
    crop.setName("Nome Plantacao");
    crop.setPlantedArea(100.10);
    crop.setFarm(farm);

    Mockito.when(cropRepository.findById(eq(1)))
            .thenReturn(Optional.of(crop));

    CropDto returnedCropDto = cropService.findById(1);

    Mockito.verify(cropRepository).findById(eq(1));

    assertEquals(returnedCropDto.getId(), crop.getId());
    assertEquals(returnedCropDto.getName(), crop.getName());
    assertEquals(returnedCropDto.getPlantedArea(), crop.getPlantedArea());
  }

  @Test
  public void testCropRetrievalByIdNotFound() {
    Mockito.when(cropRepository.findById(any()))
            .thenReturn(Optional.empty());

    assertThrows(CropNotFoundException.class, () -> cropService.findById(999));

    Mockito.verify(cropRepository).findById(eq(999));
  }

  @Test
  public void testCropGetAll() {
    Crop crop = new Crop();
    crop.setName("Nome Plantacao");
    crop.setPlantedArea(100.10);

    Crop crop2 = new Crop();
    crop2.setName("Nome Plantacao");
    crop2.setPlantedArea(100.10);

    List<Crop> list = new ArrayList<>();
    list.add(crop);
    list.add(crop2);

    Mockito.when(cropRepository.findAll()).thenReturn((list));

    //List<CropDto> list2 = cropService.findAll();

    Mockito.when(cropRepository.findAll()).thenReturn(list);
  }

  @Test
  public void testFindCropsBetweenDates() {
    CropDto crop = new CropDto();
    crop.setName("Nome Plantacao");
    crop.setPlantedArea(100.10);
    crop.setPlantedDate(LocalDate.ofEpochDay(2023-10-10));
    crop.setHarvestDate(LocalDate.ofEpochDay(2024-10-10));

    List<CropDto> listCrops = new ArrayList<>();
    listCrops.add(crop);

    LocalDate start = LocalDate.ofEpochDay(2022-01-01);
    LocalDate end = LocalDate.ofEpochDay(2025-01-01);

    Mockito.when(cropService.findCropsBetweenDates(start, end))
            .thenReturn(listCrops);
  }

  @Test
  public void testGetCropIdFertilizers() {

    Fertilizer fertilizer = new Fertilizer();
    fertilizer.setName("Nome Fertilizante");
    fertilizer.setBrand("Marca Fertilizante");
    fertilizer.setComposition("Composicao Fertilizante");

    List<Fertilizer> list = new ArrayList<>();
    list.add(fertilizer);

    Crop crop = new Crop();
    crop.setName("Nome Plantacao");
    crop.setPlantedArea(100.10);
    crop.setFertilizers(list);

    //Mockito.when(cropRepository.findById(eq(1)))
      //      .thenReturn(crop.getFertilizers(list));

    //assertEquals(list[0], fertilizer.getId());
    //assertEquals(list[1], fertilizer.getName());
    //assertEquals(list[2], fertilizer.getBrand());
    //assertEquals(list[3], fertilizer.getComposition());
  }

  @Test
  public void setCropAndFertilizerTest() {

    List<Fertilizer> fertilizerList = new ArrayList<>();

    Fertilizer fertilizer = new Fertilizer();
    fertilizer.setId(1);
    fertilizer.setName("Nome Fertilizante");
    fertilizer.setBrand("Marca Fertilizante");
    fertilizer.setComposition("Composicao Fertilizante");

    fertilizerList.add(fertilizer);

    Crop crop = new Crop();
    crop.setId(1);
    crop.setName("Nome Plantacao");
    crop.setPlantedArea(100.10);

    crop.setFertilizers(fertilizerList);

    Mockito.when(cropRepository.findById(eq(1))).thenReturn(Optional.of(crop));

    Mockito.when(fertilizerRepository.findById(eq(1))).thenReturn(Optional.of(fertilizer));

    crop.getFertilizers().add(fertilizer);

    Mockito.when(cropRepository.save(crop)).thenReturn(crop);

    assertEquals(crop.getId(), fertilizer.getId());
  }
}