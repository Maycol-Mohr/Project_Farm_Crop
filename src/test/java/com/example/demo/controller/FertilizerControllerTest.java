package com.example.demo.controller;

import com.example.demo.staff.controllers.AuthController;
import com.example.demo.staff.dtos.AuthDto;
import com.example.demo.staff.dtos.FertilizerDto;
import com.example.demo.staff.entity.Person;
import com.example.demo.staff.security.Role;
import com.example.demo.staff.service.FertilizerService;
import com.example.demo.staff.service.TokenService;
import com.example.demo.staff.service.exception.FertilizerNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(FertilizerController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FertilizerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FertilizerService fertilizerService;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private AuthController authController;

  @Autowired
  private AuthDto authDto;

  @Autowired
  private WebApplicationContext context;

  @Test
  public void createShouldReturnNewFertilizer() throws Exception {

    FertilizerDto fertilizerDto = new FertilizerDto();
    fertilizerDto.setName("Nome do Fertilizante");
    fertilizerDto.setBrand("Marca do Fertilizante");
    fertilizerDto.setComposition("Composicao do Fertilizante");

    when(fertilizerService.insertFertilizer(fertilizerDto)).thenReturn(fertilizerDto);

    ResultActions result = mockMvc.perform(post("/fertilizers").accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isCreated());
  }

  @Test
  public void findAllShouldReturnList() throws Exception {

    FertilizerDto fertilizerDto = new FertilizerDto();
    fertilizerDto.setName("Nome do Fertilizante");
    fertilizerDto.setBrand("Marca do Fertilizante");
    fertilizerDto.setComposition("Composicao do Fertilizante");

    List<FertilizerDto> list = new ArrayList<>();
    list.add(fertilizerDto);

    when(fertilizerService.findAll()).thenReturn(list);

    ResultActions result = mockMvc.perform(get("/fertilizers").accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
  }

  @Test
  public void findAllShouldReturnFindById() throws Exception {

    FertilizerDto fertilizerDto = new FertilizerDto();
    fertilizerDto.setName("Nome do Fertilizante");
    fertilizerDto.setBrand("Marca do Fertilizante");
    fertilizerDto.setComposition("Composicao do Fertilizante");

    when(fertilizerService.findById(1)).thenReturn(fertilizerDto);

    ResultActions result = mockMvc.perform(get("/fertilizers/{id}", 1).accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());

    result.andExpect(jsonPath("$.id").exists());
    result.andExpect(jsonPath("$.name").exists());
    result.andExpect(jsonPath("$.brand").exists());
    result.andExpect(jsonPath("$.composition").exists());
  }

  @Test
  public void findAllShouldReturnNotFound() throws Exception {

    FertilizerDto fertilizerDto = new FertilizerDto();
    fertilizerDto.setName("Nome do Fertilizante");
    fertilizerDto.setBrand("Marca do Fertilizante");
    fertilizerDto.setComposition("Composicao do Fertilizante");

    when(fertilizerService.findById(999)).thenThrow(FertilizerNotFoundException.class);

    ResultActions result = mockMvc.perform(get("/fertilizers/{id}", 999).accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound());
  }

  @Test
  public void updateShouldReturnUpdatedFertilizer() throws Exception {

    FertilizerDto fertilizerDto = new FertilizerDto();
    fertilizerDto.setId(1);
    fertilizerDto.setName("Nome do Fertilizante");
    fertilizerDto.setBrand("Marca do Fertilizante");
    fertilizerDto.setComposition("Composicao do Fertilizante");

    when(fertilizerService.updateFertilizer(1, fertilizerDto)).thenReturn(fertilizerDto);

    ResultActions result = mockMvc.perform(put("/fertilizers/{id}").accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
  }

  @Test
  public void deleteShouldReturnNoContent() throws Exception {

    Person person = new Person();
    person.setUsername("Novo Username");
    person.setPassword("12345");
    person.setRole(Role.ADMIN);

    authController.login(authDto);

   // MockMvc mockMvc = MockMvcBuilders
     //       .webAppContextSetup(context)
       //     .alwaysDo(print())
         //   .apply(SecurityMockMvcConfigurers.springSecurity())
           // .build();

    //FertilizerDto fertilizerDto = new FertilizerDto();
    //fertilizerDto.setId(1);
    //fertilizerDto.setName("Nome do Fertilizante");
    //fertilizerDto.setBrand("Marca do Fertilizante");
    //fertilizerDto.setComposition("Composicao do Fertilizante");

    //String username = "teste";

    String accessToken = tokenService.generateToken(authDto.username());

    ResultActions result =
            mockMvc.perform(delete("/fertilizers/{id}", 1)
                    .header("Authorization", "Bearer " + accessToken)
                    .accept(MediaType.APPLICATION_JSON));

    //ResultActions result =
      //      mockMvc.perform(delete("/fertilizers/{id}", 1).with(csrf().asHeader()));

    result.andExpect(status().isNoContent());
  }
}