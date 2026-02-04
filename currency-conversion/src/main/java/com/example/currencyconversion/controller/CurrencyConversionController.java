package com.example.currencyconversion.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.currencyconversion.client.ExchangeClient;
import com.example.currencyconversion.dto.ExchangeRateDto;

@RestController
public class CurrencyConversionController {
	
	private final ExchangeClient exchangeClient;
	
	public CurrencyConversionController(ExchangeClient exchangeClient) {
		this.exchangeClient = exchangeClient;
		
	}
	
	@GetMapping("/currency-conversion")
	public String convert(
			@RequestParam String from,
			@RequestParam String to,
			@RequestParam double quantity
			
	) {
		ExchangeRateDto dto = exchangeClient.getRate(from, to);
		double total = quantity * dto.rate;
		return "Converted " + quantity + " " + from + " to " + " = " + total + " (rate= " + dto.rate + ")";
	}
	
}
