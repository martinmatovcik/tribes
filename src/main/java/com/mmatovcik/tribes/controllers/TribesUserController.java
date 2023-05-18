package com.mmatovcik.tribes.controllers;

import com.mmatovcik.tribes.dtos.LoginRequestDto;
import com.mmatovcik.tribes.dtos.RegistrationRequestDto;
import com.mmatovcik.tribes.dtos.ResponseDto;
import com.mmatovcik.tribes.services.TribesUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class TribesUserController {
  private final TribesUserService userService;

  @PostMapping("/register")
  public ResponseEntity<ResponseDto> register(@RequestBody RegistrationRequestDto requestDto) {
    userService.register(requestDto.toUser(), requestDto.getKingdomName());
    return new ResponseEntity<>(new ResponseDto("Registration was successful!"), HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseDto> login(@RequestBody LoginRequestDto requestDto) {
    userService.login(requestDto.getUsername(), requestDto.getPassword());
    return new ResponseEntity<>(new ResponseDto("Login was successful!"), HttpStatus.OK);
  }
}
