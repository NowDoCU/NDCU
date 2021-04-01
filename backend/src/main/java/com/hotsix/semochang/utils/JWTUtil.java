package com.hotsix.semochang.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;

/**
 * author: pinest94
 * since: 2021-04-01
 */

@Slf4j
public class JWTUtil {

    private Key key;

    private long tokenValidMilisecond = 1000L * 60 * 60 * 24;

    public JWTUtil(String key) {
        this.key = Keys.hmacShaKeyFor(key.getBytes());
    }

    public String createToken(Long id, String email, String name, String status) {

        Date now = new Date();
        return Jwts.builder()
                .claim("id", id)
                .claim("email", email)
                .claim("name", name)
                .claim("status", status)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        if(validateToken(token)) { // 토큰이 유용할 경우
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        }
        return null;
    }

    /**
     * 토큰의 유효성 검증 메서드
     * */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.info("{}", "Expired JWT token");
        } catch (UnsupportedJwtException e) {
            log.info("{}", "Unsupported JWT token");
        } catch (MalformedJwtException e) {
            log.info("{}", "Invalid JWT token");
        } catch (IllegalArgumentException e) {
            log.info("{}", "JWT claims string is empty.");
        }
        return false;
    }
}
