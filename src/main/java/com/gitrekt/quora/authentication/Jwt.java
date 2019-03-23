package com.gitrekt.quora.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** A Utility class for verifying and decoding JSON Web Tokens (JWT). */
public class Jwt {

  /**
   * The Secret used for signing the token.
   */
  private static final String SECRET = System.getenv("JWT_SECRET");

  private static final String ISSUER = "gitrekt.quora";

  private static final Algorithm ALGORITHM = Algorithm.HMAC512(SECRET);

  private static final JWTVerifier JWT_VERIFIER = JWT.require(ALGORITHM).withIssuer(ISSUER).build();

  /**
   * This method will verify and decode the JWT, returning a Map of String claims. If the JWT is
   * invalid or expired this will throw a JWTVerificationException.
   *
   * @param token A String representing the token
   * @return The decoded claims
   */
  public static Map<String, Object> verifyAndDecode(String token) {
    DecodedJWT decoded = JWT_VERIFIER.verify(token);
    Map<String, Object> claims = new HashMap<>();
    Map<String, Claim> decodedClaims = decoded.getClaims();
    for (String key : decodedClaims.keySet()) {
      if (key.equals("exp") || key.equals("iat")) {
        claims.put(key, decodedClaims.get(key).asDate());
      } else {
        claims.put(key, decodedClaims.get(key).asString());
      }
    }
    return claims;
  }
}
