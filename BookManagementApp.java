// BookManagementUI.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class BookManagementUI extends JFrame {
    private BookDAO bookDAO;
    private JTable booksTable;
    private DefaultTableModel tableModel;

    public BookManagementUI() {
        bookDAO = new BookDAO();

        setTitle("Manage Books");
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the frame

        // Table setup
        String[] columnNames = {"Book ID", "Title", "Author", "Category", "Stock", "Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        booksTable = new JTable(tableModel);
        loadBooks();

        JScrollPane scrollPane = new JScrollPane(booksTable);

        // Buttons
        JButton addButton = new JButton("Add Book");
        JButton editButton = new JButton("Edit Selected");
        JButton deleteButton = new JButton("Delete Selected");
        JButton refreshButton = new JButton("Refresh");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEditBookDialog(BookManagementUI.this, "Add Book", null).setVisible(true);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = booksTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int bookID = (int) tableModel.getValueAt(selectedRow, 0);
                    String title = (String) tableModel.getValueAt(selectedRow, 1);
                    String author = (String) tableModel.getValueAt(selectedRow, 2);
                    String category = (String) tableModel.getValueAt(selectedRow, 3);
                    int stock = (int) tableModel.getValueAt(selectedRow, 4);
                    double price = (double) tableModel.getValueAt(selectedRow, 5);

                    Book book = new Book(bookID, title, author, category, stock, price);
                    new AddEditBookDialog(BookManagementUI.this, "Edit Book", book).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a book to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = booksTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this book?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        int bookID = (int) tableModel.getValueAt(selectedRow, 0);
                        bookDAO.deleteBook(bookID);
                        loadBooks();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a book to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBooks();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText().trim();
                if (!keyword.isEmpty()) {
                    loadBooks(bookDAO.searchBooks(keyword));
                } else {
                    loadBooks();
                }
            }
        });

        // Layout setup
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(addButton);
        topPanel.add(editButton);
        topPanel.add(deleteButton);
        topPanel.add(refreshButton);
        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Load all books
    public void loadBooks() {
        loadBooks(bookDAO.getAllBooks());
    }

    // Load specific list of books
    public void loadBooks(java.util.List<Book> books) {
        tableModel.setRowCount(0); // Clear existing data
        for (Book book : books) {
            Object[] row = {
                book.getBookID(),
                book.getTitle(),
                book.getAuthor(),
                book.getCategory(),
                book.getStockCount(),
                book.getPrice()
            };
            tableModel.addRow(row);
        }
    }
}

public class BookManagementApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Book Management");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.add(new BookManagementUI());
                frame.setVisible(true);
            }
        });
    }
}