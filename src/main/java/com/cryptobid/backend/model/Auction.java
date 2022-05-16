package com.cryptobid.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Entity
public class Auction {

	@Id
	@GeneratedValue
	@Column(nullable = false)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date startTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date endTime;

	@Column(nullable = false)
	private float initialValue;

	@Column(nullable = false)
	private AuctionStatus status;

	@ManyToOne(optional = false)
	private Cryptocurrency cryptocurrency;

	@ManyToOne(optional = false)
	private User startedBy;

	@ManyToOne
	private User wonBy;

	@OneToMany
	private List<Bid> bids;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public float getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(float initialValue) {
		this.initialValue = initialValue;
	}

	public AuctionStatus getStatus() {
		return status;
	}

	public void setStatus(AuctionStatus status) {
		this.status = status;
	}

	public Cryptocurrency getCryptocurrency() {
		return cryptocurrency;
	}

	public void setCryptocurrency(Cryptocurrency cryptocurrency) {
		this.cryptocurrency = cryptocurrency;
	}

	public User getStartedBy() {
		return startedBy;
	}

	public void setStartedBy(User startedBy) {
		this.startedBy = startedBy;
	}

	public User getWonBy() {
		return wonBy;
	}

	public void setWonBy(User wonBy) {
		this.wonBy = wonBy;
	}

	public List<Bid> getBids() {
		return bids;
	}

	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}
}
