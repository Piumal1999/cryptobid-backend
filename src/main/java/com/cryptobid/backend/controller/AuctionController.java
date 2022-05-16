package com.cryptobid.backend.controller;

import com.cryptobid.backend.exceptions.ResourceNotFoundException;
import com.cryptobid.backend.model.Auction;
import com.cryptobid.backend.model.Bid;
import com.cryptobid.backend.model.User;
import com.cryptobid.backend.service.AuctionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public List<Bid> getMyBidsByAuctionId(@PathVariable int id, @AuthenticationPrincipal User user)
			throws ResourceNotFoundException {
		return auctionService.getMyBidsByAuctionId(id, user.getId());
	}

	@DeleteMapping("/{auctionId}/bids/{bidId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelBidById(@PathVariable int auctionId, @PathVariable int bidId, @AuthenticationPrincipal User user)
			throws ResourceNotFoundException {
		auctionService.cancelBidById(auctionId, bidId, user.getId());
	}
}
