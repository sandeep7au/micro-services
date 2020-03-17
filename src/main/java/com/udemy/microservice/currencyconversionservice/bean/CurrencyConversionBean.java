package com.udemy.microservice.currencyconversionservice.bean;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

//@Entity class
//Testing changes for git
public class CurrencyConversionBean {

	private Long id;
	private String from;
	private String to;
	private BigDecimal currencyConversion;
	private BigDecimal quantity;
	private BigDecimal totalAmount;
	private int port;
}
