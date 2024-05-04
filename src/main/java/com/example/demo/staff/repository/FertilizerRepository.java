package com.example.demo.staff.repository;

import com.example.demo.staff.entity.Fertilizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Class FertilizerRepository.
 */
@Repository
public interface FertilizerRepository extends JpaRepository<Fertilizer, Integer> {
}