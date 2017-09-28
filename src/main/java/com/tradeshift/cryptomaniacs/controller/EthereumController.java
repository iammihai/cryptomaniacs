package com.tradeshift.cryptomaniacs.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByNumber;
import org.web3j.protocol.core.methods.response.EthTransaction;
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

	public EthSendTransaction transact(Wallet fromWallet, final String destination, final String value) throws CipherException, JsonParseException, JsonMappingException,
			IOException, InterruptedException, TransactionTimeoutException, ExecutionException {
		
		ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
		WalletFile walletFile = objectMapper.readValue(fromWallet.getDataParsed(), WalletFile.class);
		
		Credentials credentials = Credentials.create(org.web3j.crypto.Wallet.decrypt(fromWallet.getPassword(), walletFile));
		
		BigInteger senderNonce = web3j.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getTransactionCount();
		
		RawTransaction rawTransaction  = RawTransaction.createEtherTransaction(senderNonce, BigInteger.valueOf(22000l), BigInteger.valueOf(25000l), destination, BigInteger.valueOf(1000000000000000l));
		
		byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction,credentials);
		String hexValue = Numeric.toHexString(signedMessage);
		
		EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
		
		
		return ethSendTransaction;
		// poll for transaction response via org.web3j.protocol.Web3j.ethGetTransactionReceipt(<txHash>)
	}
	
	public String getNonce(final String address) throws IOException {
		return web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).send().getTransactionCount().toString();
	}

}
