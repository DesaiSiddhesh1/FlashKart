package com.flashkart.userservice.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;


@Component
public class JwtUtil {
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private long expiration;
	
	
	
	public String generateToken(String email) {
		SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
		
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+expiration))
				.signWith(key)
				.compact();
	}
	
	public String extractUsername(String token) {
		SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
		
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public boolean validateToken(String token,String email)
	{
		String username = extractUsername(token);
		
		return username.equals(email);
	}
}
