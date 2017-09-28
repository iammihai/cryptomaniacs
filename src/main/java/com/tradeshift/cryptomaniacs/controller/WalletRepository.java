package com.tradeshift.cryptomaniacs.controller;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradeshift.cryptomaniacs.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

	Wallet findByAddress(String address);

}
