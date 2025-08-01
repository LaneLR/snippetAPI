package com.verizon.springtesting.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    //note to self: final means this can't be changed.
    //similar to 'const' in Javascript
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long TOKENLIFETIME = 1000 * 60 * 60; //an hour

    //generate the token and assume we're logging in with an email
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) //set subject to the parameter
                .setIssuedAt(new Date()) // set the current date the token was issued
                .setExpiration(new Date(System.currentTimeMillis() + TOKENLIFETIME)) //set expiration
                .signWith(key) //sign it
                .compact();

            //note: I like how setting up a JWT in Java is both straight forward and granular;
    }

    // now use the token
    public String extractEmail(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            //note: after parseBuilder() set the key, build it, parse the claims, then get body and subject
    }

    //finally determine if the token is valid
    public boolean validateJwt(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }


}
