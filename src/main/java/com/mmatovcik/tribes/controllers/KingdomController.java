package com.mmatovcik.tribes.controllers;

import com.mmatovcik.tribes.dtos.KingdomDto;
import com.mmatovcik.tribes.exceptions.NoContentException;
import com.mmatovcik.tribes.models.Kingdom;
import com.mmatovcik.tribes.services.KingdomService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class KingdomController {
  private KingdomService kingdomService;

  @GetMapping("/kingdoms")
  public ResponseEntity<List<KingdomDto>> getAllKingdoms() {
    List<Kingdom> kingdoms = kingdomService.findAllKingdoms();

    if (kingdoms.isEmpty()) {
      throw new NoContentException("No content.");
    }

    List<KingdomDto> output = new ArrayList<>();

    for (Kingdom kingdom : kingdoms) {
      output.add(kingdom.toDto());
    }

    return new ResponseEntity<>(output, HttpStatus.OK);
  }
}
