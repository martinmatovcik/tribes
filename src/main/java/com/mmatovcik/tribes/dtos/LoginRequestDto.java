package com.mmatovcik.tribes.dtos;

import com.mmatovcik.tribes.models.Role;
import com.mmatovcik.tribes.models.TribesUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequestDto {
  @NotBlank(message = "Username can not be empty")
  private String username;

  @NotBlank(message = "Password can not be empty")
  @Pattern(message = "Password needs to be at least 8 characters long.", regexp = ".{8,}$")
  private String password;

  public TribesUser toUser() {
    return new TribesUser(this.username, this.password, Role.USER);
  }
}
