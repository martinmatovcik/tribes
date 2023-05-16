package com.mmatovcik.tribes.controllers;

import com.mmatovcik.tribes.services.TribesUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class TribesUserController {
  private final TribesUserService userService;
}
