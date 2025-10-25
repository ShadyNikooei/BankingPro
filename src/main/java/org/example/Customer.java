package org.example;

import java.util.*;


/** Customer model containing multiple accounts. */
public class Customer {
    private final String id;
    private final String name;
    private final List<Account> accounts = new ArrayList<>();


    public Customer(String id, String name) { this.id = Objects.requireNonNull(id); this.name = Objects.requireNonNull(name); }


    public String getId() { return id; }
    public String getName() { return name; }
    public List<Account> getAccounts() { return Collections.unmodifiableList(accounts); }


    public void addAccount(Account acc) { accounts.add(Objects.requireNonNull(acc)); }


    /** Find an account by number (or null). */
    public Account findAccount(String number) {
        for (Account a : accounts) if (a.getAccountNumber().equals(number)) return a; return null;
    }
}