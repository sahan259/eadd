// BorrowerDAO.java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowerDAO {
    // Add a new borrower
    public void addBorrower(Borrower borrower) {
        String query = "INSERT INTO Borrowers (Name, ContactNumber, Email) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, borrower.getName());
            stmt.setString(2, borrower.getContactNumber());
            stmt.setString(3, borrower.getEmail());
            stmt.executeUpdate();
            System.out.println("Borrower added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // List all borrowers
    public List<Borrower> getAllBorrowers() {
        List<Borrower> borrowers = new ArrayList<>();
        String query = "SELECT * FROM Borrowers";
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Borrower borrower = new Borrower(
                    rs.getInt("BorrowerID"),
                    rs.getString("Name"),
                    rs.getString("ContactNumber"),
                    rs.getString("Email")
                );
                borrowers.add(borrower);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowers;
    }

    // Additional CRUD operations can be implemented similarly
}
