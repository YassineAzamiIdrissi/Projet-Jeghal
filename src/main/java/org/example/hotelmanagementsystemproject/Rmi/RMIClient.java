package org.example.hotelmanagementsystemproject.Rmi;

import org.example.hotelmanagementsystemproject.Rmi.Models.*;
import org.example.hotelmanagementsystemproject.Rmi.Services.ClientService;
import org.example.hotelmanagementsystemproject.Rmi.Services.EmployeService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class RMIClient {
    private ClientService clientService;
    private EmployeService employeService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public RMIClient() {
        try {
            clientService = (ClientService) Naming.
                    lookup("rmi://localhost/ClientService");
            employeService = (EmployeService) Naming.
                    lookup("rmi://localhost/EmployeService");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean registerClient(Client_ client) {
        try {
            return clientService.registerClient
                    (client.getFirstname(),
                            client.getLastname(),
                            client.getEmail(),
                            encoder.encode(client.getPassword()),
                            client.getStatut());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int loginEmploye(String email, String password) throws RemoteException {
        return employeService.loginEmploye(email, password);
    }

    public int loginClient(String email, String password) throws RemoteException {
        return clientService.loginClient(email, password);
    }

    public String getEmpName(int empId) throws RemoteException {
        return employeService.fetchEmpName(empId);
    }

    public boolean addRoom(Chambre chambre) throws RemoteException {
        return employeService.addRoom(chambre);
    }

    public List<ChambreLine> fetchChambres() throws RemoteException {
        return employeService.fetchChambres();
    }

    public boolean updateRoom(Chambre chambre) throws RemoteException {
        return employeService.updateRoom(chambre);
    }

    public boolean isReserved(int id) throws RemoteException {
        return employeService.isReserved(id);
    }

    public boolean deleteChambre(int id) throws RemoteException {
        return employeService.deleteChambre(id);
    }

    public String getClinetName(int empId) throws RemoteException {
        return clientService.getClientName(empId);
    }

    public List<ChambreLine> fetchNonReserveChambres() throws RemoteException {
        return clientService.fetchNonReserveChambres();
    }

    public boolean reserverChambre(Reservation reservation) throws RemoteException {
        return clientService.reserveChambre(reservation);
    }

    public List<ClientLine> getAllClients() throws RemoteException {
        return employeService.getAllClients();
    }

    public List<ClientLine> getAllReservations() throws RemoteException {
        return employeService.getAllReservations();
    }

    public void accepterReservation(int reservationId) throws RemoteException {
        employeService.accepterReservation(reservationId);
    }

    public void refuserReservation(int reservationId) throws RemoteException {
        employeService.refuserReservation(reservationId);
    }

    public List<Reservation> fetchClientReservations(int clientId) throws RemoteException {
        return clientService.fetchReservations(clientId);
    }

    public void annulerReservation(int reservationId) throws RemoteException {
        clientService.annulerReservation(reservationId);
    }

    public List<Reservation> getAllResponses(int clientId) throws RemoteException {
        return clientService.getAllResponses(clientId);
    }

    public List<Reservation> getReservationsByClientId(String clientEmail) throws RemoteException {
        return employeService.getReservationsByClientId(clientEmail);
    }

    public List<Reservation> getHistReservations(int clientId) throws RemoteException {
        return clientService.getHistReservations(clientId);
    }
}

