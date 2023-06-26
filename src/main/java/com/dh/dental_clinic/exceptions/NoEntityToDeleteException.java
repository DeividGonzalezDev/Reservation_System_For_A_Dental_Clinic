package com.dh.dental_clinic.exceptions;

import java.util.UUID;

public class NoEntityToDeleteException extends Exception {
   /**
     * El id asociada con la excepción.
     */
    private UUID id;

    /**
     * Construye una nueva NoEntityToDeleteException con el mensaje de error y el id especificado.
     *
     * @param msg    El mensaje de error que describe la excepción.
     * @param id     El id asociada con la excepción.
     */
    public NoEntityToDeleteException(String msg, UUID id) {
        super(msg);
        this.id = id;
    }
     /**
     * Construye una nueva NoEntityToDeleteException con el mensaje de error y el id especificado.
     *
     * @param id     El id asociada con la excepcion.
     */
    public NoEntityToDeleteException(UUID id) {
        super("The entity to be deleted does not exist");
        this.id = id;
    }

    /**
     * Obtiene el id asociado con la excepción.
     *
     * @return El id asociado con la excepción.
     */
    public UUID getId() {
        return id;
    }
}
