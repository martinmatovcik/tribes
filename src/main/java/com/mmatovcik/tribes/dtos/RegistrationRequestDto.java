package com.mmatovcik.tribes.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegistrationRequestDto extends LoginRequestDto {
  @NotBlank(message = "Username can not be empty")
  private String kingdomName;
}
