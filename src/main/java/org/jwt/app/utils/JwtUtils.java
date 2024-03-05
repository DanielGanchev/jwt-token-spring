package org.jwt.app.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtUtils {
  @Value("${application.JWT_SECRET}")
  private String jwtSecret;

  public String generate(String username) {
    var now = Instant.now();
    var tenMinutesInSeconds = 10 * 60;
    var expiresAt = now.plus(tenMinutesInSeconds, ChronoUnit.SECONDS);

    return JWT.create()
        .withIssuer("self")
        .withClaim("username", username)
        .withSubject(username)
        .withIssuedAt(now)
        .withExpiresAt(expiresAt)
        .sign(Algorithm.HMAC256(jwtSecret));
  }

  public String verify(String token) {
    return JWT.require(Algorithm.HMAC256(jwtSecret))
        .build()
        .verify(token)
        .getSubject();
  }
}