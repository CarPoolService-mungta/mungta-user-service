package com.mungta.user.service;

import com.mungta.user.dto.Token;
import com.mungta.user.model.UserEntity;
import java.util.Base64;
import java.util.Date;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
// import lombok.extern.slf4j.Slf4j;

// @Slf4j
@Service
public class TokenProvider {

	private static final long ACCESS_TOKEN_EXPIRE_TIME  = 1000 * 60 * 30;            // 0.5 hr
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;   // 7 days

	private static final String secretKey = "MungTa03Service";
	String encodedKey  = Base64.getEncoder().encodeToString(secretKey.getBytes());

	public Token createToken (UserEntity user) {

		Date now = new Date();

		Claims claims = Jwts.claims()
		                    .setIssuer("mungtacarpool.com")
												.setSubject(user.getUserId());

		//claims for refresh
		claims.put("userId"  ,user.getUserId());

		String refreshToken =  Jwts.builder()
															 .signWith(SignatureAlgorithm.HS256, encodedKey)
															 .setHeaderParam("typ", "JWT")
															 .setClaims(claims)
															 .setIssuedAt(now)
															 .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
															 .compact();

		//claims extra for access
		claims.put("name"    ,user.getUserName());
		claims.put("email"   ,user.getUserMailAddress());
		claims.put("team"    ,user.getUserTeamName());
		claims.put("userType",user.getUserType());
		claims.put("driverYn",user.getDriverYn());

		String accessToken  =  Jwts.builder()
															 .signWith(SignatureAlgorithm.HS256,encodedKey)
															 .setHeaderParam("typ", "JWT")
															 .setClaims(claims)
															 .setIssuedAt(now)
															 .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
															 .compact();
	 return Token.builder().accessToken(accessToken).refreshToken(refreshToken).key(user.getUserId()).build();
	}
}

