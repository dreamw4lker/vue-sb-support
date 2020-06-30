package com.betanet.util;

import com.betanet.domain.bean.UserBean;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtTokenUtil {

    public static String getUsernameFromToken(String token, String secret) {
        return getClaimFromToken(token, secret, Claims::getSubject);
    }

    public static Date getExpirationDateFromToken(String token, String secret) {
        return getClaimFromToken(token, secret, Claims::getExpiration);
    }

    public static <T> T getClaimFromToken(String token, String secret, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token, secret);
        return claimsResolver.apply(claims);
    }

    private static Claims getAllClaimsFromToken(String token, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private static Boolean isTokenExpired(String token, String secret) {
        final Date expiration = getExpirationDateFromToken(token, secret);
        return expiration.before(new Date());
    }

    public static String generateToken(UserBean userBean, String secret, long expirationTime) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("fio", userBean.getLogin());
        return doGenerateToken(claims, userBean.getLogin(), secret, expirationTime);
    }

    private static String doGenerateToken(Map<String, Object> claims, String subject, String secret, long expirationTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public static Boolean validateToken(String token, String secret, UserDetails userDetails) {
        final String username = getUsernameFromToken(token, secret);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, secret));
    }
}
