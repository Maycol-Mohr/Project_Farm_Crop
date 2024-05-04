package com.example.demo.staff.dtos;

import com.example.demo.staff.entity.Person;
import com.example.demo.staff.security.Role;

import java.io.Serializable;

/**
 * Class PersonDTO.
 */
public class PersonDto implements Serializable {

  private Long id;

  private String username;

  private Role role;

  public PersonDto() {
  }

  /**
   * Constructor PersonDTO with id - username - role.
   */
  public PersonDto(Long id, String username, Role role) {
    this.id = id;
    this.username = username;
    this.role = role;
  }

  /**
   * Constructor PersonDTO with entity.
   */
  public PersonDto(Person entity) {
    this.id = entity.getId();
    this.username = entity.getUsername();
    this.role = entity.getRole();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}