package com.udemy.microservice.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.udemy.microservice.currencyconversionservice.bean.CurrencyConversionBean;
import com.udemy.microservice.currencyconversionservice.proxy.CurrencyExchangeServiceProxy;

@RestController
public class CurrencyConversionController {
	
	private  Logger logger=org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CurrencyExchangeServiceProxy proxy;

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		Map<String, String> variables = new HashMap<String, String>();
		variables.put("from", from);
		variables.put("to", to);

		ResponseEntity<CurrencyConversionBean> forEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class, variables);

		CurrencyConversionBean body = forEntity.getBody();
		
		System.out.println("Quantity is: "+quantity);
		System.out.println("conversion is: "+body.getCurrencyConversion());
		System.out.println("total is: "+quantity.multiply(body.getCurrencyConversion()));

		return new CurrencyConversionBean(body.getId(), from, to, body.getCurrencyConversion(), quantity,
				quantity.multiply(body.getCurrencyConversion()), body.getPort());

	}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyfeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {


		CurrencyConversionBean body = proxy.retrieveExchangeValue(from, to);
		logger.info("{}",body);
		
		System.out.println("Quantity is: "+quantity);
		System.out.println("conversion is: "+body.getCurrencyConversion());
		System.out.println("total is: "+quantity.multiply(body.getCurrencyConversion()));

		return new CurrencyConversionBean(body.getId(), from, to, body.getCurrencyConversion(), quantity,
				quantity.multiply(body.getCurrencyConversion()), body.getPort());

	}
}
