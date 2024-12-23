package org.example.hotelmanagementsystemproject.Rmi.CustomExceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class EmployeEmailNotFoundException extends UsernameNotFoundException {
    public EmployeEmailNotFoundException(String message) {
        super(message);
    }
}
