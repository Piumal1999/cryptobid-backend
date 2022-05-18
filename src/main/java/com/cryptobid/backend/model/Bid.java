package com.cryptobid.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@JsonIgnoreProperties("auction")
public class Bid {

	@Id
	@GeneratedValue
	@Column(nullable = false)
	private int id;

	@Column(nullable = false)
	private float amount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date time;

	@ManyToOne(optional = false)
	private User bidBy;

	@ManyToOne(optional = false)
	private Auction auction;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public User getBidBy() {
		return bidBy;
	}

	public void setBidBy(User bidBy) {
		this.bidBy = bidBy;
	}

	public Auction getAuction() {
		return auction;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
	}
}
