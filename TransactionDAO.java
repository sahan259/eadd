// TransactionDAO.java
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    // Borrow a book
    public void borrowBook(int borrowerID, int bookID) {
        String insertTransaction = "INSERT INTO Transactions (BorrowerID, BookID, BorrowDate) VALUES (?, ?, ?)";
        String updateBookStock = "UPDATE Books SET StockCount = StockCount - 1 WHERE BookID = ?";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false); // Start transaction

            // Insert into Transactions
            try (PreparedStatement stmt = con.prepareStatement(insertTransaction)) {
                stmt.setInt(1, borrowerID);
                stmt.setInt(2, bookID);
                stmt.setDate(3, Date.valueOf(LocalDate.now()));
                stmt.executeUpdate();
            }

            // Update Books stock
            try (PreparedStatement stmt = con.prepareStatement(updateBookStock)) {
                stmt.setInt(1, bookID);
                stmt.executeUpdate();
            }

            con.commit(); // Commit transaction
            System.out.println("Book borrowed successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                DBConnection.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                DBConnection.getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Return a book
    public void returnBook(int transactionID) {
        String updateTransaction = "UPDATE Transactions SET ReturnDate = ?, FineAmount = ? WHERE TransactionID = ?";
        String updateBookStock = "UPDATE Books SET StockCount = StockCount + 1 WHERE BookID = ?";
        String selectTransaction = "SELECT BookID, BorrowDate FROM Transactions WHERE TransactionID = ?";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false); // Start transaction

            // Retrieve transaction details
            int bookID = 0;
            LocalDate borrowDate = null;
            try (PreparedStatement stmt = con.prepareStatement(selectTransaction)) {
                stmt.setInt(1, transactionID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    bookID = rs.getInt("BookID");
                    borrowDate = rs.getDate("BorrowDate").toLocalDate();
                } else {
                    System.out.println("Transaction not found!");
                    return;
                }
            }

            // Calculate fine
            LocalDate returnDate = LocalDate.now();
            long daysLate = returnDate.toEpochDay() - borrowDate.plusDays(14).toEpochDay(); // 14 days borrowing period
            double fine = daysLate > 0 ? daysLate * 2.0 : 0.0; // $2 per day late

            // Update Transactions
            try (PreparedStatement stmt = con.prepareStatement(updateTransaction)) {
                stmt.setDate(1, Date.valueOf(returnDate));
                stmt.setDouble(2, fine);
                stmt.setInt(3, transactionID);
                stmt.executeUpdate();
            }

            // Update Books stock
            try (PreparedStatement stmt = con.prepareStatement(updateBookStock)) {
                stmt.setInt(1, bookID);
                stmt.executeUpdate();
            }

            con.commit(); // Commit transaction
            System.out.println("Book returned successfully! Fine: $" + fine);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                DBConnection.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                DBConnection.getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Additional methods like listing transactions can be added
}
