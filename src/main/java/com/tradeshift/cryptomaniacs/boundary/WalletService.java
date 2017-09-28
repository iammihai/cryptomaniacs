package com.tradeshift.cryptomaniacs.boundary;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.Transaction;

import com.tradeshift.cryptomaniacs.controller.EthereumController;
import com.tradeshift.cryptomaniacs.controller.UserRepository;
import com.tradeshift.cryptomaniacs.controller.WalletRepository;
import com.tradeshift.cryptomaniacs.entity.Wallet;

@RestController
@RequestMapping(value = "/api/wallet", produces = MediaType.APPLICATION_JSON)
public class WalletService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private EthereumController ethereumController;

	@RequestMapping(value = "/user/{username}", method = RequestMethod.POST)
	public Wallet addWallet(@PathVariable("username") String username, @RequestBody Wallet wallet) {
		final String address = ethereumController.extractAddress(wallet.getData());
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

	@RequestMapping(value = "/{address}/ballance", method = RequestMethod.GET)
	public double ballance(@PathVariable("address") String address) throws IOException {
		return ethereumController.getBallance(address);
	}

	@GetMapping(path = "/{address}/transactions")
	public List<Transaction> getTransactions(@PathVariable String address,
			@RequestParam(required = false, value = "block") final String block) throws IOException {
		return ethereumController.getTransactionHistory(address, block);
	}

}
