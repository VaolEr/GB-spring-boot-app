package com.example.storehouse.web;

import com.example.storehouse.dto.RestResponseTo;
import com.example.storehouse.util.exception.NotFoundException;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<RestResponseTo<?>> handleNotFoundException(RuntimeException ex) {
        return new ResponseEntity<>(
            new RestResponseTo<>(
                HttpStatus.NOT_FOUND.toString(),
                ex.getLocalizedMessage(),
                Collections.emptyList()
            ),
            HttpStatus.NOT_FOUND
        );
    }

}
