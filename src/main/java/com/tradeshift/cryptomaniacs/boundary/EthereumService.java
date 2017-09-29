package com.tradeshift.cryptomaniacs.boundary;

import java.io.IOException;
import java.math.BigInteger;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;

import com.tradeshift.cryptomaniacs.controller.EthereumController;

@RestController
@RequestMapping(value = "/api/network", produces = MediaType.APPLICATION_JSON)
public class EthereumService {

	@Autowired
	private EthereumController ethereumController;

	@GetMapping(value = "/client")
	public Web3ClientVersion getClientDetails() throws IOException {
		return ethereumController.getClientVersion();
	}

	@GetMapping(value = "/latest-block-number")
	public BigInteger latestBlockNumber() throws IOException {
		return ethereumController.getLastestBlockNumber();
	}
	
	@GetMapping(value = "/gas-price")
	public EthGasPrice getNetworkGasPrice() throws IOException {
		return ethereumController.getNetworkGasPrice();
	}

}
