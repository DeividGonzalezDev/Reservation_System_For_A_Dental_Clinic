package com.dh.dental_clinic.exceptions;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheNecessaryEntitiesForTheOperationDoNotExistException extends Exception {
  private Map<?,?> entities;

  public TheNecessaryEntitiesForTheOperationDoNotExistException(String msg, Map<?, ?> entities) {
    super(msg);
    this.entities = entities;
  }

  public TheNecessaryEntitiesForTheOperationDoNotExistException(Map<?, ?> entities) {
    super("The necessary entities for the operation do not exist");
    this.entities = entities;
  }
}
