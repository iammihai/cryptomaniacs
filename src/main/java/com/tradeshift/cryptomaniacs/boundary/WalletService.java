package com.tradeshift.cryptomaniacs.boundary;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
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
	private EthereumController ethereumController;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WalletRepository walletRepository;

	@RequestMapping(value = "/user/{username}", method = RequestMethod.POST)
	public Wallet add(@PathVariable("username") String username, @RequestBody Wallet wallet) {
		final String address = ethereumController.extractAddress(wallet.getDataParsed());
		wallet.setAddress(address);
		wallet.setUser(userRepository.findByUsername(username));
		wallet.setType(Wallet.Type.ETHEREUM);
		walletRepository.save(wallet);
		return wallet;
	}

	@RequestMapping(value = "/{address}/ballance", method = RequestMethod.GET)
	public double ballance(@PathVariable("address") String address) throws IOException {
		return ethereumController.getBallance(address);
	}

	@RequestMapping(value = "/{address}", method = RequestMethod.GET)
	public Wallet get(@PathVariable("address") String address) {
		return walletRepository.findByAddress(address);
	}

	@RequestMapping(value = "/{address}/data", method = RequestMethod.GET)
	public String getData(@PathVariable("address") String address) {
		return get(address).getDataParsed();
	}

	@GetMapping(path = "/{address}/transactions")
	public List<Transaction> transactions(@PathVariable String address,
			@RequestParam(required = false, value = "block") final String blockNumber) throws IOException {

		if (StringUtils.isNotBlank(blockNumber)) {
			return ethereumController.getTransactions(address, new BigInteger(blockNumber));
		} else {
			return ethereumController.getTransactions(address, 100);
		}

	}

	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
	public List<Wallet> userWallets(@PathVariable("username") String username) {
		return walletRepository.findByUser(userRepository.findByUsername(username));
	}
	
	@RequestMapping(value = "/exchange", method = RequestMethod.GET)
	public Double exchange(@QueryParam("currency") String currency, @QueryParam("amount") Double amount) throws Exception {
		return ethereumController.getExchange(currency, amount);
	}

}

