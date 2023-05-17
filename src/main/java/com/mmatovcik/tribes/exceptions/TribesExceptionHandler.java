package com.mmatovcik.tribes.exceptions;

import com.mmatovcik.tribes.dtos.ErrorDto;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TribesExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorDto> handleNotFound(NotFoundException e) {
    return new ResponseEntity<>(new ErrorDto(404, List.of(e.getMessage())), e.getHttpStatus());
  }

  @ExceptionHandler(NotUniqueException.class)
  public ResponseEntity<ErrorDto> handleNotUnique(NotUniqueException e) {
    return new ResponseEntity<>(new ErrorDto(409, List.of(e.getMessage())), e.getHttpStatus());
  }
}
