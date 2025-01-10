// TransactionUI.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class TransactionUI extends JFrame {
    private TransactionDAO transactionDAO;
    private BookDAO bookDAO;
    private BorrowerDAO borrowerDAO;
    private JTable transactionsTable;
    private DefaultTableModel tableModel;

    public TransactionUI() {
        transactionDAO = new TransactionDAO();
        bookDAO = new BookDAO();
        borrowerDAO = new BorrowerDAO();

        setTitle("Manage Transactions");
        setSize(900, 600);
        setLocationRelativeTo(null); // Center the frame

        // Table setup
        String[] columnNames = {"Transaction ID", "Borrower Name", "Book Title", "Borrow Date", "Return Date", "Fine Amount"};
        tableModel = new DefaultTableModel(columnNames, 0);
        transactionsTable = new JTable(tableModel);
        loadTransactions();

        JScrollPane scrollPane = new JScrollPane(transactionsTable);

        // Buttons
        JButton borrowButton = new JButton("Borrow Book");
        JButton returnButton = new JButton("Return Book");
        JButton refreshButton = new JButton("Refresh");

        // Add action listeners
        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BorrowBookDialog(TransactionUI.this).setVisible(true);
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = transactionsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int transactionID = (int) tableModel.getValueAt(selectedRow, 0);
                    String returnDate = (String) tableModel.getValueAt(selectedRow, 4);
                    if (returnDate != null && !returnDate.equals("Not Returned")) {
                        JOptionPane.showMessageDialog(null, "This book has already been returned.", "Info", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    transactionDAO.returnBook(transactionID);
                    loadTransactions();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a transaction to return.", "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTransactions();
            }
        });

        // Layout setup
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(borrowButton);
        topPanel.add(returnButton);
        topPanel.add(refreshButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Load all transactions
    public void loadTransactions() {
        tableModel.setRowCount(0); // Clear existing data
        java.util.List<Transaction> transactions = getAllTransactions();
        for (Transaction txn : transactions) {
            String borrowerName = getBorrowerName(txn.getBorrowerID());
            String bookTitle = getBookTitle(txn.getBookID());
            String borrowDate = txn.getBorrowDate().toString();
            String returnDate = (txn.getReturnDate() != null) ? txn.getReturnDate().toString() : "Not Returned";
            String fineAmount = (txn.getFineAmount() > 0) ? String.valueOf(txn.getFineAmount()) : "0.00";

            Object[] row = {
                txn.getTransactionID(),
                borrowerName,
                bookTitle,
                borrowDate,
                returnDate,
                fineAmount
            };
            tableModel.addRow(row);
        }
    }

    private java.util.List<Transaction> getAllTransactions() {
        // Implement method to fetch all transactions from the database
        // For brevity, this is left as an exercise. You can create a method in TransactionDAO to fetch all transactions
        // including borrower and book details using JOINs
        return new ArrayList<>(); // Placeholder
    }

    private String getBorrowerName(int borrowerID) {
        // Implement method to get borrower name by ID
        // For brevity, this is left as an exercise
        return "Borrower Name"; // Placeholder
    }

    private String getBookTitle(int bookID) {
        // Implement method to get book title by ID
        // For brevity, this is left as an exercise
        return "Book Title"; // Placeholder
    }
}
