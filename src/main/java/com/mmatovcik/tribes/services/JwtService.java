package com.mmatovcik.tribes.services;

import com.mmatovcik.tribes.models.TribesUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  private static final String SECRET_KEY =
      "645267556B58703273357538782F413F4428472B4B6250655368566D59713374";
  private final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(getSigningKey()).build();

  public String extractUsername(String token) {
    return extractOneClaim(token, Claims::getSubject);
  }

  public <T> T extractOneClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateTokenWithExtraClaims(
      Map<String, Object> extraClaims, UserDetails userDetails) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(convertLocalDateTimeToDate(LocalDateTime.now()))
        .setExpiration(convertLocalDateTimeToDate(LocalDateTime.now().plusMinutes(60)))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String generateToken(TribesUser user) {
    return generateTokenWithExtraClaims(new HashMap<>(), user);
  }

  public Boolean isTokenValidForUsername(String token, String expectedUsername) {
    return (extractUsername(token).equals(expectedUsername)) && !isTokenExpired(token);
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).isBefore(LocalDateTime.now());
  }

  private LocalDateTime extractExpiration(String token) {
    return convertDateToLocalDateTime(extractOneClaim(token, Claims::getExpiration));
  }

  private Claims extractAllClaims(String token) {
    return jwtParser.parseClaimsJws(token).getBody();
  }

  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private Date convertLocalDateTimeToDate(LocalDateTime dateToConvert) {
    return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
  }

  private LocalDateTime convertDateToLocalDateTime(Date dateToConvert) {
    return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }
}
