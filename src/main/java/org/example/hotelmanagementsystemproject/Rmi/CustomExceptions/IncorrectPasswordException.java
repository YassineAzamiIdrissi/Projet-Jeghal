package org.example.hotelmanagementsystemproject.Rmi.CustomExceptions;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
