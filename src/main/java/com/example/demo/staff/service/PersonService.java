package com.example.demo.staff.service;

import com.example.demo.staff.dtos.PersonDto;
import com.example.demo.staff.entity.Person;
import com.example.demo.staff.repository.PersonRepository;
import com.example.demo.staff.service.exception.PersonExistsException;
import com.example.demo.staff.service.exception.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service layer class for handling persons business logic.
 */
@Service
public class PersonService implements UserDetailsService {

  private final PersonRepository personRepository;

  @Autowired
  public PersonService(
          PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  /**
   * Returns a person for a given ID.
   */

  public Person getPersonById(Long id) {
    Optional<Person> person = personRepository.findById(id);

    if (person.isEmpty()) {
      throw new PersonNotFoundException();
    }

    return person.get();
  }

  /**
   * Returns a person for a given username.
   */
  public Person getPersonByUsername(String username) {
    Optional<Person> person = personRepository.findByUsername(username);

    if (person.isEmpty()) {
      throw new PersonNotFoundException();
    }

    return person.get();
  }

  /**
   * insertPerson.
   */
  @Transactional
  public Person create(Person person) {

    Optional<Person> personIsPresent = personRepository.findByUsername(person.getUsername());

    if(personIsPresent.isPresent()) {
      throw new PersonExistsException("Username " + person.getUsername() + " já cadastrado na base de dados! Cadastre outro!");
    }

    String hashedPassword = new BCryptPasswordEncoder()
            .encode(person.getPassword());

    person.setPassword(hashedPassword);

    return personRepository.save(person);
  }

  /**
   * getAllPersons.
   */
  @Transactional(readOnly = true)
  public List<PersonDto> findAllPersons() {
    List<Person> list = personRepository.findAll();
    return list.stream().map(person -> new PersonDto(person)).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<String> findAllUsernamesJpql() {
    return personRepository.findAllUsernamesjpql();
  }

  @Transactional(readOnly = true)
  public Page<String> findAllUsernamesJpqlPageable(Pageable pageable) {
    return personRepository.findAllUsernamesjpqlpageable(pageable);
  }

  @Transactional(readOnly = true)
  public Page<String> findAllUsernamesRolesJpqlPageable(Pageable pageable) {
    return personRepository.findAllUsernamesRolesjpqlpageable(pageable);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return personRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
  }
}