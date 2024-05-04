package com.example.demo.staff.service.exception;

/**
 * Exception for when a person is not found.
 */
public class PersonExistsException extends RuntimeException {

  public PersonExistsException(String msg) {

    super(msg);
  }

  //public PersonExistsException() {

    //super("Username já cadastrado na base de dados! Cadastre outro!");
  //}
}
