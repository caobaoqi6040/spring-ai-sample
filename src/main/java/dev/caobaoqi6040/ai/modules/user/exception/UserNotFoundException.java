package dev.caobaoqi6040.ai.modules.user.exception;

/**
 * UserNotFoundException
 *
 * @author caobaoqi6040
 * @since 2025/9/19 16:22
 */
public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String message) {
		super(message);
	}
}
