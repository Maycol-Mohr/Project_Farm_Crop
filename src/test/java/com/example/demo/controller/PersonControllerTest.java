package com.example.demo.controller;

import com.example.demo.staff.dtos.PersonDto;
import com.example.demo.staff.entity.Person;
import com.example.demo.staff.security.Role;
import com.example.demo.staff.service.PersonService;
import com.example.demo.staff.service.exception.PersonNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
public class PersonControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PersonService personService;

  @Test
  public void createShouldReturnNewPerson() throws Exception {

    Person person = new Person();
    person.setUsername("Novo Username");
    person.setRole(Role.ADMIN);

    PersonDto personDto = new PersonDto();

    when(personService.create(person)).thenReturn(person);

    ResultActions result = mockMvc.perform(post("/persons").accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isCreated());
  }

  @Test
  public void findAllPersonShouldReturnFindById() throws Exception {

    Person person = new Person();
    person.setUsername("Novo Username");
    person.setRole(Role.ADMIN);

    when(personService.getPersonById(1L)).thenReturn(person);

    ResultActions result = mockMvc.perform(get("/persons/{id}", 1).accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());

    result.andExpect(jsonPath("$.id").exists());
    result.andExpect(jsonPath("$.username").exists());
    result.andExpect(jsonPath("$.role").exists());
  }

  @Test
  public void findPersonByIdShouldReturnNotFound() throws Exception {

    Person person = new Person();
    person.setUsername("Novo Username");
    person.setRole(Role.ADMIN);

    when(personService.getPersonById(999L)).thenThrow(PersonNotFoundException.class);

    ResultActions result = mockMvc.perform(get("/persons/{id}", 999L).accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound());
  }

  @Test
  public void getPersonByUsernameTeste() throws Exception {

    Person person = new Person();
    person.setUsername("Novo Username");
    person.setRole(Role.ADMIN);

    when(personService.getPersonByUsername(person.getUsername())).thenReturn(person);

    ResultActions result = mockMvc.perform(get("/persons/username", person.getUsername()).accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());

    result.andExpect(jsonPath("$.id").exists());
    result.andExpect(jsonPath("$.username").exists());
    result.andExpect(jsonPath("$.role").exists());
  }

  @Test
  public void getPersonByUsernameNotFoundTeste() throws Exception {

    Person person = new Person();
    person.setUsername("Novo Username");
    person.setRole(Role.ADMIN);

    when(personService.getPersonByUsername("Outro Nome")).thenThrow(PersonNotFoundException.class);

    ResultActions result = mockMvc.perform(get("/persons/username", "Outro Nome").accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound());
  }

  @Test
  public void findAllShouldReturnPersonList() throws Exception {

    PersonDto personDto = new PersonDto();
    personDto.setUsername("Novo Username");
    personDto.setRole(Role.ADMIN);

    List<PersonDto> list = new ArrayList<>();
    list.add(personDto);

    when(personService.findAllPersons()).thenReturn(list);

    ResultActions result = mockMvc.perform(get("/persons").accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
  }
}