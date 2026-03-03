package com.aparzero.maya.controller;


import com.aparzero.maya.domain.ResponseObj;
import com.aparzero.maya.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseObj> handleDatabaseValidationException(NotFoundException ex) {
        return ResponseObj.failed(ex.getMessage(), HttpStatus.NOT_FOUND.value());
    }
}
