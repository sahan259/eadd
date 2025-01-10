// MainMenu.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Library Management System - Main Menu");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        // Create buttons
        JButton manageBooksButton = new JButton("Manage Books");
        JButton manageBorrowersButton = new JButton("Manage Borrowers");
        JButton manageTransactionsButton = new JButton("Manage Transactions");
        JButton viewReportsButton = new JButton("View Reports");
        JButton logoutButton = new JButton("Logout");

        // Set button sizes
        Dimension buttonSize = new Dimension(200, 40);
        manageBooksButton.setPreferredSize(buttonSize);
        manageBorrowersButton.setPreferredSize(buttonSize);
        manageTransactionsButton.setPreferredSize(buttonSize);
        viewReportsButton.setPreferredSize(buttonSize);
        logoutButton.setPreferredSize(buttonSize);

        // Add action listeners
        manageBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BookManagementUI().setVisible(true);
            }
        });

        manageBorrowersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BorrowerManagementUI().setVisible(true);
            }
        });

        manageTransactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TransactionUI().setVisible(true);
            }
        });

        viewReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReportsUI().setVisible(true);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginUI().setVisible(true);
                dispose(); // Close main menu
            }
        });

        // Layout setup
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Padding

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(manageBooksButton, gbc);

        gbc.gridy = 1;
        panel.add(manageBorrowersButton, gbc);

        gbc.gridy = 2;
        panel.add(manageTransactionsButton, gbc);

        gbc.gridy = 3;
        panel.add(viewReportsButton, gbc);

        gbc.gridy = 4;
        panel.add(logoutButton, gbc);

        add(panel);
    }

    public static void main(String[] args) {
        // Start with the login screen
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginUI().setVisible(true);
            }
        });
    }
}
