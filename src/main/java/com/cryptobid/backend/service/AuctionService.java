package com.cryptobid.backend.service;

import com.cryptobid.backend.exceptions.ResourceNotFoundException;
import com.cryptobid.backend.model.Auction;
import com.cryptobid.backend.model.Bid;
import com.cryptobid.backend.repository.AuctionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {

	private final static Logger log = LoggerFactory.getLogger(AuctionService.class);
	private final AuctionRepository auctionRepository;

	public AuctionService(AuctionRepository auctionRepository) {
		this.auctionRepository = auctionRepository;
	}

	/**
	 * Retrieves all the {@link Auction} objects
	 *
	 * @return {@link List} of {@link Auction} objects
	 */
	public List<Auction> getAllAuctions() {
		return auctionRepository.findAll();
	}

	/**
	 * Retrieves the {@link Auction} filtered from {@code id}
	 *
	 * @param id which is the id of the filtering {@link Auction}
	 * @return {@link Auction}
	 *
	 * @throws ResourceNotFoundException if the requesting {@link Auction} doesn't exist
	 */
	public Auction getAuctionById(int id) throws ResourceNotFoundException {
		Optional<Auction> auction = auctionRepository.findById(id);
		if (auction.isEmpty()) {
			String msg = "Error, Auction by id: " + id + " doesn't exist.";
			log.error(msg);
			throw new ResourceNotFoundException(msg);
		}
		return auction.get();
	}
	/**
	 * Retrieves all the {@link Bid} objects filtered from {@link Auction}
	 *
	 * @param id which is the id of the filtering {@link Auction}
	 * @return {@link List} of {@link Bid} objects
	 *
	 * @throws ResourceNotFoundException if the filtering {@link Auction} doesn't exist
	 */
	public List<Bid> getBidsByAuctionId(int id) throws ResourceNotFoundException {
		Optional<Auction> auction = auctionRepository.findById(id);
		if (auction.isEmpty()) {
			String msg = "Error, Auction by id: " + id + " doesn't exist.";
			log.error(msg);
			throw new ResourceNotFoundException(msg);
		}
		return auction.get().getBids();
	}

}
