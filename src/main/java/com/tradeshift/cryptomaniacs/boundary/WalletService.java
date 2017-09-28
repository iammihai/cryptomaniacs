package com.tradeshift.cryptomaniacs.boundary;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tradeshift.cryptomaniacs.controller.WalletRepository;
import com.tradeshift.cryptomaniacs.entity.User;
import com.tradeshift.cryptomaniacs.entity.Wallet;

@RestController
@RequestMapping(value = "/api/wallet", produces = MediaType.APPLICATION_JSON)
public class WalletService {

	@Autowired
	private WalletRepository walletRepository;

	@RequestMapping("/{address}")
	public Wallet findByAddress(String address) {
		return walletRepository.findByAddress(address);
	}

	@RequestMapping("/user/{username}")
	public List<Wallet> findByUser(User user) {
		return walletRepository.findByUser(user);
	}

}
