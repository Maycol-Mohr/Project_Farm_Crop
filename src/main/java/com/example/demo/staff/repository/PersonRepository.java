package com.example.demo.staff.repository;

import com.example.demo.staff.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Person JPA repository.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

  Optional<Person> findByUsername(String username);

  @Query("SELECT p.username FROM Person p")
  public List<String> findAllUsernamesjpql();

  @Query("SELECT p.username FROM Person p")
  public Page<String> findAllUsernamesjpqlpageable(Pageable pageable);

  @Query("SELECT p.username, p.role FROM Person p")
  public Page<String> findAllUsernamesRolesjpqlpageable(Pageable pageable);
}