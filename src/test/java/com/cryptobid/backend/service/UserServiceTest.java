package com.cryptobid.backend.service;

import com.cryptobid.backend.exceptions.ResourceNotFoundException;
import com.cryptobid.backend.repository.CryptocurrencyRepository;
import com.cryptobid.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	private final Integer userId = 1;

	@Test
	void getCryptocurrencyById_withUnavailableData_thenThrowResourceNotFound() {
		doReturn(Optional.empty())
				.when(userRepository)
				.findById(anyInt());

		Throwable thrown = catchThrowable(
				() -> userService.getUserById(userId));
		assertThat(thrown)
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Error, User by id: 1 doesn't exist.");
	}
}
