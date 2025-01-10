import java.sql.*;

public class BookManager {
    public void addBook(String title, String author, String category, int stockCount, double price) {
        try (Connection con = DBConnection.getConnection()) {
            String query = "INSERT INTO Books (Title, Author, Category, StockCount, Price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, category);
            stmt.setInt(4, stockCount);
            stmt.setDouble(5, price);
            stmt.executeUpdate();
            System.out.println("Book added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listBooks() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM Books";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("Books List:");
            while (rs.next()) {
                System.out.printf("ID: %d, Title: %s, Author: %s, Category: %s, Stock: %d, Price: %.2f%n",
                    rs.getInt("BookID"), rs.getString("Title"), rs.getString("Author"),
                    rs.getString("Category"), rs.getInt("StockCount"), rs.getDouble("Price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
