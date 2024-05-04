package com.example.demo.service;

import com.example.demo.staff.dtos.FertilizerDto;
import com.example.demo.staff.entity.Fertilizer;
import com.example.demo.staff.repository.FertilizerRepository;
import com.example.demo.staff.service.FertilizerService;
import com.example.demo.staff.service.exception.FertilizerNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
public class FertilizerServiceTest {

  @Autowired
  FertilizerService fertilizerService;

  @MockBean
  FertilizerRepository fertilizerRepository;

  @Test
  public void testFertilizerCreation() {
    Fertilizer fertilizer = new Fertilizer();
    fertilizer.setId(1);
    fertilizer.setName("Nome Fertilizante");
    fertilizer.setBrand("Marca Fertilizante");
    fertilizer.setComposition("Composicao Fertilizante");

    FertilizerDto fertilizerToReturnDto = new FertilizerDto();
    fertilizerToReturnDto.setId(1);
    fertilizerToReturnDto.setName(fertilizer.getName());
    fertilizerToReturnDto.setBrand(fertilizer.getBrand());
    fertilizerToReturnDto.setComposition(fertilizer.getComposition());

    Mockito.when(fertilizerRepository.save(any())).thenReturn(fertilizer);

    FertilizerDto fertilizerDto = new FertilizerDto();

    FertilizerDto savedFertilizerDto = fertilizerService.insertFertilizer(fertilizerDto);

    Mockito.verify(fertilizerRepository).save(any());

    //Mockito.verify(fertilizerRepository, Mockito.times(1)).save(fertilizer);

    assertEquals(1, savedFertilizerDto.getId());
    assertEquals(fertilizer.getName(), savedFertilizerDto.getName());
    assertEquals(fertilizer.getBrand(), savedFertilizerDto.getBrand());
    assertEquals(fertilizer.getComposition(), savedFertilizerDto.getComposition());
  }

  @Test
  public void testFertilizerRetrievalFindById() {
    Fertilizer fertilizer = new Fertilizer();
    fertilizer.setName("Nome Fertilizante");
    fertilizer.setBrand("Marca Fertilizante");
    fertilizer.setComposition("Composicao Fertilizante");

    Mockito.when(fertilizerRepository.findById(eq(1)))
            .thenReturn(Optional.of(fertilizer));

    FertilizerDto returnedFertilizerDto = fertilizerService.findById(1);

    Mockito.verify(fertilizerRepository).findById(eq(1));

    Mockito.verify(fertilizerRepository, Mockito.times(1)).findById(1);

    assertEquals(returnedFertilizerDto.getId(), fertilizer.getId());
    assertEquals(returnedFertilizerDto.getName(), fertilizer.getName());
    assertEquals(returnedFertilizerDto.getBrand(), fertilizer.getBrand());
    assertEquals(returnedFertilizerDto.getComposition(), fertilizer.getComposition());
  }

  @Test
  public void testFertilizerRetrievalByIdNotFound() {
    Mockito.when(fertilizerRepository.findById(any())).thenReturn(Optional.empty());

    assertThrows(FertilizerNotFoundException.class, () -> fertilizerService.findById(999));

    Mockito.verify(fertilizerRepository).findById(eq(999));

    Mockito.verify(fertilizerRepository, Mockito.times(1)).findById(999);
  }

  @Test
  public void testFertilizerGetAll() {
    Fertilizer fertilizer = new Fertilizer();
    fertilizer.setName("Nome Fertilizante");
    fertilizer.setBrand("Marca Fertilizante");
    fertilizer.setComposition("Composicao Fertilizante");

    Fertilizer fertilizer2 = new Fertilizer();
    fertilizer.setName("Nome Fertilizante 2");
    fertilizer.setBrand("Marca Fertilizante 2");
    fertilizer.setComposition("Composicao Fertilizante 2");

    List<Fertilizer> list = new ArrayList<>();
    list.add(fertilizer);
    list.add(fertilizer2);

    //Mockito.when(fertilizerRepository.findAll()).thenReturn((list));
    Mockito.when(fertilizerRepository.findAll()).thenReturn((List.of(fertilizer)));

    //Mockito.when(fertilizerService.findAll()).thenReturn(List.of());

    //List<FertilizerDto> list2 = fertilizerService.findAll();

    Mockito.when(fertilizerRepository.findAll()).thenReturn(list);

    //Mockito.verify(fertilizerRepository, Mockito.times(1)).findAll();
  }

  @Test
  @Transactional
  public void testFertilizerUpdate() {
    Fertilizer fertilizer = new Fertilizer();
    fertilizer.setId(1);

    FertilizerDto fertilizerUpdated = new FertilizerDto();
    fertilizerUpdated.setName("Nome Fertilizante");
    fertilizerUpdated.setBrand("Marca Fertilizante");
    fertilizerUpdated.setComposition("Composicao Fertilizante");

    Mockito.when(fertilizerRepository.getById(eq(1))).thenReturn(fertilizer);

    FertilizerDto result = fertilizerService.updateFertilizer(1, fertilizerUpdated);

    Mockito.verify(fertilizerRepository, Mockito.times(1)).save(fertilizer);

    assertEquals("Nome Fertilizante", result.getName());
    assertEquals("Marca Fertilizante", result.getBrand());
    assertEquals("Composicao Fertilizante", result.getComposition());
  }

  @Test
  public void testDeleteFertilizer() {
    Fertilizer fertilizer = new Fertilizer();
    fertilizer.setName("Nome Fertilizante");
    fertilizer.setBrand("Marca Fertilizante");
    fertilizer.setComposition("Composicao Fertilizante");

    Assertions.assertDoesNotThrow(() -> {fertilizerService.deleteFertilizer(fertilizer.getId());
    });

    Mockito.verify(fertilizerRepository, Mockito.times(1)).deleteById(fertilizer.getId());
  }

  @Test
  public void deleteShouldThrowExceptionWhenIdDoesNotExist() {

    Fertilizer fertilizer = new Fertilizer();
    fertilizer.setName("Nome Fertilizante");
    fertilizer.setBrand("Marca Fertilizante");
    fertilizer.setComposition("Composicao Fertilizante");

    Mockito.doThrow(FertilizerNotFoundException.class).when(fertilizerRepository).deleteById(999);

    Assertions.assertThrows(FertilizerNotFoundException.class, () -> {fertilizerService.deleteFertilizer(999);
    });

    Mockito.verify(fertilizerRepository, Mockito.times(1)).deleteById(999);
  }
}