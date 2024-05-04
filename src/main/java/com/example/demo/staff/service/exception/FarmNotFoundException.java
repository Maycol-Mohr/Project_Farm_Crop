package com.example.demo.staff.service.exception;

/**
 * Class FarmNotFoundException.
 */
public class FarmNotFoundException extends RuntimeException {

  public FarmNotFoundException(String msg) {
    super(msg);
  }
}