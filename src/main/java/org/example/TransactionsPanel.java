package org.example;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;


/** Bottom pane: transaction history table. */
public class TransactionsPanel extends JPanel {
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"Time", "Account", "Type", "Amount", "Balance"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public TransactionsPanel() {
        super(new java.awt.BorderLayout());
        setBorder(new TitledBorder("Transactions"));
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table), java.awt.BorderLayout.CENTER);
    }


    public void setRows(List<Transaction> rows) {
        model.setRowCount(0);
        for (Transaction t : rows) {
            model.addRow(new Object[]{ fmt.format(t.getTime()), t.getAccountNo(), t.getType(), t.getAmount(), t.getResultingBalance() });
        }
    }
}