package com.cryptobid.backend.model;

import com.cryptobid.backend.util.UserType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column
	private float totalBalance;

	@Enumerated(EnumType.STRING)
	@Column(length = 8, nullable = false)
	private UserType type;
}
