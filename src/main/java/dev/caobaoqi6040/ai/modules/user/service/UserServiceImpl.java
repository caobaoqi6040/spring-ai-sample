package dev.caobaoqi6040.ai.modules.user.service;

import dev.caobaoqi6040.ai.modules.user.exception.UserNotFoundException;
import dev.caobaoqi6040.ai.modules.user.model.User;
import dev.caobaoqi6040.ai.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserServiceImpl
 *
 * @author caobaoqi6040
 * @since 2025/9/19 16:20
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository repository;

	@Override
	public List<User> loadAllUser() {
		return repository.findAll();
	}

	@Override
	public User loadUserByEmail(String email) {

		return repository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException(String.format("user not found with %s", email)));

	}

}
