package classes;

import interfaces.Sellable;

import java.util.ArrayList;
import java.util.Date;

public class Book implements Sellable {

    String bookName;
    int bookId;
    String author;
    String topic;
    Date publishDate;
    int availableQuantity;
    int noOfSoldCopies;
    ArrayList<String> currentReaders = new ArrayList<>();
    ArrayList<String> bookPurchasers = new ArrayList<>();


    public void sellBook(Book book, User user) {
        if (book.availableQuantity > 0) {
            book.availableQuantity -= 1;
            book.noOfSoldCopies += 1;
            book.bookPurchasers.add(user.username);
        }
        else System.out.println("Sorry! But this book is not available.");
    }

    public Book(String bookName, String author, String bookTopic, Date publishDate, int availableQuantity, int bookId) {
        this.bookName = bookName;
        this.author = author;
        this.topic = bookTopic;
        this.publishDate = publishDate;
        this.availableQuantity = availableQuantity;
        this.bookId = bookId;
    }
}
