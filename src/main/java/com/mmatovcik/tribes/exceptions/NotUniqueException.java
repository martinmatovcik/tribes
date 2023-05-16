package com.mmatovcik.tribes.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotUniqueException extends RuntimeException {
  private final HttpStatus httpStatus = HttpStatus.CONFLICT;

  public NotUniqueException(String errorMessage) {
    super(errorMessage);
  }
}
