package com.tradeshift.cryptomaniacs.boundary;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tradeshift.cryptomaniacs.controller.UserRepository;
import com.tradeshift.cryptomaniacs.controller.WalletParser;
import com.tradeshift.cryptomaniacs.controller.WalletRepository;
import com.tradeshift.cryptomaniacs.entity.Wallet;

@RestController
@RequestMapping(value = "/api/wallet", produces = MediaType.APPLICATION_JSON)
public class WalletService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WalletRepository walletRepository;

	@RequestMapping(value = "/user/{username}", method = RequestMethod.POST)
	public Wallet addWallet(@PathVariable("username") String username, @RequestBody Wallet wallet) {
		final String address = new WalletParser().extractEthereumAddress(wallet.getData());
		wallet.setAddress(address);
		wallet.setUser(userRepository.findByUsername(username));
		wallet.setType(Wallet.Type.ETHEREUM);
		walletRepository.save(wallet);
		return wallet;
	}

	@RequestMapping(value = "/{address}", method = RequestMethod.GET)
	public Wallet findByAddress(@PathVariable("address") String address) {
		return walletRepository.findByAddress(address);
	}

	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
	public List<Wallet> findByUser(@PathVariable("username") String username) {
		return walletRepository.findByUser(userRepository.findByUsername(username));
	}

	@RequestMapping
	public Wallet sample() {
		Wallet wallet = new Wallet();
		wallet.setAddress("address");
		wallet.setType(Wallet.Type.ETHEREUM);
		wallet.setUser(userRepository.findByUsername("abe"));
		wallet.setData("hello".getBytes());
		return wallet;
	}

}
