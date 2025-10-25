package org.example;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.UUID;


/** Entry point: seeds sample data and launches the GUI. */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame.setupNimbus();


            Customer customer = new Customer(UUID.randomUUID().toString().substring(0, 8), "Shady");
            customer.addAccount(new SavingsAccount("SA-1001", new BigDecimal("1200.00"), new BigDecimal("0.06")));
            customer.addAccount(new CheckingAccount("CA-2001", new BigDecimal("500.00"), new BigDecimal("300.00")));


            BankService bank = new BankService(customer);
            MainFrame frame = new MainFrame(bank);
            frame.setVisible(true);
        });
    }
}