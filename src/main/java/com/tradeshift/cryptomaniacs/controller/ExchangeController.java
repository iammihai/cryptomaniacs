package com.tradeshift.cryptomaniacs.controller;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Service;

import com.tradeshift.cryptomaniacs.entity.CurrencyExchange;

import net.sf.json.JSONObject;

@Service
public class ExchangeController {

	public CurrencyExchange ethereumTo(final double fromAmount, final String toCurrency) throws Exception {
		final String string = Request.Get("https://coinmarketcap-nexuist.rhcloud.com/api/eth").execute().returnContent()
				.asString(StandardCharsets.UTF_8);
		
		final JSONObject json = JSONObject.fromObject(string);
		final JSONObject price = (JSONObject) json.get("price");
		final double ethInTo = (double) price.get(toCurrency.toLowerCase());
		final double toAmount = ethInTo * fromAmount;

		final CurrencyExchange result = new CurrencyExchange();
		result.setFromCurrency("ETH");
		result.setFromAmount(fromAmount);
		result.setToCurrency(toCurrency.toUpperCase());
		result.setToAmount(toAmount);
		result.setSource("https://coinmarketcap-nexuist.rhcloud.com");
		return result;
	}

}
