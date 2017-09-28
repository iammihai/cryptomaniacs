package com.tradeshift.cryptomaniacs.controller;

import java.nio.charset.StandardCharsets;

import net.sf.json.JSONObject;

public class WalletParser {

	public String extractEthereumAddress(final byte[] data) {
		final JSONObject json = JSONObject.fromObject(new String(data, StandardCharsets.UTF_8));
		return (String) json.get("address");
	}

}
