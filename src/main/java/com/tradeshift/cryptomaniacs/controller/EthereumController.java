package com.tradeshift.cryptomaniacs.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletFile;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.RawTransaction;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByNumber;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.exceptions.TransactionTimeoutException;
import org.web3j.utils.Numeric;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradeshift.cryptomaniacs.entity.Wallet;

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
			EthTransaction tx = web3j.ethGetTransactionByBlockNumberAndIndex(safeBlockNumber, BigInteger.valueOf(i)).send();
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
