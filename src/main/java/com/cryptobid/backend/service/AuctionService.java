package com.cryptobid.backend.service;

import com.cryptobid.backend.exceptions.ResourceNotFoundException;
import com.cryptobid.backend.model.Auction;
import com.cryptobid.backend.model.Bid;
import com.cryptobid.backend.repository.AuctionRepository;
import com.cryptobid.backend.repository.BidRepository;
import com.cryptobid.backend.util.AuctionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {

	private final static Logger log = LoggerFactory.getLogger(AuctionService.class);
	private final AuctionRepository auctionRepository;
	private final BidRepository bidRepository;

	public AuctionService(AuctionRepository auctionRepository, BidRepository bidRepository) {
		this.auctionRepository = auctionRepository;
		this.bidRepository = bidRepository;
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

	/**
	 * End Or Cancel an {@link Auction} object filtered by {@code id}
	 *
	 * @param id which is the id of the filtering {@link Auction}
	 * @return the {@link Auction} object
	 * @throws ResourceNotFoundException if the filtering {@link Auction} doesn't exist
	 */
	public Auction endOrCancelAuction(int id, AuctionStatus auctionStatus) throws ResourceNotFoundException {
		Optional<Auction> auction = auctionRepository.findById(id);
		if (auction.isEmpty()) {
			String msg = "Error, Auction by id: " + id + " doesn't exist.";
			log.error(msg);
			throw new ResourceNotFoundException(msg);
		}

		if (auctionStatus.equals(AuctionStatus.COMPLETED)) {
			Bid winningBid = bidRepository.getTopByAuctionIdOrderByAmountDesc(id);
			auction.get().setWonBy(winningBid.getBidBy());
			winningBid.getBidBy().setTotalBalance(winningBid.getBidBy().getTotalBalance() - winningBid.getAmount());
			auction.get().setStatus(AuctionStatus.COMPLETED);
			auction.get().setEndTime(new Date());
		} else if (auctionStatus.equals(AuctionStatus.CANCELLED)) {
			auction.get().setStatus(AuctionStatus.CANCELLED);
			auction.get().setEndTime(new Date());
		}
		return auction.get();
	}

	/**
	 * Get the bids of logged in {@code User} for an {@link Auction}
	 *
	 * @param id which is the id of the filtering {@link Auction}
	 * @param userId which is the id of the logged in user
	 * @return the {@link List} of {@link Bid} objects
	 * @throws ResourceNotFoundException if the filtering {@link Auction} doesn't exist
	 */
	public List<Bid> getMyBidsByAuctionId(int id, int userId) throws ResourceNotFoundException {
		if (!auctionRepository.existsById(id)) {
			String msg = "Error, Auction by id: " + id + " doesn't exist.";
			log.error(msg);
			throw new ResourceNotFoundException(msg);
		}
		return bidRepository.getBidsByAuctionIdAndBidBy_Id(id, userId);
	}

}
