package com.example.userservice.dto;

public class BankAccountRequest {
    public String email;
    public double balance;

    public BankAccountRequest() {}

    public BankAccountRequest(String email, double balance) {
        this.email = email;
        this.balance = balance;
    }
}

