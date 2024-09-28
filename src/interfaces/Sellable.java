package interfaces;

import classes.Book;
import classes.User;

public interface Sellable {
    void sellBook(Book book, User user);
}
