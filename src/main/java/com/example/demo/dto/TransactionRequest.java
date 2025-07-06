package com.example.demo.dto;

import java.time.LocalDate;

public class TransactionRequest {
    private String customerId;
    private double amount;
    private LocalDate date;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
    
    

    
}