package com.cryptobid.backend.model;

import com.cryptobid.backend.util.UserType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class User {

	@Id
	@GeneratedValue
	@Column(nullable = false)
	private int id;

	@Column(nullable = false,unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column
	private float totalBalance;

	@Enumerated(EnumType.STRING)
	@Column(length = 8, nullable = false)
	private UserType type;

	@OneToMany(mappedBy = "startedBy")
	private List<Auction> startedAuctions;

	@OneToMany(mappedBy = "wonBy")
	private List<Auction> wonAuctions;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public float getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(float totalBalance) {
		this.totalBalance = totalBalance;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public List<Auction> getStartedAuctions() {
		return startedAuctions;
	}

	public void setStartedAuctions(List<Auction> startedAuctions) {
		this.startedAuctions = startedAuctions;
	}

	public List<Auction> getWonAuctions() {
		return wonAuctions;
	}

	public void setWonAuctions(List<Auction> wonAuctions) {
		this.wonAuctions = wonAuctions;
	}
}
