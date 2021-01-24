package br.com.gustavoakira.agenda.controller;

import br.com.gustavoakira.agenda.exception.CustomExceptionResponse;
import br.com.gustavoakira.agenda.exception.NoteNotFoundException;
import br.com.gustavoakira.agenda.exception.UserNotAllowed;
import br.com.gustavoakira.agenda.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalRestControllerAdvice {
    @ExceptionHandler(value = UserNotAllowed.class)
    public ResponseEntity<CustomExceptionResponse> handleUserNotAllowed(UserNotAllowed e){
        CustomExceptionResponse exception = new CustomExceptionResponse( "FORBIDDEN",e.getMessage());
        exception.setTime(LocalDateTime.now());
        exception.setStatus((HttpStatus.FORBIDDEN.value()));
        return new ResponseEntity<>(exception,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {UserNotFoundException.class, NoteNotFoundException.class})
    public ResponseEntity<CustomExceptionResponse> handleNotFoundExceptions(RuntimeException e){
        CustomExceptionResponse response = new CustomExceptionResponse( "NOT_FOUND",e.getMessage());
        response.setTime(LocalDateTime.now());
        response.setStatus((HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<CustomExceptionResponse> handleGenericExceptions(Exception e){
        CustomExceptionResponse response = new CustomExceptionResponse( "INTERNAL_SERVER_ERROR",e.getMessage());
        response.setTime(LocalDateTime.now());
        response.setStatus((HttpStatus.INTERNAL_SERVER_ERROR.value()));
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}