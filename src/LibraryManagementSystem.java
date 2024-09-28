import classes.Book;
import classes.Librarian;
import classes.User;
import exceptions.NotAnAuthor;
import exceptions.UserNotFoundException;

import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class LibraryManagementSystem {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean newUser = false;
        int userId = 1;
        int bookId = 101;

        Librarian librarian = new Librarian();
        User user = null;
        welcome();

        System.out.println("Are you new here or already a user? (If you are new please type 'New User' below");
        try {
            String ans = sc.nextLine();
            if (Objects.equals(ans, "New User")) {
                newUser = true;
            } else if (Objects.equals(ans, "Already a user")) {
                newUser = false;
            } else {
                throw new UserNotFoundException("Sorry but we couldn't understand are you a user or not!");
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }

        if (newUser) {
            user = registerUser(userId, librarian);
            userId += 1;
        } else {
            System.out.print("Enter your username: ");
            String name = sc.nextLine();
            System.out.print("Enter your password: ");
            String pwd = sc.nextLine();
            try {
                System.out.println("Validating user...");
                user = signIn(user, name, pwd, librarian);
                System.out.println("User logged in successfully...");
                System.out.println();
            } catch (UserNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

        menu();
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        while (choice != 0) {
            switch (choice) {
                case 1:
                    System.out.println("Logging you out...");
                    user = null;
                    System.out.println("Successfully logged you out...");
                    sc.nextLine();
                    user = registerUser(userId, librarian);
                    userId++;
                    System.out.println();
                    menu();
                    break;
                case 2:
                    System.out.print("Enter your username: ");
                    sc.nextLine();
                    String name = sc.nextLine();
                    System.out.print("Enter your password: ");
                    String pwd = sc.nextLine();
                    try {
                        System.out.println("Validating user...");
                        user = signIn(user, name, pwd, librarian);
                        System.out.println("User successfully logged in...");
                        System.out.println();
                    } catch (UserNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    menu();
                    System.out.println();
                    break;
                case 3:
                    if (user != null)  {
                        System.out.print("Enter the name of book you want to borrow: ");
                        sc.nextLine();
                        String bkName = sc.nextLine();
                        borrowBook(user, bkName, librarian);
                    } else throw new UserNotFoundException("No user logged in!!!");
                    menu();
                    System.out.println();
                    break;
                case 4:
                    if (user != null) {
                        System.out.print("Enter the name of the book you want to purchase: ");
                        sc.nextLine();
                        String bkName2 = sc.nextLine();
                        purchaseBook(user, bkName2, librarian);
                    } else throw new UserNotFoundException("No user logged in!!!");
                    menu();
                    System.out.println();
                    break;
                case 5:
                    showBooks(librarian);
                    menu();
                    System.out.println();
                    break;
                case 6:
                    if (user != null) {
                        System.out.println("Registering you as an author...");
                        registerAuthor(user);
                        System.out.println("Author registration successful...");
                    }
                    else throw new UserNotFoundException("No user found!!!");
                    menu();
                    System.out.println();
                    break;
                case 7:
                    if (user != null) {
                        addBook(user, librarian, bookId);
                        bookId += 1;
                    } else throw new UserNotFoundException("No user logged in!!!");
                    menu();
                    System.out.println();
                    break;
                case 8:
                    if (user != null) {
                        System.out.print("Enter the bookId which you want to delete: ");
                        int bId = sc.nextInt();
                        deleteBook(user, librarian, bId);
                    } else throw new UserNotFoundException("No user logged in!!!");
                    menu();
                    System.out.println();
                    break;
                case 9:
                    showAllBooksSold(user, librarian);
                    menu();
                    System.out.println();
                    break;
                case 10:
                    System.out.println("Logging you out successfully");
                    user = null;
                    System.out.println();
                    menu();
                    break;
                default:
                    System.out.println("No valid input --- End of program!");
            }

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
        }

        System.out.println("Thanks for using the Book Worm :)");
        System.exit(0);
    }

    static void welcome() {
        System.out.println("*******WELCOME TO THE BOOK-WORM*******");
        System.out.println("The one place for all your books hunger!!!");
        System.out.println("To exit the system press 0");
        System.out.println("--------------------------------------------------------------------------------------------");
    }

    static User registerUser(int userId, Librarian librarian) {
        System.out.println("Kindly register yourself first ...");
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        User user = new User(name, email, userId);
        librarian.addUser(user);
        System.out.println("User registered successfully...");
        System.out.print("Add a password: ");
        String pwd = sc.nextLine();
        librarian.addUserCredential(user, name, pwd);
        System.out.println("User password saved successfully...");
        System.out.println();

        return user;
    }

    static void menu() {
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("1. Register New User");
        System.out.println("2. Sign In");
        System.out.println("3. Borrow a Book");
        System.out.println("4. Purchase a Book");
        System.out.println("5. Show books");
        System.out.println("---------------------FOR AUTHORS--------------------");
        System.out.println("6. Register as an author");
        System.out.println("7. Add a Book");
        System.out.println("8. Delete a Book");
        System.out.println("9. Show all books sold");
        System.out.println("10. Sign Out");
    }

    static User signIn (User user, String username, String password, Librarian librarian) {
        if (librarian.authenticateUser(username, password)) {
            for (User user1: librarian.usersList) {
                if (Objects.equals(user1.username, username)) {
                    user = user1;
                    break;
                }
            }

            return user;
        } else {
            throw new UserNotFoundException("Sorry! But this user doesn't exist in our database!!!");
        }
    }
    static void borrowBook(User user, String bookName, Librarian librarian) {
        librarian.borrowBook(user, bookName);
        System.out.println("Book borrowed successfully...");
    }

    static void purchaseBook(User user, String bookName, Librarian librarian) {
        librarian.sellBook(user, bookName);
        System.out.println("Book purchased successfully...");
        System.out.println();
    }

    static void showBooks(Librarian librarian) {
        librarian.showBooks();
        System.out.println();
    }

    static void registerAuthor(User author) {
        author.isAuthor = true;
        System.out.println("You have successfully registered as an author...");
        System.out.println();
    }

    static void addBook(User author, Librarian librarian, int bookId) {
        if (author.isAuthor) {
            sc.nextLine();
            System.out.print("Enter the book name: ");
            String bookName = sc.nextLine();
            System.out.print("Enter the book topic: ");
            String bookTopic = sc.nextLine();
            Date publishDate = new Date();

            Book book = new Book(bookName, author.username, bookTopic, publishDate, 1, bookId);
            librarian.saveBook(book);

            System.out.println("Book is added successfully...");
            System.out.println();
        } else {
            throw new NotAnAuthor("You are not an author!!!");
        }
    }

    static void deleteBook(User author, Librarian librarian, int bookId) {
        if (author.isAuthor) {
            librarian.deleteBook(bookId);
            System.out.println("Book is deleted successfully...");
            System.out.println();
        } else {
            throw new NotAnAuthor("You are not an author!!!");
        }
    }

    static void showAllBooksSold(User author, Librarian librarian) {
        if (author.isAuthor) {
            librarian.showBooksSold(author);
            System.out.println();
            menu();
        } else {
            throw new NotAnAuthor("You are not an author!!!");
        }
    }
}

// Improvements -
// 1. Quantity of Books will be more and in the initialisation process