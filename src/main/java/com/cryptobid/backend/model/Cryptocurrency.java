package com.cryptobid.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cryptocurrency {

	@Id
	@Column(nullable = false)
	private String symbol;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private int rank;

}
