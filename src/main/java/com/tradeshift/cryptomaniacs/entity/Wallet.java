package com.tradeshift.cryptomaniacs.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "address"))
public class Wallet implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private User user;
	private CryptoCurrencyType cryptoCurrencyType;
	private String address;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@NotNull
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@NotNull
	public CryptoCurrencyType getCryptoCurrencyType() {
		return cryptoCurrencyType;
	}

	public void setCryptoCurrencyType(CryptoCurrencyType cryptoCurrencyType) {
		this.cryptoCurrencyType = cryptoCurrencyType;
	}

	@NotNull
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
