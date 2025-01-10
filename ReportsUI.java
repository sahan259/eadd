// ReportsUI.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ReportsUI extends JFrame {
    private JButton mostBorrowedBooksButton;
    private JTable reportTable;
    private DefaultTableModel tableModel;

    public ReportsUI() {
        setTitle("Reports");
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the frame

        // Report Buttons
        mostBorrowedBooksButton = new JButton("Most Borrowed Books");

        // Table setup
        String[] columnNames = {"Book Title", "Author", "Times Borrowed"};
        tableModel = new DefaultTableModel(columnNames, 0);
        reportTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(reportTable);

        // Add action listeners
        mostBorrowedBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateMostBorrowedBooksReport();
            }
        });

        // Layout setup
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(mostBorrowedBooksButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void generateMostBorrowedBooksReport() {
        // Clear existing data
        tableModel.setRowCount(0);

        // Fetch data from the database
        String query = "SELECT b.Title, b.Author, COUNT(t.TransactionID) AS TimesBorrowed " +
                       "FROM Books b JOIN Transactions t ON b.BookID = t.BookID " +
                       "GROUP BY b.BookID " +
                       "ORDER BY TimesBorrowed DESC " +
                       "LIMIT 10";

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String title = rs.getString("Title");
                String author = rs.getString("Author");
                int timesBorrowed = rs.getInt("TimesBorrowed");

                Object[] row = {title, author, timesBorrowed};
                tableModel.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error generating report.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
