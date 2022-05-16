package com.cryptobid.backend.controller;

import com.cryptobid.backend.exceptions.ResourceNotFoundException;
import com.cryptobid.backend.model.User;
import com.cryptobid.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me")
public class IntrospectionController {
	private final UserService userService;

	public IntrospectionController(UserService userService ) {
		this.userService = userService;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public User getUserById(@AuthenticationPrincipal User user) throws ResourceNotFoundException {
		return userService.getUserById(user.getId());
	}
}
