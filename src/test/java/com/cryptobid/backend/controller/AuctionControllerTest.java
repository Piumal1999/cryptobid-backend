package com.cryptobid.backend.controller;

import com.cryptobid.backend.exceptions.ResourceNotFoundException;
import com.cryptobid.backend.model.Auction;
import com.cryptobid.backend.model.Bid;
import com.cryptobid.backend.service.AuctionService;
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
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuctionController.class)
public class AuctionControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private AuctionService auctionService;
	private final Bid bid = new Bid();
	private final Integer auctionId = 1;

	@Test
	void getAllAuctions_withValidData_thenReturns200() throws Exception {
		mockMvc.perform(get("/api/auctions"))
				.andExpect(status().isOk());
	}

	@Test
	void getAuctionById_withValidData_thenReturns200() throws Exception {
		mockMvc.perform(get("/api/auctions/{id}",auctionId))
				.andExpect(status().isOk());
	}
	@Test
	void getAuctionById_withUnavailableData_thenReturns404() throws Exception {
		doThrow(ResourceNotFoundException.class)
				.when(auctionService)
				.getAuctionById(anyInt());

		mockMvc.perform(get("/api/auctions/{id}",auctionId))
				.andExpect(status().isNotFound());
	}

	@Test
	void getMyBidsByAuctionId_withValidData_thenReturns200() throws Exception {
		mockMvc.perform(get("/api/auctions/{id}/bids",auctionId)
				.cookie(new Cookie("userId","1")))
				.andExpect(status().isOk());
	}
	@Test
	void getMyBidsByAuctionId_withUnavailableData_thenReturns404() throws Exception {
		doThrow(ResourceNotFoundException.class)
				.when(auctionService)
				.getMyBidsByAuctionId(anyInt(),anyInt());

		mockMvc.perform(get("/api/auctions/{id}/bids",auctionId)
				.cookie(new Cookie("userId","1")))
				.andExpect(status().isNotFound());
	}

	@Test
	void createBid_withValidData_thenReturns201() throws Exception {
		mockMvc.perform(post("/api/auctions/{id}/bids")
				.cookie(new Cookie("userId", "1"))
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(bid)))
				.andExpect(status().isCreated());
	}

	@Test
	void createBid_withValidData_thenReturnsValidResponseBody() throws Exception {
		mockMvc.perform(post("/api/auctions/{id}/bids")
				.cookie(new Cookie("userId", "1"))
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(bid)))
				.andReturn();

		ArgumentCaptor<Bid> programCaptor = ArgumentCaptor.forClass(Bid.class);
		verify(auctionService, times(1)).createBid(anyInt(), programCaptor.capture(), anyInt());

		String expectedResponse = objectMapper.writeValueAsString(bid);
		String actualResponse = objectMapper.writeValueAsString(programCaptor.getValue());
		assertThat(actualResponse).isEqualTo(expectedResponse);
	}



}
