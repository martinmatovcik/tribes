package com.mmatovcik.tribes.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NoContentException extends RuntimeException {
  private final HttpStatus httpStatus = HttpStatus.NO_CONTENT;

  public NoContentException(String errorMessage) {
    super(errorMessage);
  }
}
