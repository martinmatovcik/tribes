package com.mmatovcik.tribes.services;

import com.mmatovcik.tribes.models.TribesUser;
import io.jsonwebtoken.Claims;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
  String extractUsername(String token);

  <T> T extractOneClaim(String token, Function<Claims, T> claimResolver);

  String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

  String generateToken(TribesUser user);

  Boolean isTokenValidForUsername(String token, String expectedUsername);
}
