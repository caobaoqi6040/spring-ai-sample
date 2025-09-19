package dev.caobaoqi6040.ai.modules.user.domain.request;

/**
 * UserLoginVo
 *
 * @author caobaoqi6040
 * @since 2025/9/19 16:31
 */
public record UserLoginRequestVo(
	String email,
	String password
) {
}
