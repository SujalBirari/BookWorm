package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Librarian {
    public ArrayList<User> usersList = new ArrayList<>();
    HashMap<String, String> credentials = new HashMap<>();
    ArrayList<Book> booksList = new ArrayList<>();

    public void addUser(User user) {
        usersList.add(user);
        System.out.println();
    }

    public void addUserCredential(User user, String username, String password) {
        if (credentials.containsKey(username)) System.out.println("User is already present in the system");
        else {
            usersList.add(user);
            credentials.put(username, password);
        }
        System.out.println();
    }

    public boolean authenticateUser(String username, String password) {
        return Objects.equals(password, credentials.get(username));
    }

    public void deleteUser(int userId) {
        for (User user: usersList) {
            if (user.userId == userId) {
                usersList.remove(user);
                break;
            }
        }
        System.out.println();
    }

    public void saveBook(Book book) {
        booksList.add(book);
        System.out.println();
    }

    public void showBooks() {
        System.out.printf("%-30s %-25s %-30s %-20s\n", "Book Name", "Book Author", "Publish Date", "Available Quantity");
        System.out.println("-------------------------------------------------------------------------------------------------------");

        for (Book book : booksList) {
            System.out.printf("%-30s %-25s %-30s %-20d\n", book.bookName, book.author, book.publishDate, book.availableQuantity);
        }

        System.out.println();
    }

    public void deleteBook(int bookId) {
        for (Book book: booksList) {
            if (book.bookId == bookId) {
                booksList.remove(book);
                break;
            }
        }
        System.out.println();
    }

    public void showBooksSold(User author) {
        System.out.printf("You have sold %d books. (User ID: %d)\n", author.noOfBooksPublished, author.userId);
        System.out.printf("%-30s %-20s\n", "Book Name", "No of Copies Sold");
        System.out.println("----------------------------------------------------------");

        for (Book book : booksList) {
            if (book.noOfSoldCopies > 0 && Objects.equals(book.author, author.username)) {
                System.out.printf("%-30s %-20d\n", book.bookName, book.noOfSoldCopies);
            }
        }

        System.out.println();
    }

    public void borrowBook(User user, String bookName) {
        for (Book bk: booksList) {
            if (Objects.equals(bk.bookName, bookName) && (bk.availableQuantity > 0)) {
                bk.currentReaders.add(user.username);
                bk.availableQuantity -= 1;
                user.noOfBorrowedBooks  += 1;
                break;
            }
        }
        System.out.println();
    }

    public void sellBook(User user, String bookName) {
        for (Book book: booksList) {
            if (Objects.equals(book.bookName, bookName) && (book.availableQuantity > 0)) {
                book.bookPurchasers.add(user.username);
                book.noOfSoldCopies += 1;
                book.availableQuantity -= 1;
                user.noOfPurchasedBooks += 1;
            }
            break;
        }
        System.out.println();
    }
}
