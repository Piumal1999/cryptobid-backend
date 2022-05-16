package com.cryptobid.backend.repository;

import com.cryptobid.backend.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {

}
