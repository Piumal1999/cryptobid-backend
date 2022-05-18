package com.cryptobid.backend.service;

import com.cryptobid.backend.exceptions.BadRequestException;
import com.cryptobid.backend.exceptions.ResourceNotFoundException;
import com.cryptobid.backend.model.Auction;
import com.cryptobid.backend.model.Bid;
import com.cryptobid.backend.model.User;
import com.cryptobid.backend.repository.AuctionRepository;
import com.cryptobid.backend.repository.BidRepository;
import com.cryptobid.backend.repository.UserRepository;
import com.cryptobid.backend.util.AuctionStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class AuctionServiceTest {
	@Mock
	private AuctionRepository auctionRepository;
	@Mock
	private BidRepository bidRepository;
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private AuctionService auctionService;

	private final Integer auctionId = 1;
	private final Integer userId = 1;
	private final Integer bidId = 1;

	@Test
	void getAuctionById_withUnavailableData_thenThrowResourceNotFound() {
		doReturn(Optional.empty())
				.when(auctionRepository)
				.findById(anyInt());

		Throwable thrown = catchThrowable(
				() -> auctionService.getAuctionById(auctionId));
		assertThat(thrown)
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Error, Auction by id: 1 doesn't exist.");
	}

	@Test
	void getBidsByAuctionId_withUnavailableData_thenThrowResourceNotFound() {
		doReturn(Optional.empty())
				.when(auctionRepository)
				.findById(anyInt());

		Throwable thrown = catchThrowable(
				() -> auctionService.getBidsByAuctionId(auctionId));
		assertThat(thrown)
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Error, Auction by id: 1 doesn't exist.");
	}

	@Test
	void endOrCancelAuction_withValidData_thenReturnUpdatedData()
			throws ResourceNotFoundException {
		final Auction auction = new Auction();

		doReturn(Optional.of(auction))
				.when(auctionRepository)
				.findById(anyInt());

		Auction savedAuction = auctionService.endOrCancelAuction(auctionId, AuctionStatus.CANCELLED);
		assertThat(savedAuction).isNotNull();
		assertThat(savedAuction.getStatus()).isEqualTo(AuctionStatus.CANCELLED);
	}

	@Test
	void endOrCancelAuction_withUnavailableData_thenThrowResourceNotFound() {
		doReturn(Optional.empty())
				.when(auctionRepository)
				.findById(anyInt());

		Throwable thrown = catchThrowable(
				() -> auctionService.endOrCancelAuction(auctionId, AuctionStatus.CANCELLED));
		assertThat(thrown)
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Error, Auction by id: 1 doesn't exist.");
	}

	@Test
	void getMyBidsByAuctionId_withUnavailableData_thenThrowResourceNotFound() {
		doReturn(false)
				.when(auctionRepository)
				.existsById(anyInt());

		Throwable thrown = catchThrowable(
				() -> auctionService.getMyBidsByAuctionId(auctionId, userId));
		assertThat(thrown)
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Error, Auction by id: 1 doesn't exist.");
	}

	@Test
	void cancelBidById_withUnavailableBid_thenThrowResourceNotFound() {
		doReturn(Optional.empty())
				.when(bidRepository)
				.findById(anyInt());

		Throwable thrown = catchThrowable(
				() -> auctionService.cancelBidById(auctionId, bidId, userId));
		assertThat(thrown)
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Error, Bid by id: 1 doesn't exist.");
	}

	@Test
	void cancelBidById_withInvalidAuction_thenThrowBadRequest() {
		Bid bid = new Bid();
		Auction auction = new Auction();
		auction.setId(2);
		bid.setAuction(auction);
		doReturn(Optional.of(bid))
				.when(bidRepository)
				.findById(anyInt());

		Throwable thrown = catchThrowable(
				() -> auctionService.cancelBidById(auctionId, bidId, userId));
		assertThat(thrown)
				.isInstanceOf(BadRequestException.class)
				.hasMessage("Error, Invalid auction id.");
	}

	@Test
	void cancelBidById_withInvalidUser_thenThrowBadRequest() {
		Auction auction = new Auction();
		auction.setId(auctionId);
		User user = new User();
		user.setId(2);
		Bid bid = new Bid();
		bid.setAuction(auction);
		bid.setBidBy(user);

		doReturn(Optional.of(bid))
				.when(bidRepository)
				.findById(anyInt());

		Throwable thrown = catchThrowable(
				() -> auctionService.cancelBidById(auctionId, bidId, userId));
		assertThat(thrown)
				.isInstanceOf(BadRequestException.class)
				.hasMessage("Error, Invalid user id");
	}

	@Test
	void createBid_withValidData_thenReturnCreatedData() {
		final Bid bid = new Bid();
		bid.setAmount(1000);
		bid.setTime(new Date());
		User user = new User();
		user.setId(userId);
		Auction auction = new Auction();
		auction.setId(auctionId);

		doReturn(user)
				.when(userRepository)
				.getById(anyInt());
		doReturn(auction)
				.when(auctionRepository)
				.getById(anyInt());
		doReturn(bid)
				.when(bidRepository)
				.save(any(Bid.class));

		Bid savedBid = auctionService.createBid(auctionId, bid, userId);
		assertThat(savedBid).isNotNull();
	}
}
