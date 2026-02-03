package com.example.users.client;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "currency-exchange")
public interface ExchangeClient {

    @GetMapping("/exchange")
    ExchangeRateDto getRate(@RequestParam String from, @RequestParam String to);
}
