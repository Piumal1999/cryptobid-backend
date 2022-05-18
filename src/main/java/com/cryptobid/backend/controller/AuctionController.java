package com.cryptobid.backend.controller;

import com.cryptobid.backend.exceptions.BadRequestException;
import com.cryptobid.backend.exceptions.ResourceNotFoundException;
import com.cryptobid.backend.model.Auction;
import com.cryptobid.backend.model.Bid;
import com.cryptobid.backend.model.User;
import com.cryptobid.backend.service.AuctionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {
	private final AuctionService auctionService;

	public AuctionController(AuctionService auctionService) {
		this.auctionService = auctionService;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Auction> getAllAuctions() {
		return auctionService.getAllAuctions();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Auction getAuctionById(@PathVariable int id) throws ResourceNotFoundException {
		return auctionService.getAuctionById(id);
	}

	@GetMapping("/{id}/bids")
	@ResponseStatus(HttpStatus.OK)
	public List<Bid> getMyBidsByAuctionId(@PathVariable int id, @CookieValue int userId)
			throws ResourceNotFoundException {
		return auctionService.getMyBidsByAuctionId(id, userId);
	}

	@DeleteMapping("/{auctionId}/bids/{bidId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelBidById(@PathVariable int auctionId, @PathVariable int bidId, @CookieValue int userId)
			throws ResourceNotFoundException, BadRequestException {
		auctionService.cancelBidById(auctionId, bidId, userId);
	}

	@PostMapping("/{id}/bids")
	@ResponseStatus(HttpStatus.CREATED)
	public Bid createBid(@PathVariable int id, @RequestBody Bid bid, @CookieValue int userId) {
		return auctionService.createBid(id, bid, userId);
	}
}
