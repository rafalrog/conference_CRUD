package com.sda.conference_room.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler{

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return errorMap;
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorModel> handleEntityNotFound(NotFoundException ex){
        ErrorModel error = new ErrorModel(HttpStatus.NOT_FOUND, "Entity not found", ex.getMessage());
        log.debug(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(NameIsNotUniqueException.class)
    private ResponseEntity<ErrorModel> handleEntityNotUnique(NameIsNotUniqueException ex){
        ErrorModel error = new ErrorModel(HttpStatus.BAD_REQUEST, "Name must be unique", ex.getMessage());
        log.debug(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}