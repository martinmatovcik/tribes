package com.mmatovcik.tribes.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponseDto extends ResponseDto {
  private String token;
  public LoginResponseDto(String message, String token) {
    super(message);
    this.token = token;
  }
}
