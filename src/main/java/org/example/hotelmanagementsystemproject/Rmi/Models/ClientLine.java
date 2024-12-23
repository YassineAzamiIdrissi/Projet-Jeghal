package org.example.hotelmanagementsystemproject.Rmi.Models;

import java.io.Serializable;

public class ClientLine implements Serializable {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private int chambre;
    private String de;
    private int nbrNuits;
    private String fullName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public ClientLine() {

    }
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getChambre() {
        return chambre;
    }

    public void setChambre(int chambre) {
        this.chambre = chambre;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public int getNbrNuits() {
        return nbrNuits;
    }

    public void setNbrNuits(int nbrNuits) {
        this.nbrNuits = nbrNuits;
    }

    public ClientLine(String firstname, String lastname, String email,
                      int chambre, String de,
                      int nbrNuits) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.chambre = chambre;
        this.de = de;
        this.nbrNuits = nbrNuits;
    }
}
