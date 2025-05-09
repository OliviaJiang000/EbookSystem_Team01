package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
/**

 * Class Name: JwtUtil

 *

 * Purpose:

 * Utility class for generating, parsing, and validating JSON Web Tokens (JWT).

 * It uses a symmetric secret key (HMAC-SHA256) for signing and verifying tokens.

 *

 * Interface Description:

 * - generateToken: creates a signed JWT with username and expiration.

 * - extractUsername: parses a token to extract the subject (username).

 * - validateToken: checks if a token is valid and not expired.

 * - isTokenExpired: internal helper to check expiration date.

 *

 * Important Data:

 * - secret: configured signing secret (base64 string from application properties).

 * - expirationMs: token validity duration in milliseconds.

 * - key: cryptographic key derived from the secret.

 *

 * Development History:

 * - Designed by: Peilin Li

 * - Reviewed by: Yunyi Jiang Guanyuan Wang

 * - Created on: 2025-04-29

 * - Last modified: 2025-05-03 (Initial version)

 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expirationMs;

    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(org.springframework.security.core.userdetails.User userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // attation: setSubject
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean validateToken(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
