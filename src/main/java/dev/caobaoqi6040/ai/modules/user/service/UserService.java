package dev.caobaoqi6040.ai.modules.user.service;

import dev.caobaoqi6040.ai.modules.user.model.User;

import java.util.List;

/**
 * UserService
 *
 * @author caobaoqi6040
 * @since 2025/9/19 16:19
 */
public interface UserService {
	List<User> loadAllUser();
	User loadUserByEmail(String email);
}
