package com.example.exchange;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(ExchangeRateRepository repo) {
        return args -> {
            // Ako već postoji EUR->RSD, ne ubacuj opet
            boolean exists = repo.findByFromCurrencyAndToCurrency("EUR", "RSD").isPresent();
            if (!exists) {
                ExchangeRate r = new ExchangeRate();
                r.setFromCurrency("EUR");
                r.setToCurrency("RSD");
                r.setRate(117.5); // stavi koji god broj želiš
                repo.save(r);
            }
        };
    }
}

