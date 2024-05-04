package com.example.demo.staff.controllers;

import com.example.demo.staff.dtos.PersonDto;
import com.example.demo.staff.entity.Person;
import com.example.demo.staff.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class PersonController.
 */
@RestController
@RequestMapping(value = "/persons")
public class PersonController {

  @Autowired
  private PersonService personService;

  /**
   * createPerson.
   */
  @PostMapping()
  public ResponseEntity<PersonDto> createPerson(@RequestBody Person personEntity) {
    personEntity = personService.create(personEntity);
    PersonDto personDto = new PersonDto(
            personEntity.getId(),
            personEntity.getUsername(),
            personEntity.getRole());
    return ResponseEntity.status(HttpStatus.CREATED).body(personDto);
  }

  /**
   * getPersonById.
   */
  @GetMapping(value = "/{id}")
  public ResponseEntity<PersonDto> findById(@PathVariable Long id) {
    Person personEntity = personService.getPersonById(id);
    PersonDto personDto = new PersonDto(
            personEntity.getId(),
            personEntity.getUsername(),
            personEntity.getRole());
    return ResponseEntity.ok().body(personDto);
  }

  /**
   * getPersonByUsername.
   */
  @GetMapping(value = "/username")
  public ResponseEntity<PersonDto> getPersonByUsername(@RequestParam String username) {
    Person personEntity = personService.getPersonByUsername(username);
    PersonDto personDto = new PersonDto(
            personEntity.getId(),
            personEntity.getUsername(),
            personEntity.getRole());
    return ResponseEntity.ok().body(personDto);
  }

  /**
   * getAllPersons.
   */
  @GetMapping
  public ResponseEntity<List<PersonDto>> getAllPersons() {
    List<PersonDto> list = personService.findAllPersons();
    return ResponseEntity.ok().body(list);
  }

  /**
   * getAllUsernamesJpql.
   */
  @GetMapping("/usernamejpql")
  public ResponseEntity<List<String>> getAllUsernamesJpql() {
    List<String> list = personService.findAllUsernamesJpql();
    return ResponseEntity.ok().body(list);
  }

  /**
   * getAllUsernamesJpqlPageable.
   */
  @GetMapping("/usernamejpqlpageable")
  public ResponseEntity<Page<String>> getAllUsernamesJpqlPageable(Pageable pageable) {
    Page<String> list = personService.findAllUsernamesJpqlPageable(pageable);
    return ResponseEntity.ok().body(list);
  }

  /**
   * getAllUsernamesRoleJpqlPageable.
   */
  @GetMapping("/usernamerolejpqlpageable")
  public ResponseEntity<Page<String>> getAllUsernamesRoleJpqlPageable(Pageable pageable) {
    Page<String> list = personService.findAllUsernamesRolesJpqlPageable(pageable);
    return ResponseEntity.ok().body(list);
  }
}