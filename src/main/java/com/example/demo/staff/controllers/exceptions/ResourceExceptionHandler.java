package com.example.demo.staff.controllers.exceptions;

import com.example.demo.staff.service.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

/**
 * Class ResourceExceptionHandler.
 */
@ControllerAdvice
public class ResourceExceptionHandler {

  /**
   * FarmNotFoundException.
   */
  @ExceptionHandler(FarmNotFoundException.class)
  public ResponseEntity<StandardError> farmNotFound(FarmNotFoundException e,
                                                    HttpServletRequest request) {
    StandardError err = new StandardError();
    err.setTimestamp(Instant.now());
    err.setStatus(HttpStatus.NOT_FOUND.value());
    err.setError("Fazenda não encontrada!");
    err.setMessage(e.getMessage());
    err.setPath(request.getRequestURI());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

  /**
   * CropNotFoundException.
   */
  @ExceptionHandler(CropNotFoundException.class)
  public ResponseEntity<StandardError> cropNotFound(CropNotFoundException e,
                                                    HttpServletRequest request) {
    StandardError err = new StandardError();
    err.setTimestamp(Instant.now());
    err.setStatus(HttpStatus.NOT_FOUND.value());
    err.setError("Plantação não encontrada!");
    err.setMessage(e.getMessage());
    err.setPath(request.getRequestURI());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

  /**
   * PersonNotFoundException.
   */
  @ExceptionHandler(PersonNotFoundException.class)
  public ResponseEntity<StandardError> personNotFound(PersonNotFoundException e,
                                                      HttpServletRequest request) {
    StandardError err = new StandardError();
    err.setTimestamp(Instant.now());
    err.setStatus(HttpStatus.NOT_FOUND.value());
    err.setError("Pessoa não encontrada!");
    err.setMessage(e.getMessage());
    err.setPath(request.getRequestURI());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

  /**
   * PersonExistsException.
   */
  @ExceptionHandler(PersonExistsException.class)
  public ResponseEntity<StandardError> personExists(PersonExistsException e,
                                                      HttpServletRequest request) {
    StandardError err = new StandardError();
    err.setTimestamp(Instant.now());
    err.setStatus(HttpStatus.CONFLICT.value());
    err.setError("Username já existe!");
    err.setMessage(e.getMessage());
    err.setPath(request.getRequestURI());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
  }

  /**
   * FertilizerNotFoundException.
   */
  @ExceptionHandler(FertilizerNotFoundException.class)
  public ResponseEntity<StandardError> fertilizerNotFound(FertilizerNotFoundException e,
                                                          HttpServletRequest request) {
    StandardError err = new StandardError();
    err.setTimestamp(Instant.now());
    err.setStatus(HttpStatus.NOT_FOUND.value());
    err.setError("Fertilizante não encontrado!");
    err.setMessage(e.getMessage());
    err.setPath(request.getRequestURI());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }
}