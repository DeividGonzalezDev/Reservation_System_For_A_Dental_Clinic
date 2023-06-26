package com.dh.dental_clinic.exceptions;

/**
 * Representa una excepción que ocurre cuando no hay entidad para actualizar en la base de datos.
 */
public class NoEntityToUpdateException extends Exception {
    /**
     * La entidad asociada con la excepción.
     */
    private Object entity;

    /**
     * Construye una nueva NoEntityToUpdateException con el mensaje de error y la entidad especificados.
     *
     * @param msg    El mensaje de error que describe la excepción.
     * @param entity La entidad asociada con la excepción.
     */
    public NoEntityToUpdateException(String msg, Object entity) {
        super(msg);
        this.entity = entity;
    }
     /**
     * Construye una nueva NoEntityToUpdateException con el mensaje de error y la entidad especificados.
     *
     * @param msg    El mensaje de error que describe la excepción.
     * @param entity La entidad asociada con la excepción.
     */
    public NoEntityToUpdateException(Object entity) {
        super("The entity to be updated does not exist");
        this.entity = entity;
    }

    /**
     * Obtiene la entidad asociada con la excepción.
     *
     * @return La entidad asociada con la excepción.
     */
    public Object getEntity() {
        return entity;
    }
  }

