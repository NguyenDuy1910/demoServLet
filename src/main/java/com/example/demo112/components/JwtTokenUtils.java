package com.example.demo112.components;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;

import javax.crypto.SecretKey;


import javax.crypto.spec.SecretKeySpec;

import java.security.SecureRandom;
import java.util.*;
import java.util.function.Function;

public class JwtTokenUtils {

    private int expiration = 2592000; // Default expiration time in seconds

    private String secretKey = "TaqlmGv1iEDMRiFp/pHuID1+T84IABfuA0xXh4GhiUI="; // Default secret key

    public String generateToken(com.example.demo112.models.User user) throws Exception {
        //properties => claims
        Map<String, Object> claims = new HashMap<>();
        //this.generateSecretKey();
        claims.put("phoneNumber", user.getPhoneNumber());
        claims.put("userId", user.getId());
        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        } catch (Exception e) {
            throw new Exception("Cannot create jwt token, error: " + e.getMessage());
        }
    }

    private SecretKey getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return new SecretKeySpec(bytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256-bit key
        random.nextBytes(keyBytes);
        return Encoders.BASE64.encode(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //check expiration
    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    public String extractPhoneNumber(String token) {
        return extractClaim(token, Claims::getSubject);
    }
//    public boolean validateToken(String token, UserDetails userDetails) {
//        String phoneNumber = extractPhoneNumber(token);
//        return (phoneNumber.equals(userDetails.getUsername()))
//                && !isTokenExpired(token);
//    }

//    api send mail google grid
//19102003@nguyendinhquocduy//

}

