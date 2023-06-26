package com.dh.dental_clinic.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.dh.dental_clinic.exceptions.NoEntityToDeleteException;
import com.dh.dental_clinic.exceptions.NoEntityToUpdateException;
import com.dh.dental_clinic.exceptions.TheEntityAlredyExistsException;
import com.dh.dental_clinic.exceptions.TheNecessaryEntitiesForTheOperationDoNotExistException;

import jakarta.validation.Valid;

/**
 * Interfaz que define las operaciones básicas de un servicio CRUD genérico.
 *
 * @param <T>   El tipo de entidad para las operaciones de CRUD.
 * @param <TDto> El tipo de objeto DTO (Data Transfer Object) para las operaciones de CRUD.
 */
public interface IBaseCrudService<T, TDto> {

    /**
     * Guarda una entidad.
     *
     * @param t La entidad a guardar.
     * @return El DTO que representa la entidad guardada.
     */
    TDto save(@Valid T t) throws TheEntityAlredyExistsException, TheNecessaryEntitiesForTheOperationDoNotExistException;

    /**
     * Obtiene una lista de todos los DTOs de las entidades.
     *
     * @return Una lista de DTOs de las entidades encontradas.
     */
    List<TDto> findAll();

    /**
     * Busca una entidad por su ID.
     *
     * @param id El ID de la entidad a buscar.
     * @return Un Optional que contiene el DTO de la entidad encontrada, o un Optional vacío si no se encontró la entidad.
     */
    Optional<TDto> findById(UUID id);

    /**
     * Elimina una entidad por su ID.
     *
     * @param id El ID de la entidad a eliminar.
     * @return true si la eliminación fue exitosa, false de lo contrario.
     * @throws NoEntityToDeleteException Si la entidad a eliminar no existe.
     */
    Boolean deleteById(UUID id) throws NoEntityToDeleteException;

    /**
     * Actualiza toda la entidad existente.
     *
     * @param t La entidad a actualizar.
     * @return El DTO que representa la entidad actualizada.
     * @throws NoEntityToUpdateException Si la entidad a actualizar no existe.
     * @throws TheNecessaryEntitiesForTheOperationDoNotExistException Si no existen las entidades necesarias para la operación
     */
    TDto update(@Valid T t) throws NoEntityToUpdateException, TheNecessaryEntitiesForTheOperationDoNotExistException;

    /**
     * Actualiza solo alguno de los parámetros de una entidad existente.
     *  @param t La entidad a actualizar.
     *  @return El DTO que representa la entidad actualizada.
     *  @throws NoEntityToUpdateException Si la entidad a actualizar no existe.
     * */
    //TDto updateSomeParameters(T t) throws NoEntityToUpdateException;
}

