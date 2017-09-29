package com.tradeshift.cryptomaniacs.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletFile;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.RawTransaction;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.utils.Numeric;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradeshift.cryptomaniacs.entity.Wallet;

@Service
public class EthereumController {

	@Autowired
	private Web3j web3j;

	public Web3ClientVersion getClientVersion() throws IOException {
		return web3j.web3ClientVersion().send();
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

	public List<Transaction> getTransactionsFromLastBlocks(final String fromAddress, final int lastBlocks)
			throws IOException {

		final List<Transaction> transactions = new ArrayList<>();
		BigInteger currentBlockNumber = getLastestBlockNumber();

		for (int i = lastBlocks; i > 0; i--) {
			transactions.addAll(getTransactionsFromBlockNumber(fromAddress, currentBlockNumber));
			currentBlockNumber = currentBlockNumber.subtract(BigInteger.ONE);
		}

		return transactions;

	}

	public List<Transaction> getTransactionsFromBlockNumber(final String fromAddress, final BigInteger blockNumber)
			throws IOException {

		final Block block = web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(blockNumber), true).send()
				.getBlock();
		return block.getTransactions().stream().map(tr -> (Transaction) tr.get())
				.filter(t -> t.getFrom().equalsIgnoreCase(fromAddress)).collect(Collectors.toList());

	}

	public EthSendTransaction transact(Wallet fromWallet, final BigInteger gasPrice, final BigInteger gasLimit,
			final String toAddress, final BigInteger value) throws Exception {

		Credentials credentials = extractCredentials(fromWallet);
		BigInteger senderNonce = getNonce(credentials.getAddress());

		RawTransaction rawTransaction = RawTransaction.createEtherTransaction(senderNonce, gasPrice, gasLimit,
				toAddress, value);

		byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
		String hexValue = Numeric.toHexString(signedMessage);

		return web3j.ethSendRawTransaction(hexValue).send();

	}

	private Credentials extractCredentials(Wallet fromWallet) throws Exception {
		ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
		WalletFile walletFile = objectMapper.readValue(fromWallet.getDataParsed(), WalletFile.class);
		return Credentials.create(org.web3j.crypto.Wallet.decrypt(fromWallet.getPassword(), walletFile));
	}

	public EthGasPrice getNetworkGasPrice() throws IOException {
		return web3j.ethGasPrice().send();
	}

	private BigInteger getNonce(final String address) throws IOException {
		return web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).send().getTransactionCount();
	}

}
