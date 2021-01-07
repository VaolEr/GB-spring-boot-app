package com.example.storehouse.web;

import com.example.storehouse.dto.RestResponseTo;
import com.example.storehouse.util.exception.IllegalRequestDataException;
import com.example.storehouse.util.exception.NotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collections;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@RestControllerAdvice
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class, EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<RestResponseTo<?>> handleNotFound(RuntimeException ex) {
        return new ResponseEntity<>(
            new RestResponseTo<>(
                HttpStatus.NOT_FOUND.toString(),
                ex.getLocalizedMessage(),
                Collections.emptyList()
            ),
            HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({IllegalRequestDataException.class, SQLIntegrityConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<RestResponseTo<?>> handleBadRequest(RuntimeException ex) {
        return new ResponseEntity<>(
            new RestResponseTo<>(
                HttpStatus.BAD_REQUEST.toString(),
                ex.getLocalizedMessage(),
                Collections.emptyList()
            ),
            HttpStatus.BAD_REQUEST
        );
    }

}
