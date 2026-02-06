package com.example.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.userservice.dto.BankAccountRequest;

@FeignClient(name = "bank-account")
public interface BankAccountClient {

    @PostMapping("/bank-accounts")
    void createAccount(@RequestBody BankAccountRequest request);
}
