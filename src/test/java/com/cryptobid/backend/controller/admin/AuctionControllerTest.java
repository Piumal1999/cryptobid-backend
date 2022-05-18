package com.cryptobid.backend.controller.admin;

import com.cryptobid.backend.exceptions.ResourceNotFoundException;
import com.cryptobid.backend.model.Auction;
import com.cryptobid.backend.service.AuctionService;
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

@WebMvcTest(AuctionController.class)
public class AuctionControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private AuctionService auctionService;
	private final Integer auctionId = 1;
	private final Auction auction = new Auction();

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

	@Test
	void startAuction_withValidData_thenReturns201() throws Exception {
		mockMvc.perform(post("/api/admin/auctions")
						.cookie(new Cookie("userId", "1"))
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(auction)))
				.andExpect(status().isCreated());
	}

	@Test
	void startAuction_withValidData_thenReturnsValidResponseBody() throws Exception {
		mockMvc.perform(post("/api/admin/auctions")
						.cookie(new Cookie("userId", "1"))
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(auction)))
				.andReturn();

		ArgumentCaptor<Auction> programCaptor = ArgumentCaptor.forClass(Auction.class);
		verify(auctionService, times(1)).startAuction(programCaptor.capture(), anyInt());

		String expectedResponse = objectMapper.writeValueAsString(auction);
		String actualResponse = objectMapper.writeValueAsString(programCaptor.getValue());
		assertThat(actualResponse).isEqualTo(expectedResponse);
	}

	@Test
	void endOrCancelAuction_withValidData_thenReturns200() throws Exception {
		auction.setStatus(AuctionStatus.COMPLETED);
		mockMvc.perform(put("/api/admin/auctions/{id}?auctionStatus=COMPLETED", auctionId)
						.param("auctionStatus", AuctionStatus.COMPLETED.toString()))
				.andExpect(status().isOk());
	}

	@Test
	void endOrCancelAuction_withUnavailableData_thenReturn404() throws Exception {
		auction.setStatus(AuctionStatus.COMPLETED);
		doThrow(ResourceNotFoundException.class)
				.when(auctionService)
				.endOrCancelAuction(anyInt(), any(AuctionStatus.class));

		mockMvc.perform(put("/api/admin/auctions/{id}", auctionId)
						.param("auctionStatus", AuctionStatus.COMPLETED.toString()))
				.andExpect(status().isNotFound());
	}

}
