package com.cryptobid.backend.repository;

import com.cryptobid.backend.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Integer> {
	Bid getTopByAuctionIdOrderByAmountDesc(int id);
	List<Bid> getBidsByAuctionIdAndBidBy_Id(int id, int userId);
}
