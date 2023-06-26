package com.dh.dental_clinic.controllers.base;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dh.dental_clinic.dto.ErrorResponse;
import com.dh.dental_clinic.exceptions.NoEntityToUpdateException;
import com.dh.dental_clinic.exceptions.TheEntityAlredyExistsException;

import jakarta.validation.ConstraintViolationException;


@RestControllerAdvice
public class BaseController {
  private static final Logger logger = LogManager.getLogger(BaseController.class);

  
  /**
 * Maneja ConstraintViolationException y devuelve un ErrorResponse con detalles del error.
 *
 * @param  ex la ConstraintViolationException a manejar
 * @return    el ResponseEntity que contiene el ErrorResponse
 */
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
    Map<String, String> errors = new HashMap<String, String>();
      ex.getConstraintViolations().forEach((error) -> {

        String fieldName = error.getPropertyPath() != null ? error.getPropertyPath().toString() : "";
        String message = error.getMessage() != null ? error.getMessage() : "";


        errors.put(fieldName, message);
      });
      logger.error(ex);
      ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
          "Los Datos introducidos no son válidos", errors);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

  }

  /**
 * Maneja una TransactionSystemException y devuelve una ResponseEntity con un objeto ErrorResponse.
 *
 * @param  ex   la TransactionSystemException que se está manejando
 * @return      una ResponseEntity que contiene un objeto ErrorResponse
 */
  //Un manejador de excepciones para la clase TransactionSystemException que devuelve una respuesta HTTP con un estado de 400 Bad Request. Además, si la causa raíz de la excepción es una ConstraintViolationException, llama a otro método para manejar esa excepción específica. En caso contrario, registra el error y devuelve una entidad de respuesta con un mensaje de error.
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(TransactionSystemException.class)
  public ResponseEntity<ErrorResponse> handleTransactionSystemException(TransactionSystemException ex) {
    if (ex.getRootCause() instanceof ConstraintViolationException) {
      ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex.getRootCause();
      handleConstraintViolationException(constraintViolationException);
    }
    logger.error(ex.getRootCause());
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
        "Se ha producido un error, por favor intentelo mas tarde");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

 /**
 * Maneja la excepción TheEntityAlredyExistsException devolviendo un ResponseEntity
 * con un objeto ErrorResponse que contiene la información del error relevante.
 *
 * @param  ex  la excepción TheEntityAlredyExistsException capturada
 * @return     un ResponseEntity con un objeto ErrorResponse
 */
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(TheEntityAlredyExistsException.class)
  public ResponseEntity<ErrorResponse> handleTheEntityAlredyExistsException(TheEntityAlredyExistsException ex) {
    logger.error("There has been an error because the entity already exists in the database :(");
    logger.error(ex);
    Map<String, Object> errors = new HashMap<>();
    errors.put("entity", ex.getEntity());
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
        "La entidad ya existe", errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  /**
 * Maneja la excepción NoEntityToUpdateException devolviendo un ResponseEntity con un cuerpo de ErrorResponse y
 * un código de estado HttpStatus.BAD_REQUEST. El cuerpo de ErrorResponse contiene un Map con la entidad que
 * falló al actualizarse.
 *
 * @param  ex  la excepción NoEntityToUpdateException que se manejará
 * @return     un ResponseEntity con un cuerpo de ErrorResponse y un código de estado HttpStatus.BAD_REQUEST
 */
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(NoEntityToUpdateException.class)
  public ResponseEntity<ErrorResponse> handleNoEntityToUpdateException(NoEntityToUpdateException ex) {
    logger.error("There has been an error because the entity to be updated does not exist in the database :(");
    logger.error(ex);
    Map<String, Object> errors = new HashMap<>();
    errors.put("entity", ex.getEntity());
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
        "La entidad no existe", errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  /**
 * Maneja cualquier excepción lanzada por la aplicación y devuelve un ResponseEntity con un objeto ErrorResponse.
 *
 * @param  ex  la excepción que fue lanzada
 * @return     un ResponseEntity que contiene un objeto ErrorResponse y un código de estado HTTP de INTERNAL_SERVER_ERROR
 */
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Estamos experimentando problemas, intentalo nuevamente mas tarde");
    logger.error("There has been an un controlled exception", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

}