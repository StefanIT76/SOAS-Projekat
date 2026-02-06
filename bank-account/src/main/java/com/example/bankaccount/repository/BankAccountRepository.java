package com.example.bankaccount.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bankaccount.model.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByEmail(String email);
    boolean existsByEmail(String email);
}

