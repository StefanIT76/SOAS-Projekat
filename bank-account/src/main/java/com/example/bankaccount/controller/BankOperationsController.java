package com.example.bankaccount.controller;

import org.springframework.web.bind.annotation.*;


import com.example.bankaccount.dto.DepositRequest;
import com.example.bankaccount.dto.WithdrawRequest;
import com.example.bankaccount.model.BankAccount;
import com.example.bankaccount.repository.BankAccountRepository;

@RestController
@RequestMapping("/bank-operations")
public class BankOperationsController {

    private final BankAccountRepository repo;

    public BankOperationsController(BankAccountRepository repo) {
        this.repo = repo;
    }

    // 1) Provera stanja
    @GetMapping("/balance")
    public double balance(@RequestParam String email) {
        BankAccount acc = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return acc.getBalance();
    }

    // 2) Uplata
    @PostMapping("/deposit")
    public BankAccount deposit(@RequestBody DepositRequest req) {
        if (req.email == null || req.email.isBlank()) {
            throw new RuntimeException("Email is required");
        }
        if (req.amount <= 0) {
            throw new RuntimeException("Amount must be > 0");
        }

        BankAccount acc = repo.findByEmail(req.email)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        acc.setBalance(acc.getBalance() + req.amount);
        return repo.save(acc);
    }

    // 3) Isplata
    @PostMapping("/withdraw")
    public BankAccount withdraw(@RequestBody WithdrawRequest req) {
        if (req.email == null || req.email.isBlank()) {
            throw new RuntimeException("Email is required");
        }
        if (req.amount <= 0) {
            throw new RuntimeException("Amount must be > 0");
        }

        BankAccount acc = repo.findByEmail(req.email)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (acc.getBalance() < req.amount) {
            throw new RuntimeException("Insufficient funds");
        }

        acc.setBalance(acc.getBalance() - req.amount);
        return repo.save(acc);
    }
}

