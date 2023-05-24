package com.mmatovcik.tribes.dtos;

import com.mmatovcik.tribes.models.Role;
import com.mmatovcik.tribes.models.TribesUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginRequestDto(
    @NotBlank(message = "Username can not be empty") String username,
    @NotBlank(message = "Password can not be empty")
        @Pattern(message = "Password needs to be at least 8 characters long.", regexp = ".{8,}$")
        String password) {
  public TribesUser toUser() {
    return new TribesUser(username, password, Role.USER);
  }
}
