package dev.caobaoqi6040.ai.infrastructure;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

/**
 * GlobalExceptionHandler
 *
 * @author caobaoqi6040
 * @since 2025/9/19 17:03
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({Exception.class})
	public ProblemDetail handlerException(Exception ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		problemDetail.setTitle("服务器发生异常");
		problemDetail.setType(URI.create("https://www.starlinktd.com"));
		return problemDetail;
	}

	@ExceptionHandler({JWTVerificationException.class})
	public ProblemDetail handlerJWTVerificationException(JWTVerificationException ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		problemDetail.setTitle("服务器发生异常");
		problemDetail.setType(URI.create("https://www.starlinktd.com"));
		return problemDetail;
	}

}
