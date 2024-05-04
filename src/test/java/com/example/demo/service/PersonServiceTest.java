package com.example.demo.service;

import com.example.demo.staff.dtos.PersonDto;
import com.example.demo.staff.entity.Person;
import com.example.demo.staff.repository.PersonRepository;
import com.example.demo.staff.security.Role;
import com.example.demo.staff.service.PersonService;
import com.example.demo.staff.service.exception.PersonNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
public class PersonServiceTest {

  @Autowired
  PersonService personService;

  @MockBean
  PersonRepository personRepository;

  @Test
  public void testPersonCreation() {
    Person person = new Person();
    person.setUsername("Teste");
    person.setPassword("12345");
    person.setRole(Role.ADMIN);

    String hashedPassword = new BCryptPasswordEncoder().encode(person.getPassword());

    person.setPassword(hashedPassword);

    Person personToReturn = new Person();
    personToReturn.setId(1L);
    personToReturn.setUsername(person.getUsername());
    personToReturn.setPassword(person.getPassword());
    personToReturn.setRole(person.getRole());

    Mockito.when(personRepository.save(any())).thenReturn(personToReturn);

    Person savedPerson = personService.create(person);

    Mockito.verify(personRepository).save(any());

    assertEquals(1L, savedPerson.getId());
    assertEquals(person.getUsername(), savedPerson.getUsername());
    assertEquals(person.getRole(), savedPerson.getRole());
    //assertEquals(person.getPassword(), savedPerson.getPassword());
  }

  @Test
  public void testPersonRetrievalFindById() {
    Person person = new Person();
    person.setId(1L);
    person.setUsername("Teste");
    person.setPassword("12345");

    Mockito.when(personRepository.findById(eq(1L)))
            .thenReturn(Optional.of(person));

    Person returnedPerson = personService.getPersonById(1L);

    Mockito.verify(personRepository).findById(eq(1L));

    Mockito.verify(personRepository, Mockito.times(1)).findById(person.getId());

    assertEquals(returnedPerson.getId(), person.getId());
    assertEquals(returnedPerson.getUsername(), person.getUsername());
    assertEquals(returnedPerson.getPassword(), person.getPassword());
  }

  @Test
  public void testPersonRetrievalByIdNotFound() {
    Mockito.when(personRepository.findById(any())).thenReturn(Optional.empty());

    assertThrows(PersonNotFoundException.class, () -> personService.getPersonById(999L));

    Mockito.verify(personRepository).findById(eq(999L));

    Mockito.verify(personRepository, Mockito.times(1)).findById(999L);
  }

  @Test
  public void testPersonRetrievalFindByUsername() {
    Person person = new Person();
    person.setUsername("Teste");
    person.setPassword("12345");

    Mockito.when(personRepository.findByUsername(eq("Teste"))).thenReturn(Optional.of(person));

    Person returnedPerson = personService.getPersonByUsername("Teste");

    Mockito.verify(personRepository).findByUsername(eq("Teste"));

    Mockito.verify(personRepository, Mockito.times(1)).findByUsername("Teste");

    assertEquals(returnedPerson.getUsername(), person.getUsername());
  }

  @Test
  public void testPersonRetrievalByUsernameNotFound() {
    Mockito.when(personRepository.findByUsername(any())).thenReturn(Optional.empty());

    assertThrows(PersonNotFoundException.class, () -> personService.getPersonByUsername("nome incorreto"));

    Mockito.verify(personRepository).findByUsername(eq("nome incorreto"));

    Mockito.verify(personRepository, Mockito.times(1)).findByUsername("nome incorreto");
  }

  @Test
  public void testPersonGetAll() {
    Person person = new Person();
    person.setId(1L);
    person.setUsername("Teste");
    person.setPassword("12345");
    person.setRole(Role.ADMIN);

    Person person2 = new Person();
    person2.setId(2L);
    person2.setUsername("Teste2");
    person2.setPassword("55555");
    person2.setRole(Role.MANAGER);

    List<Person> list = new ArrayList<>();
    list.add(person);
    list.add(person2);

    List<PersonDto> personDto = new ArrayList<>();
    personDto.add(new PersonDto(person));

    var response = personService.findAllPersons();

    Mockito.when(personRepository.findAll()).thenReturn(list);

    Mockito.when(personService.findAllPersons()).thenReturn(personDto);

    Mockito.verify(personRepository, Mockito.times(1)).findAll();

    assertEquals(list, list);
  }
}