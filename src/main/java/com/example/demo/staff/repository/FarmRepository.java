package com.example.demo.staff.repository;

import com.example.demo.staff.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class FarmRepository.
 */
@Repository
public interface FarmRepository extends JpaRepository<Farm, Integer> {

  //@Query("SELECT f.name, c.name FROM Farm f INNER JOIN Crop c ON c.id = f.id")
  //public List<String> findAllFarmsAndCropsjpql();

  @Query("SELECT f.name, f.size FROM Farm f")
  public List<String> findAllFarmsAndCropsjpql();
}