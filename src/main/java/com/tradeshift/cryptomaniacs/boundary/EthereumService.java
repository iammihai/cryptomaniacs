package com.tradeshift.cryptomaniacs.boundary;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.Transaction;

import com.tradeshift.cryptomaniacs.controller.EthereumController;

@RestController
@RequestMapping(value = "/api/ethereum")
public class EthereumService {

	@Autowired
	EthereumController service;

	@GetMapping(path = "getVersion", produces = "application/json")
	public String getVetsion() throws IOException {
		return service.getClientVersion();
	}

	@GetMapping(path = "wallet/{address}", produces = "application/json")
	public String getBallance(@PathVariable String address) throws InterruptedException, ExecutionException {
		return service.getBallance(address);
	}

	@GetMapping(path = "wallet/{address}/transactions", produces = "application/json")
	public List<Transaction> getTransactions(@PathVariable String address,
			@RequestParam(required = false, value = "block") final String block)
			throws InterruptedException, ExecutionException, IOException, DecoderException {
		return service.getTransactionHistory(address, block);
	}

}
