package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;


/** Checking account with per-transaction withdrawal limit. */
public class CheckingAccount extends Account {
    private BigDecimal withdrawLimit;


    public CheckingAccount(String accountNumber, BigDecimal openingBalance, BigDecimal withdrawLimit) {
        super(accountNumber, openingBalance);
        setWithdrawLimit(withdrawLimit);
    }


    public BigDecimal getWithdrawLimit() { return withdrawLimit; }


    public void setWithdrawLimit(BigDecimal limit) {
        if (limit == null || limit.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Invalid withdrawal limit.");
        this.withdrawLimit = limit.setScale(2, RoundingMode.DOWN);
    }


    @Override public void withdraw(BigDecimal amount) {
        requirePositive(amount, "Withdraw amount must be greater than zero.");
        if (amount.compareTo(withdrawLimit) > 0) throw new IllegalArgumentException("Withdrawal exceeds per-transaction limit.");
        super.withdraw(amount);
    }
}