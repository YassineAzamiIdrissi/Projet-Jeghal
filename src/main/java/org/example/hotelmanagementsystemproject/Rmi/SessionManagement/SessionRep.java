package org.example.hotelmanagementsystemproject.Rmi.SessionManagement;

public class SessionRep {
    UserType type;
    int id;

    public SessionRep() {
    }

    public SessionRep(UserType type, int id) {
        this.type = type;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
