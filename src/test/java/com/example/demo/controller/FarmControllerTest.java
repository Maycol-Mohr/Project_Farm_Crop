package com.example.demo.controller;

import com.example.demo.staff.dtos.CropDto;
import com.example.demo.staff.dtos.FarmDto;
import com.example.demo.staff.entity.Crop;
import com.example.demo.staff.service.FarmService;
import com.example.demo.staff.service.exception.FarmNotFoundException;
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

//@WebMvcTest(FarmController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FarmControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FarmService farmService;

  @Test
  public void createShouldReturnNewFarm() throws Exception {

    FarmDto farmDto = new FarmDto();
    farmDto.setName("Nome da Fazenda");
    farmDto.setSize(100.10);

    when(farmService.insertFarm(farmDto)).thenReturn(farmDto);

    ResultActions result = mockMvc.perform(post("/farms").accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isCreated());
  }

  @Test
  public void findAllShouldReturnList() throws Exception {

    FarmDto farmDto = new FarmDto();
    farmDto.setName("Nome da Fazenda");
    farmDto.setSize(100.10);

    List<FarmDto> list = new ArrayList<>();
    list.add(farmDto);

    when(farmService.findAll()).thenReturn(list);

    ResultActions result = mockMvc.perform(get("/farms").accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
  }

  @Test
  public void findAllShouldReturnFindById() throws Exception {

    FarmDto farmDto = new FarmDto();
    farmDto.setName("Teste do Nome");
    farmDto.setSize(100.10);

    when(farmService.findById(1)).thenReturn(farmDto);

    ResultActions result = mockMvc.perform(get("/farms/{id}", 1).accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());

    result.andExpect(jsonPath("$.id").exists());
    result.andExpect(jsonPath("$.name").exists());
    result.andExpect(jsonPath("$.size").exists());
  }

  @Test
  public void findAllShouldReturnNotFound() throws Exception {

    FarmDto farmDto = new FarmDto();
    farmDto.setName("Teste do Nome");
    farmDto.setSize(100.10);

    when(farmService.findById(999)).thenThrow(FarmNotFoundException.class);

    ResultActions result = mockMvc.perform(get("/farms/{id}", 999).accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound());
  }

  @Test
  public void createCropsInFarmShouldReturnCropsInFarm() throws Exception {

    FarmDto farmDto = new FarmDto();
    farmDto.setName("Nome da Fazenda");
    farmDto.setSize(100.10);

    Crop crop = new Crop();
    crop.setName("Teste de Plantacao");
    crop.setPlantedArea(100.10);
    //crop.setFarm(farmDto.setId(1));
    crop.setPlantedDate(LocalDate.ofEpochDay(2024-10-10));
    crop.setHarvestDate(LocalDate.ofEpochDay(2025-10-10));

    CropDto cropDto = new CropDto();

    when(farmService.insertCrop(farmDto.getId(), crop)).thenReturn(new Crop());

    ResultActions result = mockMvc.perform(post("/{farmId}/crops").accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isCreated());
  }

  @Test
  public void getAllCropsInFarmShouldReturnGetAllCropsInFarm() throws Exception {

    FarmDto farmDto = new FarmDto();
    farmDto.setName("Nome da Fazenda");
    farmDto.setSize(100.10);

    Crop crop = new Crop();
    crop.setName("Teste de Plantacao");
    crop.setPlantedArea(100.10);
    //crop.setFarm(farmDto.setId(1));
    crop.setPlantedDate(LocalDate.ofEpochDay(2024-10-10));
    crop.setHarvestDate(LocalDate.ofEpochDay(2025-10-10));

    List<CropDto> listCropDto = new ArrayList<>();

    //when(farmService.getFarmAndCropsById(farmDto.getId())).thenReturn(listCropDto);

    ResultActions result = mockMvc.perform(get("/{farmId}/crops").accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isCreated());
  }
}