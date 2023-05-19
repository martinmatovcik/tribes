package com.mmatovcik.tribes.services;

import io.jsonwebtoken.Claims;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
  String extractUsername(String token);
  <T> T extractOneClaim(String token, Function<Claims, T> claimResolver);
  String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
  Boolean isTokenValidForUsername(String token, String expectedUsername);
}
