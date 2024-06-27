package com.emodi.emodibackv2.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtProvider {

	private static final String USER_ID = "user_id";
	@Value("${jwt.secret-key}")
	private String secretKey;

	public String generateToken(Long userId) {
		Instant now = Instant.now();
		byte[] secret = decodeSecretKey(secretKey);

		return Jwts.builder()
			.claim(USER_ID, userId)
			.setIssuedAt(Date.from(now))
			.setExpiration(Date.from(now.plus(2, ChronoUnit.HOURS)))
			.signWith(Keys.hmacShaKeyFor(secret))
			.compact();
	}

	public Long verifyToken(String token) {
		try {
			byte[] secret = decodeSecretKey(secretKey);

			Jws<Claims> result = Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(secret))
				.build()
				.parseClaimsJws(token);

			Claims body = result.getBody();
			return body.get(USER_ID, Long.class);

		} catch (SignatureException e) {
			throw new RuntimeException("유효하지 않은 JWT 시그니처입니다.", e);
		} catch (Exception e) {
			throw new RuntimeException("토큰이 유효하지 않습니다.", e);
		}
	}

	private byte[] decodeSecretKey(String secretKey) {
		return Base64.getDecoder().decode(secretKey);
	}
}
