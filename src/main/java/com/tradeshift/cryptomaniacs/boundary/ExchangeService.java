package com.tradeshift.cryptomaniacs.boundary;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tradeshift.cryptomaniacs.controller.ExchangeController;
import com.tradeshift.cryptomaniacs.entity.CurrencyExchange;

@RestController
@RequestMapping(value = "/api/exchange", produces = MediaType.APPLICATION_JSON)
public class ExchangeService {

	@Autowired
	private ExchangeController exchangeController;

	@RequestMapping(value = "/convert", method = RequestMethod.GET)
	public CurrencyExchange convert(@QueryParam("amount") double amount, @QueryParam("currency") String currency)
			throws Exception {

		return exchangeController.ethereumTo(amount, currency);

	}

}
