package com.project.hyperface_project.security;

import com.project.hyperface_project.constants.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtTokenGenerator {
    public String generateToken(Authentication authentication){

        String username=authentication.getName();
        Date creationDate=new Date();
        Date expirationDate=new Date(creationDate.getTime()+ SecurityConstant.JWT_EXPIRATION);

        io.jsonwebtoken.JwtBuilder builder = Jwts.builder();
        builder.setSubject(username);

        builder.setExpiration(expirationDate);

        builder.setIssuedAt(new Date());

        builder.signWith(getSigningKey(),SignatureAlgorithm.HS256);

        String jwt= builder.compact();
        System.out.println("Reached end");
        return jwt;
    }
    public String getUsernameFromToken(String token){
        Claims claims=Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        System.out.println(claims.getSubject());
        return claims.getSubject();
    }
    public Boolean validateToken(String token) throws AuthenticationCredentialsNotFoundException, ExpiredJwtException {
            System.out.println(token);
            Claims claims=Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
            System.out.println("done");
            return true;
    }
    private Key getSigningKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SecurityConstant.secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
