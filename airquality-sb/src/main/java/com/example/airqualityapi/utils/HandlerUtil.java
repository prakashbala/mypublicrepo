package com.example.airqualityapi.utils;

import com.example.airqualityapi.model.CityNotFoundException;
import com.example.airqualityapi.model.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class HandlerUtil {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        return new ResponseEntity<>(new ExceptionResponse(null, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = CityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(CityNotFoundException ce) {
        return new ResponseEntity<>(ce.getExceptionResponse(), HttpStatus.NOT_FOUND);
    }
}
