package com.tradeshift.cryptomaniacs.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByNumber;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;

@Service
public class EthereumController {

	@Autowired
	private Web3j web3j;

	public String getClientVersion() throws IOException {
		Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
		return web3ClientVersion.getWeb3ClientVersion();
	}

	public String getBallance(final String address) throws InterruptedException, ExecutionException {

		// send asynchronous requests to get balance
		EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).sendAsync().get();
		BigInteger wei = ethGetBalance.getBalance();
		return String.valueOf(wei.doubleValue() / Math.pow(10, 18));
	}

	public List<Transaction> getTransactionHistory(final String address, final String blockNumber)
			throws InterruptedException, ExecutionException, IOException, DecoderException {

		EthGetBlockTransactionCountByNumber count = web3j
				.ethGetBlockTransactionCountByNumber(getBlockNumber(blockNumber)).sendAsync().get();
		int value = Integer.parseInt(count.getResult().replace("0x", ""), 16);

		System.out.println("Found tx number: " + value);

		List<Transaction> myList = new ArrayList<>();

		for (int i = 0; i < value; i++) {
			EthTransaction tx = web3j
					.ethGetTransactionByBlockNumberAndIndex(getBlockNumber(blockNumber), BigInteger.valueOf(i))
					.sendAsync().get();

			System.out.println(tx.getResult().getFrom());

			if ((tx.getResult().getFrom()).equals(address)) {
				myList.add(tx.getResult());
			}

		}

		return myList;
	}

	private DefaultBlockParameter getBlockNumber(final String blockNumber) {
		if (blockNumber != null) {
			return new DefaultBlockParameterNumber(BigInteger.valueOf(Long.valueOf(blockNumber)));
		} else {
			return DefaultBlockParameterName.LATEST;
		}

	}

}
