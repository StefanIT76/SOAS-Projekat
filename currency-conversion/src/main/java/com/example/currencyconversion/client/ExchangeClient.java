package com.example.currencyconversion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.currencyconversion.dto.ExchangeRateDto;

@FeignClient(name = "currency-exchange")
public interface ExchangeClient {

    @GetMapping("/exchange")
    ExchangeRateDto getRate(@RequestParam String from, @RequestParam String to);
}

