package com.example.bankaccount.model;

import jakarta.persistence.*;

@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private double balance;

    public BankAccount() {}

    public BankAccount(String email, double balance) {
        this.email = email;
        this.balance = balance;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}
