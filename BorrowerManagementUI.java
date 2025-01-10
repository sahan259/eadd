// BorrowerManagementUI.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class BorrowerManagementUI extends JFrame {
    private BorrowerDAO borrowerDAO;
    private JTable borrowersTable;
    private DefaultTableModel tableModel;

    public BorrowerManagementUI() {
        borrowerDAO = new BorrowerDAO();

        setTitle("Manage Borrowers");
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the frame

        // Table setup
        String[] columnNames = {"Borrower ID", "Name", "Contact Number", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0);
        borrowersTable = new JTable(tableModel);
        loadBorrowers();

        JScrollPane scrollPane = new JScrollPane(borrowersTable);

        // Buttons
        JButton addButton = new JButton("Add Borrower");
        JButton editButton = new JButton("Edit Selected");
        JButton deleteButton = new JButton("Delete Selected");
        JButton refreshButton = new JButton("Refresh");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEditBorrowerDialog(BorrowerManagementUI.this, "Add Borrower", null).setVisible(true);
            }
        });

        // Implement edit and delete similarly as in BookManagementUI
        // For brevity, these can be implemented following the BookManagementUI pattern

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBorrowers();
            }
        });

        // Implement search similarly as in BookManagementUI

        // Layout setup
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(addButton);
        // Add other buttons similarly
        topPanel.add(refreshButton);
        // Add search components similarly

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Load all borrowers
    public void loadBorrowers() {
        tableModel.setRowCount(0); // Clear existing data
        java.util.List<Borrower> borrowers = borrowerDAO.getAllBorrowers();
        for (Borrower borrower : borrowers) {
            Object[] row = {
                borrower.getBorrowerID(),
                borrower.getName(),
                borrower.getContactNumber(),
                borrower.getEmail()
            };
            tableModel.addRow(row);
        }
    }
}
