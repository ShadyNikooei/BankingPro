package org.example;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/** Simple transaction record (for UI history table). */
public class Transaction {
    public enum Type { DEPOSIT, WITHDRAW, INTEREST }
    private final LocalDateTime time; private final String accountNo; private final Type type; private final BigDecimal amount; private final BigDecimal resultingBalance;
    public Transaction(LocalDateTime time, String accountNo, Type type, BigDecimal amount, BigDecimal resultingBalance) {
        this.time = time; this.accountNo = accountNo; this.type = type; this.amount = amount; this.resultingBalance = resultingBalance;
    }
    public LocalDateTime getTime() { return time; }
    public String getAccountNo() { return accountNo; }
    public Type getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getResultingBalance() { return resultingBalance; }
}