package exceptions;

public class NotAnAuthor extends RuntimeException {
    public NotAnAuthor(String message) {
        super(message);
    }
}
