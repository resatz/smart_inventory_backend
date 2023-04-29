package com.incedo.smart_inventory.common.exception_handlers;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

@ControllerAdvice(basePackages = "com.incedo.smart_inventory.controllers")
public class RestExceptionHandler {
	
	@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
			org.hibernate.exception.ConstraintViolationException nestedEx = (org.hibernate.exception.ConstraintViolationException) ex.getCause();
			return new ResponseEntity<String>(nestedEx.getSQLException().getMessage().split("\n")[1], HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
		StringBuffer error = new StringBuffer();
		ex.getConstraintViolations().forEach(violation -> {
			error.append(violation.getPropertyPath() + " " + violation.getMessage() + "\n");
		});
        return new ResponseEntity<String>(error.toString(), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<String> handleInvalidFormatException(InvalidFormatException ex) {
		return new ResponseEntity<String>(String.format("`%s` should be of type %s, but the given value \"%s\" is of type %s.", ex.getPath().get(0).getFieldName(), ex.getTargetType().getSimpleName(), ex.getValue(), ex.getValue().getClass().getSimpleName()), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<String> handleMismatchedInputException(MismatchedInputException ex) {
		return new ResponseEntity<String>(String.format("`%s` should be of type %s.", ex.getPath().get(0).getFieldName(), ex.getTargetType().getSimpleName()), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		return new ResponseEntity<String>("Resource with the given id does not exist.", HttpStatus.BAD_REQUEST);
    }
	
}
