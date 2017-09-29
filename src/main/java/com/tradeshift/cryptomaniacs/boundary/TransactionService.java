package com.tradeshift.cryptomaniacs.boundary;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Transaction;

import com.tradeshift.cryptomaniacs.controller.EthereumController;
import com.tradeshift.cryptomaniacs.controller.WalletRepository;
import com.tradeshift.cryptomaniacs.entity.Wallet;

@RestController
@RequestMapping(value = "/api/tx", produces = MediaType.APPLICATION_JSON)
public class TransactionService {

	@Autowired
	private EthereumController ethereumController;
	@Autowired
	private WalletRepository walletRepository;

	@GetMapping(path = "/list/{address}/last-blocks")
	public List<Transaction> transactionsFromLastBlocks(@PathVariable String address,
			@RequestParam(required = false, value = "n") final int lastBlocks) throws IOException {

		return ethereumController.getTransactionsFromLastBlocks(address, lastBlocks);

	}

	@GetMapping(path = "/list/{address}/block-number")
	public List<Transaction> transactions(@PathVariable String address,
			@RequestParam(required = false, value = "n") final String blockNumber) throws IOException {

		return ethereumController.getTransactionsFromBlockNumber(address, new BigInteger(blockNumber));

	}

	@PostMapping(value = "/pay/{fromAddress}/{toAddress}")
	public EthSendTransaction pay(@PathVariable("fromAddress") String fromAddress,
			@PathVariable(value = "toAddress") final String toAddress,
			@RequestParam(value = "amount") final String amount,
			@RequestParam(value = "gasLimit") final String gasLimit) throws Exception {

		Wallet from = walletRepository.findByAddress(fromAddress);
		return ethereumController.pay(from, new BigInteger(gasLimit), toAddress, new BigInteger(amount));

	}

}
