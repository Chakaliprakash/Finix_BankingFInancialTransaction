package Exceptions;

public class Account_notFound extends RuntimeException {
    public Account_notFound(String message) {
        super(message);
    }
}
