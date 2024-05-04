package com.example.demo.controller;

import com.example.demo.staff.dtos.CropDto;
import com.example.demo.staff.entity.Crop;
import com.example.demo.staff.entity.Fertilizer;
import com.example.demo.staff.service.CropService;
import com.example.demo.staff.service.exception.CropNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(CropController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CropControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CropService cropService;

  @Test
  public void findAllShouldReturnList() throws Exception {

    CropDto cropDto = new CropDto();
    cropDto.setName("Teste de Plantacao");
    cropDto.setPlantedArea(100.10);
    cropDto.setFarmId(1);
    cropDto.setPlantedDate(LocalDate.ofEpochDay(2024-10-10));
    cropDto.setHarvestDate(LocalDate.ofEpochDay(2025-10-10));

    List<CropDto> list = new ArrayList<>();
    list.add(cropDto);

    when(cropService.findAll()).thenReturn(list);

    ResultActions result = mockMvc.perform(get("/crops").accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
  }

  @Test
  public void findAllShouldReturnFindById() throws Exception {

    CropDto cropDto = new CropDto();
    cropDto.setName("Teste de Plantacao");
    cropDto.setPlantedArea(100.10);
    cropDto.setFarmId(1);
    cropDto.setPlantedDate(LocalDate.ofEpochDay(2024-10-10));
    cropDto.setHarvestDate(LocalDate.ofEpochDay(2025-10-10));

    when(cropService.findById(1)).thenReturn(cropDto);

    ResultActions result = mockMvc.perform(get("/crops/{id}", 1).accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());

    result.andExpect(jsonPath("$.id").exists());
    result.andExpect(jsonPath("$.name").exists());
    result.andExpect(jsonPath("$.plantedArea").exists());
    result.andExpect(jsonPath("$.farmId").exists());
    result.andExpect(jsonPath("$.plantedDate").exists());
    result.andExpect(jsonPath("$.harvestDate").exists());
  }

  @Test
  public void findAllShouldReturnNotFound() throws Exception {

    CropDto cropDto = new CropDto();
    cropDto.setName("Teste de Plantacao");
    cropDto.setPlantedArea(100.10);
    cropDto.setFarmId(1);
    cropDto.setPlantedDate(LocalDate.ofEpochDay(2024-10-10));
    cropDto.setHarvestDate(LocalDate.ofEpochDay(2025-10-10));

    when(cropService.findById(999)).thenThrow(CropNotFoundException.class);

    ResultActions result = mockMvc.perform(get("/crops/{id}", 999).accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound());
  }

  @Test
  public void findByBetweenDatesCropsTest() throws Exception {

    CropDto cropDto = new CropDto();
    cropDto.setName("Teste de Plantacao");
    cropDto.setPlantedArea(100.10);
    cropDto.setFarmId(1);
    cropDto.setPlantedDate(LocalDate.ofEpochDay(2024-10-10));
    cropDto.setHarvestDate(LocalDate.ofEpochDay(2025-10-10));

    LocalDate start = LocalDate.ofEpochDay(2000-01-01);
    LocalDate end = LocalDate.ofEpochDay(2030-01-01);

    List<CropDto> list = new ArrayList<>();
    list.add(cropDto);

    when(cropService.findCropsBetweenDates(start, end)).thenReturn(list);

    ResultActions result = mockMvc.perform(get("/crops/search").accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
  }

  @Test
  public void getCropIdFertilizersTest() throws Exception {

    Fertilizer fertilizer = new Fertilizer();
    fertilizer.setName("Nome Fertilizante");
    fertilizer.setBrand("Marca Fertilizante");
    fertilizer.setComposition("Composicao Fertilizante");

    List<Fertilizer> listFertilizer = new ArrayList<>();
    listFertilizer.add(fertilizer);

    Crop crop = new Crop();
    crop.setName("Teste de Plantacao");
    crop.setPlantedArea(100.10);
    crop.setFertilizers(listFertilizer);

    //when(cropService.getCropIdFertilizers(1)).thenReturn(new FertilizerDto(listFertilizer));

    ResultActions result = mockMvc.perform(get("/{cropId}/fertilizers", 1).accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());

    result.andExpect(jsonPath("$.id").exists());
    result.andExpect(jsonPath("$.name").exists());
    result.andExpect(jsonPath("$.brand").exists());
    result.andExpect(jsonPath("$.composition").exists());
  }

  @Test
  public void setCropsAndFertilizersTest() throws Exception {

    Fertilizer fertilizer = new Fertilizer();
    fertilizer.setName("Nome Fertilizante");
    fertilizer.setBrand("Marca Fertilizante");
    fertilizer.setComposition("Composicao Fertilizante");

    Crop crop = new Crop();
    crop.setName("Nome Plantacao");
    crop.setPlantedArea(100.10);

    when(cropService.setCropAndFertilizer(crop.getId(), fertilizer.getId()))
            .thenReturn(crop);

    ResultActions result = mockMvc.perform(post("/crops/{cropId}/fertilizers/{fertilizerId}", 1, 1).accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isCreated());

    result.andExpect(jsonPath("$.cropId").exists());
    result.andExpect(jsonPath("$.fertilizerId").exists());
  }
}