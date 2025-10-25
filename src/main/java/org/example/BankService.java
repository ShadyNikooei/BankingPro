package org.example;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


/** In-memory banking operations with transaction history. */
public class BankService {
    private final Customer customer;
    private final List<Transaction> history = new ArrayList<>();


    public BankService(Customer customer) { this.customer = customer; }
    public Customer getCustomer() { return customer; }
    public List<Transaction> getHistory() { return Collections.unmodifiableList(history); }


    public void deposit(Account acc, BigDecimal amount) {
        acc.deposit(amount);
        history.add(new Transaction(LocalDateTime.now(), acc.getAccountNumber(), Transaction.Type.DEPOSIT, amount, acc.getBalance()));
    }


    public void withdraw(Account acc, BigDecimal amount) {
        acc.withdraw(amount);
        history.add(new Transaction(LocalDateTime.now(), acc.getAccountNumber(), Transaction.Type.WITHDRAW, amount, acc.getBalance()));
    }


    public BigDecimal applyMonthlyInterest(SavingsAccount acc) {
        BigDecimal interest = acc.applyMonthlyInterest();
        history.add(new Transaction(LocalDateTime.now(), acc.getAccountNumber(), Transaction.Type.INTEREST, interest, acc.getBalance()));
        return interest;
    }
}