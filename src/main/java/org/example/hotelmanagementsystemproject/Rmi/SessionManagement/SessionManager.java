package org.example.hotelmanagementsystemproject.Rmi.SessionManagement;

public class SessionManager {
    private static SessionRep session = new SessionRep();

    public static void setSession(UserType type, int id) {
        session.setType(type);
        session.setId(id);
    }

    public static SessionRep getSession() {
        return session;
    }

    public static void logout() {
        session =  new SessionRep();
    }


}
