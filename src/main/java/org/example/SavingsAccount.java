package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;


/** Savings account with annual interest rate. */
public class SavingsAccount extends Account {
    private BigDecimal annualInterestRate; // e.g., 0.06 for 6%


    public SavingsAccount(String accountNumber, BigDecimal openingBalance, BigDecimal annualInterestRate) {
        super(accountNumber, openingBalance);
        setAnnualInterestRate(annualInterestRate);
    }


    public BigDecimal getAnnualInterestRate() { return annualInterestRate; }


    public void setAnnualInterestRate(BigDecimal rate) {
        if (rate == null || rate.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid interest rate.");
        }
        this.annualInterestRate = rate.setScale(4, RoundingMode.HALF_UP);
    }


    /** Apply simple monthly interest (no intra-month compounding). */
    public BigDecimal applyMonthlyInterest() {
        BigDecimal monthlyRate = annualInterestRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
        BigDecimal interest = balance.multiply(monthlyRate).setScale(2, RoundingMode.DOWN);
        balance = balance.add(interest);
        return interest;
    }
}