package com.chandu.movie_service.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.chandu.movie_service.exceptions.InvalidDataException;
import com.chandu.movie_service.exceptions.NotFoundException;


import lombok.Getter;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @Getter
    static class Error{
        public final String reason;
        public final String message;

        public Error(String reason, String message) {
            this.reason = reason;
            this.message = message;
        }
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException exception)
    {
        Error error = new Error(HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage());

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<?> handleNotFoundException(InvalidDataException exception)
    {
        Error error = new Error(HttpStatus.BAD_REQUEST.getReasonPhrase(), exception.getMessage());

        return ResponseEntity.badRequest().body(error);

        
    }
}
