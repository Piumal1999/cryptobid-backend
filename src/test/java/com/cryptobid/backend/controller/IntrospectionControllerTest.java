package com.cryptobid.backend.controller;

import com.cryptobid.backend.controller.admin.AuctionController;
import com.cryptobid.backend.exceptions.ResourceNotFoundException;
import com.cryptobid.backend.model.Auction;
import com.cryptobid.backend.model.User;
import com.cryptobid.backend.service.AuctionService;
import com.cryptobid.backend.service.UserService;
import com.cryptobid.backend.util.AuctionStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IntrospectionController.class)
public class IntrospectionControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private UserService userService;
	private final Integer userId = 1;
	private final User user = new User();

	@Test
	void getUserById_withValidData_thenReturns200() throws Exception {
		mockMvc.perform(get("/api/me")
						.cookie(new Cookie("userId","1")))
				.andExpect(status().isOk());
	}

	@Test
	void getUserById_withUnavailableData_thenReturns404() throws Exception {
		doThrow(ResourceNotFoundException.class)
				.when(userService)
				.getUserById(anyInt());

		mockMvc.perform(get("/api/me")
						.cookie(new Cookie("userId","1")))
				.andExpect(status().isNotFound());
	}

}
