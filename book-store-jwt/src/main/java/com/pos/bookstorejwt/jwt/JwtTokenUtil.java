package com.pos.bookstorejwt.jwt;

import com.pos.bookstorejwt.repository.Role;
import com.pos.bookstorejwt.repository.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Log4j2
@Component
public class JwtTokenUtil {

    public static final long JWT_TOKEN_VALIDITY = 3 * 60 * 1000;          // 3 minutes

    @Value("${jwt.secret}")
    private String secret;

    @Value("${server.address}:${server.port}")
    private String issuer;

    private Claims getAllClaimsToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("role", user.getRole());

        return createToken(claims, user.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer(issuer)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = getAllClaimsToken(token);

        return claims.getSubject();
    }

    public Role getRoleFromToken(String token) {
        Claims claims = getAllClaimsToken(token);

        String role = claims.get("role").toString();

        switch (role.toUpperCase(Locale.ROOT)) {
            case "USER":
                return Role.USER;
            case "ADMIN":
                return Role.ADMIN;
            case "MANAGER":
                return Role.MANAGER;
            default:
                throw new RuntimeException("Rolul specificat nu exista");
        }
    }

    public Boolean isTokenExpired(String token) {
        Claims claims = getAllClaimsToken(token);

        return claims.getExpiration().before(new Date());
    }
}
