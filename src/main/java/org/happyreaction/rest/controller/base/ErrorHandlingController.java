package org.happyreaction.rest.controller.base;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Abstract class to REST controller for exception handling for REST controller implementation.
 * 
 * @author Ignas
 *
 */
public abstract class ErrorHandlingController {

    private final Logger log = Logger.getLogger(ErrorHandlingController.class.getName());

    /**
     * Handles exception if entity is not found in the system.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDTO handleEntityNotFoundExceptions(EntityNotFoundException exception, HttpServletResponse response) {
        return new ErrorDTO("Entity not found");
    }

    /**
     * Handles exception when save/update results in validation error.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ErrorDTO handleConstraintViolationExceptions(ConstraintViolationException exception, HttpServletResponse response) {
        ErrorDTO error = new ErrorDTO("Validation failed");
        if (exception.getConstraintViolations() != null) {
            for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
                error.addDetail(cv.getPropertyPath() + " " + cv.getMessage() + " (" + cv.getInvalidValue() + ")");
            }
        }
        return error;
    }

    /**
     * Handles all unknown unexpected exceptions that can happen during WS call.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDTO handleExceptions(Exception exception, HttpServletResponse response) {
        log.error("[ErrorHandlingController]", exception);
        return new ErrorDTO("Internal server error");
    }


}
