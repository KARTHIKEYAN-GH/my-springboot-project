package com.demo.token.serviceImpl;

import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.demo.token.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	private final String SECRET_KEY = "f6feb1191c573a5a3f71ad6c3b90f2e8798858c872b06aabb9294e5d216ec5b7";

	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public boolean isValid(String token, UserDetails users) {
		String username = extractUserName(token);
		return (username.equals(users.getUsername())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> resolver) {
		Claims claims = extractAllClaims(token);
		return resolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getSigninKey()).build().parseSignedClaims(token).getPayload();
	}

	public String generateToken(Users users) {

		String token = Jwts.builder().subject(users.getUserName()).claim("role", users.getRole().name())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
				// .signWith(getSigninKey(),SignatureAlgorithm.HS384)
				.signWith(getSigninKey()).compact();
		return token;

	}

	private SecretKey getSigninKey() {
		byte[] keyBytes = Base64.getUrlDecoder().decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		String username = extractUserName(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
}
