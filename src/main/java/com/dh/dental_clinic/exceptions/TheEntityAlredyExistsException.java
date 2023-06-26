package com.dh.dental_clinic.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter

/**
 * Lanzada cuando se intenta crear una entidad que ya existe en el sistema.
 */
public class TheEntityAlredyExistsException extends Exception {
  Object entity;

  /**
   * Crea una nueva instancia de la excepción con la entidad que ya existe.
   *
   * @param entity La entidad que ya existe.
   */
  public TheEntityAlredyExistsException(Object entity) {
    super("The Entity Already Exists");
    this.entity = entity;
  }
}

