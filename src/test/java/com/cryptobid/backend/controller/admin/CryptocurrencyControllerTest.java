package com.cryptobid.backend.controller.admin;

import com.cryptobid.backend.exceptions.ResourceNotFoundException;
import com.cryptobid.backend.service.AuctionService;
import com.cryptobid.backend.service.CryptocurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CryptocurrencyController.class)
public class CryptocurrencyControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private CryptocurrencyService cryptocurrencyService;

	private final Integer cryptocurrencyId = 1;

	@Test
	void getAllCryptocurrencies_withValidData_thenReturns200() throws Exception {
		mockMvc.perform(get("/api/admin/cryptocurrency"))
				.andExpect(status().isOk());
	}

	@Test
	void getCryptocurrencyById_withValidData_thenReturns200() throws Exception {
		doThrow(ResourceNotFoundException.class)
				.when(cryptocurrencyService)
				.getCryptocurrencyById(anyInt());

		mockMvc.perform(get("/api/admin/cryptocurrency/{id}", cryptocurrencyId))
				.andExpect(status().isNotFound());
	}

	@Test
	void getCryptocurrencyById_withUnavailableData_thenReturns404() throws Exception {
		doThrow(ResourceNotFoundException.class)
				.when(cryptocurrencyService)
				.getCryptocurrencyById(anyInt());

		mockMvc.perform(get("/api/admin/cryptocurrency/{id}", cryptocurrencyId))
				.andExpect(status().isNotFound());
	}

}
