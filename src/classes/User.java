package classes;

public class User {

    public String username;
    int userId;
    String email;
    int noOfBorrowedBooks;
    int noOfPurchasedBooks;

    public boolean isAuthor;
    int noOfBooksPublished;

    public User(String username, String email, int id) {
        this.username = username;
        this.email = email;
        this.userId = id;
        this.isAuthor = false;
    }
}
