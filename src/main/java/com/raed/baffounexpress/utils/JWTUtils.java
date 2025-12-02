package com.raed.baffounexpress.utils;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.raed.baffounexpress.user.DTO.FullUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtils {
    @Value("${bezkoder.app.jwtSecret}")
    private String jwtSecret;

    @Value("${bezkoder.app.jwtExpirationMs}")
    private Long jwtExpirationMs;
    @Value("${bezkoder.app.jwtRefreshExpirationMs}")
    private Long jwtRefreshExpirationMs;

    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));

    }

    public String signJWT(Authentication auth) {
        FullUser user = (FullUser) auth.getPrincipal();
        return Jwts.builder().issuedAt(new Date()).subject(user.getUsername())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(key()).compact();

    }
    public String signRefreshToken(Authentication auth) {
        FullUser user = (FullUser) auth.getPrincipal();
        return Jwts.builder().issuedAt(new Date()).subject(user.getUsername())
                .expiration(new Date((new Date()).getTime() + jwtRefreshExpirationMs)).signWith(key()).compact();
    }

    public String extractUsernameFromToken(String token) {
        SecretKey key = (SecretKey) key();
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();

    }

    public boolean validateToken(String token) {
        try {
            SecretKey key = (SecretKey) key();
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;

        } catch (MalformedJwtException e) {
            System.err.println(e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println(e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        return false;

    }

}
