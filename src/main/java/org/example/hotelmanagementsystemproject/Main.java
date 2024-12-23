package org.example.hotelmanagementsystemproject;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Main {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        System.out.println(passwordEncoder.encode("password_10"));
    }
}
