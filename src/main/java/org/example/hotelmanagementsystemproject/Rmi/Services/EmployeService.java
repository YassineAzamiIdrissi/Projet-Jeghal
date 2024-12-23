package org.example.hotelmanagementsystemproject.Rmi.Services;

import org.example.hotelmanagementsystemproject.Rmi.Models.Chambre;
import org.example.hotelmanagementsystemproject.Rmi.Models.ChambreLine;
import org.example.hotelmanagementsystemproject.Rmi.Models.ClientLine;
import org.example.hotelmanagementsystemproject.Rmi.Models.Reservation;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface EmployeService extends Remote {
    int loginEmploye(String email, String password) throws RemoteException;

    String fetchEmpName(int empId) throws RemoteException;

    boolean addRoom(Chambre chambre) throws RemoteException;

    List<ChambreLine> fetchChambres() throws RemoteException;

    boolean updateRoom(Chambre chambre) throws RemoteException;

    boolean isReserved(int id) throws RemoteException;

    boolean deleteChambre(int id) throws RemoteException;

    List<ClientLine> getAllClients() throws RemoteException;

    List<ClientLine> getAllReservations() throws RemoteException;

    void accepterReservation(int reservationId) throws RemoteException;

    void refuserReservation(int reservationId) throws RemoteException;

    List<Reservation> getReservationsByClientId(String clientEmail) throws RemoteException;
}
