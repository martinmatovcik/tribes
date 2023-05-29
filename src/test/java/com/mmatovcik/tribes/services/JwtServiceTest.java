package com.mmatovcik.tribes.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mmatovcik.tribes.models.TribesUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtServiceTest {
  @Autowired JwtService jwtService;

  @Test
  public void isTokenValidForUsername_returnsTrue_successful() {
    //Given
    String username = "username";
    TribesUser mockedUser = new TribesUser(username, "password");
    String token = jwtService.generateToken(mockedUser);

    //When

    //Then
    assertTrue(jwtService.isTokenValidForUsername(token, username));

  }
  @Test
  public void isTokenValidForUsername_returnsFalse_successful() {
    //Given
    TribesUser mockedUser = new TribesUser("username", "password");
    String token = jwtService.generateToken(mockedUser);

    //When

    //Then
    assertFalse(jwtService.isTokenValidForUsername(token, "wrongUsername"));

  }
}
