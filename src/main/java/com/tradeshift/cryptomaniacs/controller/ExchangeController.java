package com.tradeshift.cryptomaniacs.controller;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

@Service
public class ExchangeController {

	public Double convert(final String currency, final Double amount) throws Exception {
		try {
			final String string = Request.Get("https://coinmarketcap-nexuist.rhcloud.com/api/eth").execute()
					.returnContent().asString(StandardCharsets.UTF_8);
			final JSONObject json = JSONObject.fromObject(string);
			final JSONObject price = (JSONObject) json.get("price");
			final Double oneEthereum = (Double) price.get(currency);
			return oneEthereum * amount;
		} catch (final Exception e) {
			e.printStackTrace();
			return Double.NaN;
		}
	}

}
