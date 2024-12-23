package org.example.hotelmanagementsystemproject.Rmi.Models;

public class User {
    protected Integer id;
    protected String firstname;
    protected String lastname;
    protected String password;
    protected String email;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
