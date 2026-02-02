package com.example.exchange;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    private final ExchangeRateRepository repo;

    public ExchangeController(ExchangeRateRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ExchangeRate getRate(@RequestParam String from, @RequestParam String to) {
        return repo.findByFromCurrencyAndToCurrency(from, to)
                .orElseThrow(() -> new RuntimeException("Rate not found"));
    }
}
