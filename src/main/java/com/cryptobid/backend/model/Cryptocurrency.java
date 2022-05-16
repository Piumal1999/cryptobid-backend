package com.cryptobid.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cryptocurrency {

	@Id
	@GeneratedValue
	@Column(nullable = false)
	private int id;

	@Column(nullable = false, unique = true)
	private String symbol;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private int cryptoRank;

	@OneToMany(mappedBy = "cryptocurrency")
	private List<Auction> auctions  = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCryptoRank() {
		return cryptoRank;
	}

	public void setCryptoRank(int cryptoRank) {
		this.cryptoRank = cryptoRank;
	}

	public List<Auction> getAuctions() {
		return auctions;
	}

	public void setAuctions(List<Auction> auctions) {
		this.auctions = auctions;
	}
}
