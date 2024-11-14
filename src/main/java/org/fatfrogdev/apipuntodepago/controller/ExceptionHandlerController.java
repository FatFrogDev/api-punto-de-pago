package org.fatfrogdev.apipuntodepago.controller;

import org.fatfrogdev.apipuntodepago.domain.exceptions.APIWeatherException;
import org.fatfrogdev.apipuntodepago.domain.exceptions.ConversionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({APIWeatherException.class})
    public ResponseEntity<String> handleAPIWeatherException(APIWeatherException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler({ConversionException.class})
    public ResponseEntity<String> handleConversionException(ConversionException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
