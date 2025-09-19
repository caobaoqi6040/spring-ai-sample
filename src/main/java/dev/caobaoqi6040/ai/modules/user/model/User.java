package dev.caobaoqi6040.ai.modules.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * User
 *
 * @author caobaoqi6040
 * @since 2025/9/19 16:13
 */
@Getter
@Setter
@Entity
@Table(schema = "bacnend", name = "sys_user")
public class User {

	@Id
	private Long id;
	@Column(name = "username")
	private String username;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;

}
