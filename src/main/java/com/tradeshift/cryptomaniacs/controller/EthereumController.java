package com.tradeshift.cryptomaniacs.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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

import net.sf.json.JSONObject;

@Service
public class EthereumController {

	@Autowired
	private Web3j web3j;

	public String getClientVersion() throws IOException {
		return web3j.web3ClientVersion().send().getWeb3ClientVersion();
	}

	public double getBallance(final String address) throws IOException {
		EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
		BigInteger wei = ethGetBalance.getBalance();
		return wei.doubleValue() / Math.pow(10, 18);
	}

	public List<Transaction> getTransactionHistory(final String address, final String blockNumber) throws IOException {
		DefaultBlockParameter safeBlockNumber = getBlockNumber(blockNumber);
		EthGetBlockTransactionCountByNumber count = web3j.ethGetBlockTransactionCountByNumber(safeBlockNumber).send();
		int value = Integer.parseInt(count.getResult().replace("0x", ""), 16);
		List<Transaction> result = new ArrayList<>();

		for (int i = 0; i < value; i++) {
			EthTransaction tx = web3j.ethGetTransactionByBlockNumberAndIndex(safeBlockNumber, BigInteger.valueOf(i))
					.send();
			if (tx.getResult().getFrom().equals(address)) {
				result.add(tx.getResult());
			}
		}

		return result;
	}

	private DefaultBlockParameter getBlockNumber(final String blockNumber) {
		if (blockNumber != null) {
			return new DefaultBlockParameterNumber(BigInteger.valueOf(Long.valueOf(blockNumber)));
		} else {
			return DefaultBlockParameterName.LATEST;
		}
	}

	public String extractAddress(final String data) {
		final JSONObject json = JSONObject.fromObject(data);
		return "0x" + json.get("address");
	}

}
