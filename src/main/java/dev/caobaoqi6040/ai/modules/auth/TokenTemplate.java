package dev.caobaoqi6040.ai.modules.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * TokenTemplate
 *
 * @author caobaoqi6040
 * @since 2025/9/19 15:43
 */
@Slf4j
@Setter
@Component
@ConfigurationProperties(prefix = "security")
public class TokenTemplate {

	private String secretKey;

	public static final String USER_ID = "user-id";
	public static final String USER_EMAIL = "user-email";

	public String generateToken(String identifier, String email) {
		return JWT
			.create()
			.withClaim(USER_ID, identifier)
			.withClaim(USER_EMAIL, email)
			.sign(Algorithm.HMAC256(secretKey));
	}

	public DecodedJWT verifyToken(String token) {
		try {
			return JWT.require(Algorithm.HMAC256(secretKey))
				.build().verify(token);
		} catch (JWTVerificationException ex) {
			log.warn("token 解析失败", ex);
			throw new JWTVerificationException("token 不合法");
		}
	}

	public Map<String, String> parseToken(String token) {
		HashMap<String, String> map = new HashMap<>();
		DecodedJWT decodedJWT = this.verifyToken(token);
		Claim userId = decodedJWT.getClaim(USER_ID);
		Claim userEmail = decodedJWT.getClaim(USER_EMAIL);
		map.put(USER_ID, userId.asString());
		map.put(USER_EMAIL, userEmail.asString());
		return map;
	}


}
