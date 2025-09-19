package dev.caobaoqi6040.ai.interceptor;

import dev.caobaoqi6040.ai.modules.auth.TokenTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * AuthInterceptor
 *
 * @author caobaoqi6040
 * @since 2025/9/19 15:36
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

	public static final String TOKEN_CACHED_FLAG = "auth::token";

	private final TokenTemplate tokenTemplate;
	private final StringRedisTemplate redisTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		String token = request.getHeader("Authorization").substring(7);
		if (StringUtils.isEmpty(token)) {
			log.warn("no header named Authorization ");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return false;
		}
		if (!ObjectUtils.allNotNull(tokenTemplate.verifyToken(token))) {
			log.warn("token verify failure");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return false;
		}
		Map<String, String> map = tokenTemplate.parseToken(token);
		String userEmail = map.get(TokenTemplate.USER_EMAIL);
		String cached_token = redisTemplate.opsForValue().get(String.format("%s:%s", TOKEN_CACHED_FLAG, userEmail));
		if (ObjectUtils.allNull(cached_token)) {
			log.warn("登录状态失效");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return false;
		}
		if (StringUtils.equals(cached_token, token)) {
			log.info("{} 认证成功", userEmail);
			return true;
		}
		return true;
	}

}
