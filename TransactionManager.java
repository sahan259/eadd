import java.sql.*;
import java.time.LocalDate;

public class TransactionManager {
    public void borrowBook(int borrowerID, int bookID) {
        try (Connection con = DBConnection.getConnection()) {
            String borrowQuery = "INSERT INTO Transactions (BorrowerID, BookID, BorrowDate) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(borrowQuery);
            stmt.setInt(1, borrowerID);
            stmt.setInt(2, bookID);
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            stmt.executeUpdate();

            String updateStock = "UPDATE Books SET StockCount = StockCount - 1 WHERE BookID = ?";
            PreparedStatement updateStmt = con.prepareStatement(updateStock);
            updateStmt.setInt(1, bookID);
            updateStmt.executeUpdate();

            System.out.println("Book borrowed successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook(int transactionID) {
        try (Connection con = DBConnection.getConnection()) {
            String updateQuery = "UPDATE Transactions SET ReturnDate = ? WHERE TransactionID = ?";
            PreparedStatement stmt = con.prepareStatement(updateQuery);
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setInt(2, transactionID);
            stmt.executeUpdate();

            // Calculate Fine
            String fineQuery = "SELECT BorrowDate FROM Transactions WHERE TransactionID = ?";
            PreparedStatement fineStmt = con.prepareStatement(fineQuery);
            fineStmt.setInt(1, transactionID);
            ResultSet rs = fineStmt.executeQuery();

            if (rs.next()) {
                LocalDate borrowDate = rs.getDate("BorrowDate").toLocalDate();
                LocalDate returnDate = LocalDate.now();
                long daysLate = returnDate.toEpochDay() - borrowDate.plusDays(14).toEpochDay();
                double fine = daysLate > 0 ? daysLate * 2.0 : 0.0;

                String updateFine = "UPDATE Transactions SET FineAmount = ? WHERE TransactionID = ?";
                PreparedStatement fineUpdateStmt = con.prepareStatement(updateFine);
                fineUpdateStmt.setDouble(1, fine);
                fineUpdateStmt.setInt(2, transactionID);
                fineUpdateStmt.executeUpdate();
            }

            System.out.println("Book returned successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
