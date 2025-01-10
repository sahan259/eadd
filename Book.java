// Book.java
public class Book {
    private int bookID;
    private String title;
    private String author;
    private String category;
    private int stockCount;
    private double price;

    // Constructors, Getters, and Setters
    public Book() { }

    public Book(int bookID, String title, String author, String category, int stockCount, double price) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.category = category;
        this.stockCount = stockCount;
        this.price = price;
    }

    // Getters and Setters...
}
