package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;


/** Base account model. */
public abstract class Account {
    protected final String accountNumber;
    protected BigDecimal balance;


    protected Account(String accountNumber, BigDecimal openingBalance) {
        this.accountNumber = Objects.requireNonNull(accountNumber);
        this.balance = openingBalance == null ? BigDecimal.ZERO : openingBalance.setScale(2, RoundingMode.DOWN);
    }


    public String getAccountNumber() { return accountNumber; }
    public BigDecimal getBalance() { return balance; }


    public void deposit(BigDecimal amount) {
        requirePositive(amount, "Deposit amount must be greater than zero.");
        balance = balance.add(amount);
    }


    public void withdraw(BigDecimal amount) {
        requirePositive(amount, "Withdraw amount must be greater than zero.");
        if (balance.compareTo(amount) < 0) throw new IllegalArgumentException("Insufficient funds.");
        balance = balance.subtract(amount);
    }


    protected static void requirePositive(BigDecimal amount, String message) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException(message);
    }


    @Override public String toString() { return getClass().getSimpleName() + " (" + accountNumber + ")"; }
}