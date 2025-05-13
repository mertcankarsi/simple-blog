package com.mertcankarsi.simpleblog.security;

import static org.junit.jupiter.api.Assertions.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.ArrayList;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

class JwtServiceTest {

  private JwtService jwtService;
  private String secretKey = "thisIsATestSecretKeyThatIsLongEnoughForHS256";
  private UserDetails userDetails;
  private String token;

  @BeforeEach
  void setUp() {
    jwtService = new JwtService();
    ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);

    userDetails = new User("testuser", "password", new ArrayList<>());
    token = generateTestToken(userDetails.getUsername());
  }

  @Test
  void extractUsername_ShouldReturnCorrectUsername() {
    String username = jwtService.extractUsername(token);
    assertEquals("testuser", username);
  }

  @Test
  void isTokenValid_ShouldReturnTrueForValidToken() {
    boolean isValid = jwtService.isTokenValid(token, userDetails);
    assertTrue(isValid);
  }

  @Test
  void isTokenValid_ShouldReturnFalseForInvalidUsername() {
    UserDetails differentUser = new User("otheruser", "password", new ArrayList<>());
    boolean isValid = jwtService.isTokenValid(token, differentUser);
    assertFalse(isValid);
  }

  @Test
  void isTokenValid_ShouldReturnFalseForExpiredToken() {
    String expiredToken = generateExpiredTestToken(userDetails.getUsername());
    boolean isValid = jwtService.isTokenValid(expiredToken, userDetails);
    assertFalse(isValid);
  }

  private String generateTestToken(String username) {
    return Jwts.builder()
        .subject(username)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
        .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
        .compact();
  }

  private String generateExpiredTestToken(String username) {
    return Jwts.builder()
        .subject(username)
        .issuedAt(new Date(System.currentTimeMillis() - 2000 * 60 * 60))
        .expiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60)) // 1 hour ago
        .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
        .compact();
  }
}
