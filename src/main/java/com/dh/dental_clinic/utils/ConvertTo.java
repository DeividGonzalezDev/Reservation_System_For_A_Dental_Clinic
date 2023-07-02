package com.dh.dental_clinic.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Clase de utilidad para convertir entidades en objetos DTO.
 */
public class ConvertTo {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  /**
   * Convierte una entidad en un objeto DTO.
   *
   * @param entity    La entidad a convertir.
   * @param dtoClass  La clase del DTO en la que se realizará la conversión.
   * @param <E>       El tipo de la entidad.
   * @param <D>       El tipo del DTO.
   * @return El objeto DTO convertido.
   */
  public static <E, D> D dto(E entity, Class<D> dtoClass) {
    D classDTO = OBJECT_MAPPER.convertValue(entity, dtoClass);
    return classDTO;
  }
  

  /**
   * Convierte una lista de entidades en una lista de objetos DTO.
   *
   * @param entities  La lista de entidades a convertir.
   * @param dtoClass  La clase del DTO en la que se realizará la conversión.
   * @param <E>       El tipo de las entidades.
   * @param <D>       El tipo del DTO.
   * @return La lista de objetos DTO convertidos.
   */
  public static <E, D> List<D> dto(List<E> entities, Class<D> dtoClass) {
    List<D> classesDTO = new ArrayList<>();
    for (E entity : entities) {
      D classDTO = OBJECT_MAPPER.convertValue(entity, dtoClass);
      classesDTO.add(classDTO);
    }
    return classesDTO;
  }
 /**
   * Convierte una objetoDTO en una entidad.
   *
   * @param dtoEntity El objetoDTO a convertir.
   * @param entity    La entidad a la que se realizará la conversión.
   * @param extraParamsToAdd Los parámetros adicionales para agregar a la entidad.
   * @param <E>       El tipo del DTO.
   * @param <D>       El tipo de la entidad.
   * @return La entidad convertida.
   */
  public static <E, D> D entity(E dtoEntity, Class<D> entity, Map<String, Object> extraParamsToAdd) {
    D entityConverted = OBJECT_MAPPER.convertValue(dtoEntity, entity);

    if(entityConverted instanceof Map){
      ((Map) entityConverted).putAll(extraParamsToAdd);
    }else{
      Map<String, Object> entityMap = OBJECT_MAPPER.convertValue(entityConverted, Map.class);
    entityMap.putAll(extraParamsToAdd);
    entityConverted = OBJECT_MAPPER.convertValue(entityMap, entity);
    }
    return entityConverted;
  }
}

