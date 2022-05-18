package com.cryptobid.backend.controller.admin;

import com.cryptobid.backend.exceptions.ResourceNotFoundException;
import com.cryptobid.backend.service.AuctionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuctionController.class)
public class AuctionControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private AuctionService auctionService;
	private final Integer auctionId = 1;

	@Test
	void getBidsByAuctionId_withValidData_thenReturns200() throws Exception {
		mockMvc.perform(get("/api/admin/auctions/{id}/bids", auctionId))
				.andExpect(status().isOk());
	}

	@Test
	void getBidsByAuctionId_withUnavailableData_thenReturns404() throws Exception {
		doThrow(ResourceNotFoundException.class)
				.when(auctionService)
				.getBidsByAuctionId(anyInt());

		mockMvc.perform(get("/api/admin/auctions/{id}/bids", auctionId))
				.andExpect(status().isNotFound());
	}

}
