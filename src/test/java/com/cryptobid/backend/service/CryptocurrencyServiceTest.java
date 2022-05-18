package com.cryptobid.backend.service;

import com.cryptobid.backend.exceptions.ResourceNotFoundException;
import com.cryptobid.backend.repository.CryptocurrencyRepository;
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
public class CryptocurrencyServiceTest {
	@Mock
	private CryptocurrencyRepository cryptocurrencyRepository;

	@InjectMocks
	private CryptocurrencyService cryptocurrencyService;

	private final Integer cryptocurrencyId = 1;

	@Test
	void getCryptocurrencyById_withUnavailableData_thenThrowResourceNotFound() {
		doReturn(Optional.empty())
				.when(cryptocurrencyRepository)
				.findById(anyInt());

		Throwable thrown = catchThrowable(
				() -> cryptocurrencyService.getCryptocurrencyById(cryptocurrencyId));
		assertThat(thrown)
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Error, Cryptocurrency by id: 1 doesn't exist.");
	}
}
