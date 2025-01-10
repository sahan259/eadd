// Transaction.java
import java.time.LocalDate;

public class Transaction {
    private int transactionID;
    private int borrowerID;
    private int bookID;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private double fineAmount;

    // Constructors, Getters, and Setters
    public Transaction() { }

    public Transaction(int transactionID, int borrowerID, int bookID, LocalDate borrowDate, LocalDate returnDate, double fineAmount) {
        this.transactionID = transactionID;
        this.borrowerID = borrowerID;
        this.bookID = bookID;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.fineAmount = fineAmount;
    }

    // Getters and Setters...
}
