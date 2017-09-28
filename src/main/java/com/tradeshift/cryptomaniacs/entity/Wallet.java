package com.tradeshift.cryptomaniacs.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

	public static enum Type {
		BITCOIN, ETHEREUM
	}

	private static final long serialVersionUID = 1L;
	private Long id;
	private User user;
	private Type type;
	private String address;
	private String password;
	private byte[] data;

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
	@Enumerated(EnumType.STRING)
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@NotNull
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@NotNull
	@Column(columnDefinition = "BINARY(2048)")
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@NotNull
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
