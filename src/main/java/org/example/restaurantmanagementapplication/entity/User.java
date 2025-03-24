package org.example.restaurantmanagementapplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@NotNull(message = "E-mail cannot be empty")
	@Column(name = "email")
	private String email;

	@NotNull(message = "Password cannot be empty")
	@Column(name = "password")
	private String password;

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "role_id")
	private Collection<Role> roles;

	public User() {
		this.roles = new ArrayList<>();
	}

	public void addRole(Role role) {
		if (this.roles == null) {
			this.roles = new ArrayList<>();
		}
		roles.add(role);
	}

	public void removeRole(Role role) {
		roles.remove(role);
	}
}
