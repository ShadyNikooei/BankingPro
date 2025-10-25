package org.example;

// File: MainFrame.java
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;

/**
 * Main window (single-folder version) with built-in Nimbus look & feel.
 * English-only UI and messages.
 */
public class MainFrame extends JFrame {
    private final BankService bank;
    private final AccountsPanel accountsPanel;
    private final ActionsPanel actionsPanel;
    private final TransactionsPanel transactionsPanel;

    public MainFrame(BankService bank) {
        this.bank = bank;

        setTitle("BankingPro – Account Manager");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(980, 620));

        // Root container
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(new EmptyBorder(12, 12, 12, 12));
        setContentPane(root);

        // Header
        JLabel title = new JLabel("💰 BankingPro");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        root.add(title, BorderLayout.NORTH);

        // Panels
        accountsPanel = new AccountsPanel(bank.getCustomer());
        actionsPanel = new ActionsPanel();
        transactionsPanel = new TransactionsPanel();

        JSplitPane center = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, accountsPanel, actionsPanel);
        center.setResizeWeight(0.45);
        root.add(center, BorderLayout.CENTER);
        root.add(transactionsPanel, BorderLayout.SOUTH);

        // Wire actions
        actionsPanel.onDeposit(() -> withErrors(() -> {
            Account acc = accountsPanel.getSelectedAccount();
            BigDecimal amount = actionsPanel.readAmount();
            bank.deposit(acc, amount);
            refresh();
            toast("Deposit successful");
        }));

        actionsPanel.onWithdraw(() -> withErrors(() -> {
            Account acc = accountsPanel.getSelectedAccount();
            BigDecimal amount = actionsPanel.readAmount();
            bank.withdraw(acc, amount);
            refresh();
            toast("Withdrawal successful");
        }));

        actionsPanel.onApplyInterest(() -> withErrors(() -> {
            Account acc = accountsPanel.getSelectedAccount();
            if (acc instanceof SavingsAccount) {
                bank.applyMonthlyInterest((SavingsAccount) acc);
                refresh();
                toast("Interest applied");
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Select a SavingsAccount to apply interest.",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }));

        refresh();
        setLocationRelativeTo(null);
    }

    private void refresh() {
        accountsPanel.refreshList();
        transactionsPanel.setRows(bank.getHistory());
    }

    private void withErrors(RunnableEx r) {
        try {
            r.run();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void toast(String msg) {
        // Simple toast-like dialog
        JDialog d = new JDialog(this, false);
        d.setUndecorated(true);
        JLabel l = new JLabel("  " + msg + "  ");
        l.setBorder(new EmptyBorder(8, 12, 8, 12));
        d.getContentPane().add(l);
        d.pack();
        d.setLocationRelativeTo(this);
        new Timer(1100, e -> d.dispose()).start();
        d.setVisible(true);
    }

    /** Call once before showing the frame to apply Nimbus look & feel. */
    public static void setupNimbus() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) { }
        UIManager.put("Button.arc", 16);
        UIManager.put("Component.arc", 16);
        UIManager.put("TextComponent.arc", 12);
    }

    @FunctionalInterface interface RunnableEx { void run() throws Exception; }
}
