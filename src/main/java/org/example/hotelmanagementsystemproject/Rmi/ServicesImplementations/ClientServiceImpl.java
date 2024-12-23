package org.example.hotelmanagementsystemproject.Rmi.ServicesImplementations;

import org.example.hotelmanagementsystemproject.Database.DatabaseConnection;
import org.example.hotelmanagementsystemproject.Rmi.CustomExceptions.ClientEmailNotFoundException;
import org.example.hotelmanagementsystemproject.Rmi.CustomExceptions.IncorrectPasswordException;
import org.example.hotelmanagementsystemproject.Rmi.Models.ChambreLine;
import org.example.hotelmanagementsystemproject.Rmi.Models.Client_;
import org.example.hotelmanagementsystemproject.Rmi.Models.Reservation;
import org.example.hotelmanagementsystemproject.Rmi.Services.ClientService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientServiceImpl extends UnicastRemoteObject implements ClientService {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public ClientServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public boolean registerClient(String firstname,
                                  String lastname,
                                  String email,
                                  String password,
                                  String statut
    ) throws RemoteException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO client_ (firstname, lastname, email,passwrd,statut) VALUES (?, ?, ?, ?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, email);
            stmt.setString(4, password);
            stmt.setString(5, statut);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Client_ getClientByEmail(String email) {
        Client_ client = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM client_ WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Integer clientId = rs.getInt("id");
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    String password = rs.getString("passwrd");
                    String status = rs.getString("statut");

                    client = new Client_(clientId, firstName, lastName,
                            email, password, status);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }


    @Override
    public int loginClient(String email, String password) {
        Client_ concerned = getClientByEmail(email);
        if (concerned == null) {
            throw new ClientEmailNotFoundException
                    ("pas de clients qui conviennent l'email enseigné");
        }
        if (!encoder.matches(password, concerned.getPassword())) {
            System.out.println(concerned.getPassword());
            throw new IncorrectPasswordException("Mot de passe incorrecte ");
        }
        return concerned.getId();
    }

    @Override
    public String getClientName(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM client_ WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getString("firstname") + " " + rs.getString("lastname");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public List<ChambreLine> fetchNonReserveChambres() {
        List<ChambreLine> chambreLignes = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "select chambre.id,chambre.surface, " +
                    "chambre.prixNuit, chambre.descr, " +
                    "chambre.createdByEmp as createdBy " +
                    "from chambre " +
                    "left join reservation on " +
                    "chambre.id = reservation.chambre_id " +
                    "where reservation.id is not null";

            try (PreparedStatement statement = conn.prepareStatement(query)) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    double surface = rs.getInt("surface");
                    Integer createdBy = rs.getInt("createdBy");
                    int prixNuit = rs.getInt("prixNuit");
                    String descr = rs.getString("descr");
                    ChambreLine line = new ChambreLine
                            (id, surface, prixNuit, "", createdBy, descr);
                    chambreLignes.add(line);
                }
            }
        } catch (SQLException exp) {
            System.out.println(exp.getMessage());
        }
        return chambreLignes;
    }

    @Override
    public boolean reserveChambre(Reservation rservation) throws RemoteException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "insert into " +
                    "reservation (client_id,chambre_id,frm,nbrPersonnes,nbrDays,answered,accepted) " +
                    "values (?,?,?,?,?,?,?)";
            // (Integer clientId, Integer chambreId, boolean accepted,
            // boolean answered, int nbrPersonnes,
            // String from, int nbrDays)
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, rservation.getClientId());
            stmt.setInt(2, rservation.getChambreId());
            stmt.setString(3, rservation.getFrom());
            stmt.setInt(4, rservation.getNbrPersonnes());
            stmt.setInt(5, rservation.getNbrDays());
            stmt.setBoolean(6, false);
            stmt.setBoolean(7, false);
            return stmt.executeUpdate() > 0;
        } catch (SQLException exp) {
            System.out.println(exp.getMessage());
        }
        return false;
    }

    @Override
    public List<Reservation> fetchReservations(int clientId) throws RemoteException {
        List<Reservation> reservations = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM reservation WHERE answered = false AND client_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int chambre_id = rs.getInt("chambre_id");
                int nbrDays = rs.getInt("nbrDays");
                int nbrPersonnes = rs.getInt("nbrPersonnes");
                String de = rs.getString("frm");
                int id = rs.getInt("id");
                Reservation r = new Reservation();
                r.setId(id);
                r.setChambreId(chambre_id);
                r.setNbrDays(nbrDays);
                r.setNbrPersonnes(nbrPersonnes);
                r.setFrom(de);

                reservations.add(r);
            }
            return reservations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void annulerReservation(int reservationId) throws RemoteException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "DELETE FROM reservation WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, reservationId);
            stmt.executeUpdate();
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public List<Reservation> getAllResponses(int clientId) throws RemoteException {
        List<Reservation> reservationsResp = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM reservation WHERE answered = true AND client_id = ? ";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, clientId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String from = rs.getString("frm");
                int nbrDays = rs.getInt("nbrDays");
                int nbrPersonnes = rs.getInt("nbrPersonnes");
                boolean accepted = rs.getBoolean("accepted");

                Reservation reservation = new Reservation();
                reservation.setId(id);
                reservation.setFrom(from);
                reservation.setNbrDays(nbrDays);
                reservation.setNbrPersonnes(nbrPersonnes);
                if (accepted) {
                    reservation.setStatus("Accepté");
                } else {
                    reservation.setStatus("Refusée");
                }
                reservationsResp.add(reservation);
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return reservationsResp;
    }

    @Override
    public List<Reservation> getHistReservations(int clientId) throws RemoteException {
        List<Reservation> ress = new ArrayList<>();
        try{
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM " +
                    "reservation " +
                    "WHERE reservation.client_id = ? " +
                    "AND reservation.accepted = true;";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int chambreId = rs.getInt("chambre_id");
                String de = rs.getString("frm");
                int nuits = rs.getInt("nbrDays");
                int nbrPersonnes = rs.getInt("nbrPersonnes");

                Reservation res = new Reservation();
                res.setId(rs.getInt("id"));
                res.setChambreId(chambreId);
                res.setFrom(de);
                res.setNbrDays(nuits);
                res.setNbrPersonnes(nbrPersonnes);

                ress.add(res);
            }
            return ress;
        }catch(Exception exp) {
            exp.printStackTrace();
        }
        return null;
    }
}
