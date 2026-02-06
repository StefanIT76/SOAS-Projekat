package com.example.bankaccount.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.bankaccount.model.BankAccount;
import com.example.bankaccount.repository.BankAccountRepository;

@RestController
@RequestMapping("/bank-accounts")
public class BankAccountController {

    private final BankAccountRepository repo;

    public BankAccountController(BankAccountRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<BankAccount> all() {
        return repo.findAll();
    }

    @GetMapping("/{email}")
    public BankAccount byEmail(@PathVariable String email) {
        return repo.findByEmail(email).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BankAccount create(@RequestBody BankAccount req) {
        if (repo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Account already exists");
        }
        // balance će biti 0 kad pravimo automatski iz users-service
        return repo.save(new BankAccount(req.getEmail(), req.getBalance()));
    }

    @DeleteMapping("/{email}")
    public void delete(@PathVariable String email) {
        BankAccount acc = repo.findByEmail(email).orElseThrow(() -> new RuntimeException("Account not found"));
        repo.delete(acc);
    }
}

