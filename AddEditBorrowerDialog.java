// AddEditBorrowerDialog.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddEditBorrowerDialog extends JDialog {
    private JTextField nameField, contactField, emailField;
    private JButton saveButton, cancelButton;
    private BorrowerDAO borrowerDAO;
    private BorrowerManagementUI parent;
    private Borrower borrower; // null for add, non-null for edit

    public AddEditBorrowerDialog(BorrowerManagementUI parent, String title, Borrower borrower) {
        super(parent, title, true);
        this.parent = parent;
        this.borrower = borrower;
        borrowerDAO = new BorrowerDAO();

        setSize(400, 300);
        setLocationRelativeTo(parent);

        // Create components
        JLabel nameLabel = new JLabel("Name:");
        JLabel contactLabel = new JLabel("Contact Number:");
        JLabel emailLabel = new JLabel("Email:");

        nameField = new JTextField(20);
        contactField = new JTextField(20);
        emailField = new JTextField(20);

        if (borrower != null) { // If editing, populate fields
            nameField.setText(borrower.getName());
            contactField.setText(borrower.getContactNumber());
            emailField.setText(borrower.getEmail());
        }

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        // Add action listeners
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate inputs
                String name = nameField.getText().trim();
                String contact = contactField.getText().trim();
                String email = emailField.getText().trim();

                if (name.isEmpty() || contact.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all required fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (borrower == null) { // Add
                    Borrower newBorrower = new Borrower(0, name, contact, email);
                    borrowerDAO.addBorrower(newBorrower);
                } else { // Edit
                    borrower.setName(name);
                    borrower.setContactNumber(contact);
                    borrower.setEmail(email);
                    // Implement update method in BorrowerDAO if needed
                }

                parent.loadBorrowers();
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
        panel.add(nameLabel, gbc);

        gbc.gridy = 1;
        panel.add(contactLabel, gbc);

        gbc.gridy = 2;
        panel.add(emailLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);

        gbc.gridy = 1;
        panel.add(contactField, gbc);

        gbc.gridy = 2;
        panel.add(emailField, gbc);

        // Buttons
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveButton, gbc);

        gbc.gridy = 4;
        panel.add(cancelButton, gbc);

        add(panel);
    }
}
