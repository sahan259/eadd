// BorrowBookDialog.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class BorrowBookDialog extends JDialog {
    private JComboBox<Borrower> borrowerComboBox;
    private JComboBox<Book> bookComboBox;
    private JButton borrowButton, cancelButton;
    private TransactionDAO transactionDAO;
    private BookDAO bookDAO;
    private BorrowerDAO borrowerDAO;
    private TransactionUI parent;

    public BorrowBookDialog(TransactionUI parent) {
        super(parent, "Borrow Book", true);
        this.parent = parent;
        transactionDAO = new TransactionDAO();
        bookDAO = new BookDAO();
        borrowerDAO = new BorrowerDAO();

        setSize(400, 300);
        setLocationRelativeTo(parent);

        // Create components
        JLabel borrowerLabel = new JLabel("Select Borrower:");
        JLabel bookLabel = new JLabel("Select Book:");

        borrowerComboBox = new JComboBox<>();
        bookComboBox = new JComboBox<>();

        loadBorrowers();
        loadAvailableBooks();

        borrowButton = new JButton("Borrow");
        cancelButton = new JButton("Cancel");

        // Add action listeners
        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Borrower selectedBorrower = (Borrower) borrowerComboBox.getSelectedItem();
                Book selectedBook = (Book) bookComboBox.getSelectedItem();

                if (selectedBorrower == null || selectedBook == null) {
                    JOptionPane.showMessageDialog(null, "Please select both borrower and book.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (selectedBook.getStockCount() <= 0) {
                    JOptionPane.showMessageDialog(null, "Selected book is out of stock.", "Stock Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                transactionDAO.borrowBook(selectedBorrower.getBorrowerID(), selectedBook.getBookID());
                parent.loadTransactions();
                dispose();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        // Layout setup
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Labels and combo boxes
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(borrowerLabel, gbc);

        gbc.gridy = 1;
        panel.add(bookLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(borrowerComboBox, gbc);

        gbc.gridy = 1;
        panel.add(bookComboBox, gbc);

        // Buttons
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(borrowButton, gbc);

        gbc.gridy = 3;
        panel.add(cancelButton, gbc);

        add(panel);
    }

    private void loadBorrowers() {
        List<Borrower> borrowers = borrowerDAO.getAllBorrowers();
        for (Borrower borrower : borrowers) {
            borrowerComboBox.addItem(borrower);
        }
    }

    private void loadAvailableBooks() {
        List<Book> books = bookDAO.getAllBooks();
        for (Book book : books) {
            if (book.getStockCount() > 0) {
                bookComboBox.addItem(book);
            }
        }
    }
}
