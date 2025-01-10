// AddEditBookDialog.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddEditBookDialog extends JDialog {
    private JTextField titleField, authorField, categoryField, stockField, priceField;
    private JButton saveButton, cancelButton;
    private BookDAO bookDAO;
    private BookManagementUI parent;
    private Book book; // null for add, non-null for edit

    public AddEditBookDialog(BookManagementUI parent, String title, Book book) {
        super(parent, title, true);
        this.parent = parent;
        this.book = book;
        bookDAO = new BookDAO();

        setSize(400, 300);
        setLocationRelativeTo(parent);

        // Create components
        JLabel titleLabel = new JLabel("Title:");
        JLabel authorLabel = new JLabel("Author:");
        JLabel categoryLabel = new JLabel("Category:");
        JLabel stockLabel = new JLabel("Stock Count:");
        JLabel priceLabel = new JLabel("Price:");

        titleField = new JTextField(20);
        authorField = new JTextField(20);
        categoryField = new JTextField(20);
        stockField = new JTextField(20);
        priceField = new JTextField(20);

        if (book != null) { // If editing, populate fields
            titleField.setText(book.getTitle());
            authorField.setText(book.getAuthor());
            categoryField.setText(book.getCategory());
            stockField.setText(String.valueOf(book.getStockCount()));
            priceField.setText(String.valueOf(book.getPrice()));
        }

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        // Add action listeners
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate inputs
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String category = categoryField.getText().trim();
                String stockStr = stockField.getText().trim();
                String priceStr = priceField.getText().trim();

                if (title.isEmpty() || author.isEmpty() || stockStr.isEmpty() || priceStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all required fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int stock;
                double price;
                try {
                    stock = Integer.parseInt(stockStr);
                    price = Double.parseDouble(priceStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid number format for stock or price.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (book == null) { // Add
                    Book newBook = new Book(0, title, author, category, stock, price);
                    bookDAO.addBook(newBook);
                } else { // Edit
                    book.setTitle(title);
                    book.setAuthor(author);
                    book.setCategory(category);
                    book.setStockCount(stock);
                    book.setPrice(price);
                    bookDAO.updateBook(book);
                }

                parent.loadBooks();
                dispose();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        // Layout setup
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Labels and fields
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        gbc.gridy = 1;
        panel.add(authorLabel, gbc);

        gbc.gridy = 2;
        panel.add(categoryLabel, gbc);

        gbc.gridy = 3;
        panel.add(stockLabel, gbc);

        gbc.gridy = 4;
        panel.add(priceLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(titleField, gbc);

        gbc.gridy = 1;
        panel.add(authorField, gbc);

        gbc.gridy = 2;
        panel.add(categoryField, gbc);

        gbc.gridy = 3;
        panel.add(stockField, gbc);

        gbc.gridy = 4;
        panel.add(priceField, gbc);

        // Buttons
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveButton, gbc);

        gbc.gridy = 6;
        panel.add(cancelButton, gbc);

        add(panel);
    }
}
