package dev.caobaoqi6040.ai.modules.user.repository;

import dev.caobaoqi6040.ai.modules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UserRepository
 *
 * @author caobaoqi6040
 * @since 2025/9/19 16:17
 */
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
