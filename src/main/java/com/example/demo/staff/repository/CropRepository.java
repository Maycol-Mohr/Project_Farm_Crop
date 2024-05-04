package com.example.demo.staff.repository;

import com.example.demo.staff.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Class CropRepository.
 */
@Repository
public interface CropRepository extends JpaRepository<Crop, Integer> {
}