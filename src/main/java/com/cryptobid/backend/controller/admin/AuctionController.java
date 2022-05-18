package com.cryptobid.backend.controller.admin;

import com.cryptobid.backend.exceptions.ResourceNotFoundException;
import com.cryptobid.backend.model.Auction;
import com.cryptobid.backend.model.Bid;
import com.cryptobid.backend.model.User;
import com.cryptobid.backend.service.AuctionService;
import com.cryptobid.backend.util.AuctionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("AdminAuctionController")
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

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Auction startAuction(@RequestBody Auction auction,
								@CookieValue int userId) {
		return auctionService.startAuction(auction, userId);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Auction endOrCancelAuction(@PathVariable int id,
									  @RequestParam AuctionStatus auctionStatus) throws ResourceNotFoundException {
		return auctionService.endOrCancelAuction(id, auctionStatus);
	}
}
