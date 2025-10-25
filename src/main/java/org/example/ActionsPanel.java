package org.example;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;


/** Right pane: amount input and action buttons. */
public class ActionsPanel extends JPanel {
    private final JTextField amountField = new JTextField();
    private final JButton depositBtn = new JButton("Deposit");
    private final JButton withdrawBtn = new JButton("Withdraw");
    private final JButton interestBtn = new JButton("Apply Interest");


    public ActionsPanel() {
        super(new GridBagLayout());
        setBorder(new TitledBorder("Actions"));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.fill = GridBagConstraints.HORIZONTAL;


        gc.gridx = 0; gc.gridy = 0; add(new JLabel("Amount:"), gc);
        gc.gridx = 1; gc.gridy = 0; gc.weightx = 1; add(amountField, gc);


        JPanel buttons = new JPanel(new GridLayout(1,3,8,8));
        buttons.add(depositBtn); buttons.add(withdrawBtn); buttons.add(interestBtn);
        gc.gridx = 0; gc.gridy = 1; gc.gridwidth = 2; gc.weightx = 1; add(buttons, gc);
    }


    public void onDeposit(Runnable r) { depositBtn.addActionListener(e -> r.run()); }
    public void onWithdraw(Runnable r) { withdrawBtn.addActionListener(e -> r.run()); }
    public void onApplyInterest(Runnable r) { interestBtn.addActionListener(e -> r.run()); }


    public BigDecimal readAmount() {
        String raw = amountField.getText().replace(",", ".").trim();
        if (raw.isEmpty()) throw new IllegalArgumentException("Amount is required.");
        BigDecimal amount = new BigDecimal(raw).setScale(2, RoundingMode.DOWN);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be greater than zero.");
        return amount;
    }
}