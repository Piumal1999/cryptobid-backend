package com.cryptobid.backend.repository;

import com.cryptobid.backend.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Integer> {
	public Bid getTopByAuctionIdOrderByAmountDesc(int id);
}
