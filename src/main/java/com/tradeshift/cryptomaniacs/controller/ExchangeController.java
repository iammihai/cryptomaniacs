package com.tradeshift.cryptomaniacs.controller;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Service;

import com.tradeshift.cryptomaniacs.entity.CurrencyExchange;

import net.sf.json.JSONObject;

@Service
public class ExchangeController {

	public CurrencyExchange ethereumToCurrency(final String toCurrency, final double fromAmount) throws Exception {
		final String string = Request.Get("https://coinmarketcap-nexuist.rhcloud.com/api/eth").execute().returnContent()
				.asString(StandardCharsets.UTF_8);
		final JSONObject json = JSONObject.fromObject(string);
		final JSONObject price = (JSONObject) json.get("price");
		final double oneEthereumInToCurrency = (Double) price.get(toCurrency.toLowerCase());
		final double toAmount = oneEthereumInToCurrency * fromAmount;

		final CurrencyExchange result = new CurrencyExchange();
		result.setFromCurrency("ETH");
		result.setToCurrency(toCurrency.toUpperCase());
		result.setFromAmount(fromAmount);
		result.setToAmount(toAmount);
		return result;
	}

}
