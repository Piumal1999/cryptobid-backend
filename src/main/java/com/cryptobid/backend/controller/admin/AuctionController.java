package com.cryptobid.backend.controller.admin;

import com.cryptobid.backend.exceptions.ResourceNotFoundException;
import com.cryptobid.backend.model.Bid;
import com.cryptobid.backend.service.AuctionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/auctions")
public class AuctionController {
	private final AuctionService auctionService;

	public AuctionController(AuctionService auctionService) {
		this.auctionService = auctionService;
	}

	@GetMapping("/{id}/bids")
	@ResponseStatus(HttpStatus.OK)
	public List<Bid> getBidsByAuctionId(@PathVariable int id) throws ResourceNotFoundException {
		return auctionService.getBidsByAuctionId(id);
	}
}
