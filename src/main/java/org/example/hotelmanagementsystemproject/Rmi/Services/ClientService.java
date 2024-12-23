package org.example.hotelmanagementsystemproject.Rmi.Services;

import org.example.hotelmanagementsystemproject.Rmi.Models.ChambreLine;
import org.example.hotelmanagementsystemproject.Rmi.Models.Reservation;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientService extends Remote {
    boolean registerClient(String firstname,
                           String lastname,
                           String email,
                           String password,
                           String statut
    ) throws RemoteException;

    int loginClient(String email, String password) throws RemoteException;

    String getClientName(int clientId) throws RemoteException;

    List<ChambreLine> fetchNonReserveChambres() throws RemoteException;

    boolean reserveChambre(Reservation rservation) throws RemoteException;

    List<Reservation> fetchReservations(int clientId) throws RemoteException;

    void annulerReservation(int reservationId) throws RemoteException;

    List<Reservation> getAllResponses(int clientId) throws RemoteException;

    List<Reservation> getHistReservations(int clientId) throws RemoteException;

}



