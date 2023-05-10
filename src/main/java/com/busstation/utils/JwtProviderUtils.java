package com.busstation.utils;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.busstation.entities.Account;
import com.busstation.entities.Token;
import com.busstation.enums.TokenEnum;
import com.busstation.repositories.TokenRepository;

import java.util.Date;

@Component
@Slf4j
public class JwtProviderUtils {

	@Autowired
	private TokenRepository tokenRepository;

	@Value("${busstation.app.jwtSecret}")
	private String jwtSecret;

	@Value("${busstation.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateTokenUsingUserName(String userName) {
		String token = Jwts.builder().setSubject(userName)
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
		return token;
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException ex) {
			log.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			log.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty.");
		}
		return false;
	}

	public void saveUserToken(Account account, String jwtToken) {
		Token token = new Token();
		token.setAccount(account);
		token.setToken(jwtToken);
		token.setExpired(false);
		token.setRevoked(false);
		token.setTokenType(TokenEnum.BEARER);
		tokenRepository.save(token);
	}

	public void revokeAllUserTokens(Account account) {
		var validUserTokens = tokenRepository.findAllValidTokenByUser(account.getAccountId());
		if (validUserTokens.isEmpty())
			return;
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});
		tokenRepository.saveAll(validUserTokens);
	}

}