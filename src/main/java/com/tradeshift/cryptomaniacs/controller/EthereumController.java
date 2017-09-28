package com.tradeshift.cryptomaniacs.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Transaction;

import net.sf.json.JSONObject;

@Service
public class EthereumController {

	@Autowired
	private Web3j web3j;

	public Block wee() throws IOException {
		return web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send().getBlock();
	}

	public String getClientVersion() throws IOException {
		return web3j.web3ClientVersion().send().getWeb3ClientVersion();
	}

	public double getBallance(final String fromAddress) throws IOException {
		EthGetBalance ethGetBalance = web3j.ethGetBalance(fromAddress, DefaultBlockParameterName.LATEST).send();
		BigInteger wei = ethGetBalance.getBalance();
		return wei.doubleValue() / Math.pow(10, 18);
	}

	public BigInteger getLastestBlockNumber() throws IOException {
		EthBlockNumber firstBlock = web3j.ethBlockNumber().send();
		return new BigInteger(firstBlock.getResult().replace("0x", ""), 16);
	}

	public List<Transaction> getTransactions(final String fromAddress, final int previousBlocks) throws IOException {
		final List<Transaction> transactions = new ArrayList<>();
		BigInteger currentBlockNumber = getLastestBlockNumber();

		for (int i = previousBlocks; i > 0; i--) {
			transactions.addAll(getTransactions(fromAddress, currentBlockNumber));
			currentBlockNumber = currentBlockNumber.subtract(BigInteger.ONE);
		}

		return transactions;
	}

	public List<Transaction> getTransactions(final String fromAddress, final BigInteger blockNumber)
			throws IOException {

		final Block block = web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(blockNumber), true).send()
				.getBlock();
		return block.getTransactions().stream().map(tr -> (Transaction) tr.get())
				.filter(t -> t.getFrom().equals(fromAddress)).collect(Collectors.toList());

	}

	public String extractAddress(final String data) {
		final JSONObject json = JSONObject.fromObject(data);
		return "0x" + json.get("address");
	}
	
	public Double getExchange(final String currency, final Double amount) throws Exception {
		try {
			final String string = Request.Get("https://coinmarketcap-nexuist.rhcloud.com/api/eth").execute().returnContent().asString(StandardCharsets.UTF_8);
			final JSONObject json = JSONObject.fromObject(string);
			final JSONObject price = (JSONObject) json.get("price");
			final Double oneEthereum = (Double)price.get(currency);
			return oneEthereum * amount;
		} catch (final Exception e) {
			e.printStackTrace();
			return Double.NaN;
		}
	}

}
