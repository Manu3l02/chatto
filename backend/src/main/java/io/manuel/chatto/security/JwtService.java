package io.manuel.chatto.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.manuel.chatto.model.User;


@Service
public class JwtService {
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	@Value("${jwt.expiration}")
	private long expiration;
	
	private Key getSigningKey() {
		return  Keys.hmacShaKeyFor(secretKey.getBytes());
	}
	
	public String generateToken(User user) {
		Date now = new Date();
		Date exp = new Date(now.getTime() + expiration);
		
		return Jwts.builder()
				.setSubject(user.getEmail())
				.setIssuedAt(now)
				.setExpiration(exp)
				.signWith(getSigningKey())
				.compact();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String extractEmail(String token) {
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(getSigningKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
		
		return claims.getSubject();
	}
}
