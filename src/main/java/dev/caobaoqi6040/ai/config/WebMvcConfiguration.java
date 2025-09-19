package dev.caobaoqi6040.ai.config;

import dev.caobaoqi6040.ai.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfiguration
 *
 * @author caobaoqi6040
 * @since 2025/9/19 15:35
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	private final AuthInterceptor authInterceptor;

	public WebMvcConfiguration(AuthInterceptor authInterceptor) {
		this.authInterceptor = authInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor)
			.addPathPatterns(
				"/api/v1/ollama/**",
				"/api/v1/openai/**"
			);
	}

}
