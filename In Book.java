// In Book.java
@Override
public String toString() {
    return this.title + " by " + this.author;
}

// In Borrower.java
@Override
public String toString() {
    return this.name;
}
