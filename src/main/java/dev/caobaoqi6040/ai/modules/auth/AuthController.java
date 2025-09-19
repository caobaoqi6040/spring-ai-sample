package dev.caobaoqi6040.ai.modules.auth;

import dev.caobaoqi6040.ai.interceptor.AuthInterceptor;
import dev.caobaoqi6040.ai.modules.user.domain.request.UserLoginRequestVo;
import dev.caobaoqi6040.ai.modules.user.domain.response.UserLoginResponseVo;
import dev.caobaoqi6040.ai.modules.user.exception.UserNotFoundException;
import dev.caobaoqi6040.ai.modules.user.model.User;
import dev.caobaoqi6040.ai.modules.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

/**
 * AuthController
 *
 * @author caobaoqi6040
 * @since 2025/9/19 16:11
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final UserService service;
	private final TokenTemplate tokenTemplate;
	private final StringRedisTemplate redisTemplate;

	public AuthController(UserService service, TokenTemplate tokenTemplate, StringRedisTemplate redisTemplate) {
		this.service = service;
		this.tokenTemplate = tokenTemplate;
		this.redisTemplate = redisTemplate;
	}

	@GetMapping("/sign-in")
	public ResponseEntity<UserLoginResponseVo> signIn(@RequestBody UserLoginRequestVo vo) {

		User user = service.loadUserByEmail(vo.email());
		if (StringUtils.equals(vo.password(), user.getPassword())) {
			String token = tokenTemplate.generateToken(String.valueOf(user.getId()), user.getEmail());
			redisTemplate.opsForValue().set(
				String.format("%s:%s", AuthInterceptor.TOKEN_CACHED_FLAG, vo.email()),
				token,
				Duration.ofHours(24 * 7)
			);
			return ResponseEntity.ok(new UserLoginResponseVo(token));
		}
		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler({UserNotFoundException.class})
	public ResponseEntity<String> handlerUserNotFoundException(UserNotFoundException ex) {
		log.warn(ex.getLocalizedMessage(), ex);
		return ResponseEntity.notFound().build();
	}
}
