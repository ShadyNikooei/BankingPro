package org.example;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;


/** Left pane: customer info and accounts list. */
public class AccountsPanel extends JPanel {
    private final Customer customer;
    private final DefaultListModel<Account> model = new DefaultListModel<>();
    private final JList<Account> list = new JList<>(model);


    public AccountsPanel(Customer customer) {
        super(new BorderLayout(8, 8));
        this.customer = customer;


        JLabel customerLbl = new JLabel("Customer: " + customer.getName() + " (ID: " + customer.getId() + ")");
        customerLbl.setFont(customerLbl.getFont().deriveFont(Font.BOLD));
        add(customerLbl, BorderLayout.NORTH);


        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setBorder(new TitledBorder("Accounts"));
        add(new JScrollPane(list), BorderLayout.CENTER);


        refreshList();
        if (model.getSize() > 0) list.setSelectedIndex(0);
    }


    public void refreshList() {
        model.clear();
        for (Account a : customer.getAccounts()) model.addElement(a);
    }


    public Account getSelectedAccount() {
        Account a = list.getSelectedValue();
        if (a == null) throw new IllegalArgumentException("Please select an account.");
        return a;
    }
}