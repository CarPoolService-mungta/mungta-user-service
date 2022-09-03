package com.mungta.user.auth;

import com.mungta.user.dto.UserLoginDto;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {

	private static final long ACCESS_TOKEN_EXPIRE_TIME  = 1000 * 60 * 30;            // 0.5 hr
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;   // 7 days

	private static final String secretKey = "MungTa03Service";
	String encodedKey  = Base64.getEncoder().encodeToString(secretKey.getBytes());

	//byte[] decodedBytes   = Base64.getDecoder().decode(encodedKey);
	//String decodedString  = new String(decodedBytes);

	public String create(UserLoginDto user) {

		log.debug("secretKey",secretKey);
		log.debug("encodeToString",secretKey.getBytes());
		log.debug("encodedString",encodedKey);

		Date now = new Date();

		Claims claims = Jwts.claims()
		                    .setIssuer("mungtacarpool.com")
												.setSubject(user.getUserId())
												.setAudience(user.getUserId());

		////ID, 이름, 고객유형 ,아이디, 드라이버여부 ->서브젝트
		claims.put("userId"  ,user.getUserId());
	  //claims.put("name","testkimmy");
		//claims.put("custType","ADMIN");

		String accessToken  =  Jwts.builder()
															 .signWith(SignatureAlgorithm.HS256,encodedKey)
															 .setHeaderParam("typ", "JWT")
															 .setClaims(claims)
															 .setIssuedAt(now)
															 .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
															 .compact();

		String refreshToken =  Jwts.builder()
															 .signWith(SignatureAlgorithm.HS256, encodedKey)
															 .setHeaderParam("typ", "JWT")
															 .setClaims(claims) // 정보 저장
															 .setIssuedAt(now) // 토큰 발행 시간 정보
															 .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
															 .compact();

		return accessToken;
	 // return Token.builder().accessToken(accessToken).refreshToken(refreshToken).key(user.getUserId()).build();
	}

	public String validateAndGetUserId(String token) {
		Claims claims = Jwts.parser()
												.setSigningKey(encodedKey)
												.parseClaimsJws(token)
												.getBody();

		return claims.getSubject();
	}
}

