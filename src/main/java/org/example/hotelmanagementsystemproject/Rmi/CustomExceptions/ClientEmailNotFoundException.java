package org.example.hotelmanagementsystemproject.Rmi.CustomExceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ClientEmailNotFoundException extends UsernameNotFoundException {
    public ClientEmailNotFoundException(String message) {
        super(message);
    }
}
