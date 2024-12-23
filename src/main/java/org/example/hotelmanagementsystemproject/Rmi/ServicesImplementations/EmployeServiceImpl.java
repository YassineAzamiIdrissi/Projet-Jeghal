package org.example.hotelmanagementsystemproject.Rmi.ServicesImplementations;

import org.example.hotelmanagementsystemproject.Database.DatabaseConnection;
import org.example.hotelmanagementsystemproject.Rmi.CustomExceptions.EmployeEmailNotFoundException;
import org.example.hotelmanagementsystemproject.Rmi.CustomExceptions.IncorrectPasswordException;
import org.example.hotelmanagementsystemproject.Rmi.Models.*;
import org.example.hotelmanagementsystemproject.Rmi.Services.EmployeService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeServiceImpl extends UnicastRemoteObject
        implements EmployeService {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public EmployeServiceImpl() throws RemoteException {
        super();
    }

    public Employe getEmployeByEmail(String email) {
        Employe employee = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM employe WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Integer empId = rs.getInt("id");
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    String password = rs.getString("passwrd");
                    employee = new Employe(empId, firstName, lastName, password, email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public int loginEmploye(String email, String password) {
        Employe employee = getEmployeByEmail(email);
        if (employee == null) {
            throw new EmployeEmailNotFoundException
                    ("pas d'employés qui conviennent l'email enseigné");
        }
        String empPassword = employee.getPassword();
        boolean validPassword = encoder.matches(password, empPassword);
        if (!validPassword) {
            throw new IncorrectPasswordException("Incorrect password entered");
        }
        return employee.getId();
    }

    @Override
    public String fetchEmpName(int empId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM employe WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, empId);
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
    public boolean addRoom(Chambre chambre) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO chambre (surface, prixNuit, createdByEmp,descr) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDouble(1, chambre.getSurface());
            stmt.setInt(2, chambre.getPrixNuit());
            stmt.setInt(3, chambre.getCreateBy());
            stmt.setString(4, chambre.getDescription());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ChambreLine> fetchChambres() {
        List<ChambreLine> chambreLignes = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT chambre.id, chambre.surface, chambre.createdByEmp, chambre.prixNuit,chambre.descr, " +
                    "reservation.id IS NOT NULL AS isTaken " +
                    "FROM chambre " +
                    "LEFT JOIN reservation ON chambre.id = reservation.chambre_id";

            try (PreparedStatement statement = conn.prepareStatement(query)) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    double surface = rs.getInt("surface");
                    int prixNuit = rs.getInt("prixNuit");
                    Integer createdBy = rs.getInt("createdByEmp");
                    boolean isTaken = rs.getBoolean("isTaken");
                    String taken = isTaken ? "oui" : "non";
                    String descr = rs.getString("descr");
                    ChambreLine line = new ChambreLine
                            (id, surface, prixNuit, taken, createdBy, descr);
                    chambreLignes.add(line);
                }
            }
        } catch (SQLException exp) {
            System.out.println(exp.getMessage());
        }
        return chambreLignes;
    }

    @Override
    public boolean updateRoom(Chambre chambre) {
        String query = "UPDATE chambre SET surface = ?, prixNuit = ?, descr = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDouble(1, chambre.getSurface());
                stmt.setInt(2, chambre.getPrixNuit());
                stmt.setString(3, chambre.getDescription());
                stmt.setInt(4, chambre.getId());

                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isReserved(int id) throws RemoteException {
        String query = "SELECT COUNT(*) FROM reservation " +
                "WHERE reservation.chambre_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException exp) {
            System.err.println("Database error: " + exp.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteChambre(int id) throws RemoteException {
        String query = "DELETE FROM chambre WHERE chambre.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException exp) {
            System.err.println("Database error during deleteChambre: " + exp.getMessage());
        }
        return false;
    }

    @Override
    public List<ClientLine> getAllClients() throws RemoteException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "select client_.firstname," +
                    "client_.lastname,client_.email," +
                    "reservation.chambre_id as chambre," +
                    "reservation.frm as de," +
                    "reservation.nbrDays nuits  from" +
                    " client_ inner join" +
                    " reservation on client_.id = reservation.client_id" +
                    " and reservation.accepted = true; ";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            List<ClientLine> lines = new ArrayList<>();
            while (rs.next()) {
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String email = rs.getString("email");
                int chambreId = rs.getInt("chambre");
                String de = rs.getString("de");
                int nuits = rs.getInt("nuits");
                ClientLine line = new ClientLine(
                        firstname,
                        lastname,
                        email,
                        chambreId,
                        de,
                        nuits
                );
                lines.add(line);
            }
            return lines;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ClientLine> getAllReservations() throws RemoteException {
        ArrayList<ClientLine> lines = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "select reservation.id as resId," +
                    "concat(client_.firstname,\" \",client_.lastname) " +
                    "as fullname, reservation.chambre_id as chambre, " +
                    "reservation.frm as de, " +
                    "reservation.nbrDays as nuits " +
                    "from reservation " +
                    "inner join client_ on " +
                    "client_.id = reservation.client_id " +
                    "where answered = false; ";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ClientLine line = new ClientLine();
                line.setId(rs.getInt("resId"));
                line.setChambre(rs.getInt("chambre"));
                line.setFullName(rs.getString("fullname"));
                line.setDe(rs.getString("de"));
                line.setNbrNuits(rs.getInt("nuits"));
                lines.add(line);
            }
            return lines;
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        return null;
    }

    @Override
    public void accepterReservation(int reservationId) throws RemoteException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "UPDATE reservation " +
                    "SET accepted = true, " +
                    "answered = true " +
                    "WHERE id = ?;";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, reservationId);
            stmt.executeUpdate();
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void refuserReservation(int reservationId) throws RemoteException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "update reservation " +
                    "set reservation.accepted = false, " +
                    "reservation.answered = true" +
                    "where id = ?; ";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, reservationId);
            stmt.executeUpdate();
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public List<Reservation> getReservationsByClientId(String clientEmail) throws RemoteException {
        List<Reservation> reservations = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query =
                    "SELECT * FROM reservation INNER JOIN client_ " +
                            "ON reservation.client_id = client_.id " +
                            "WHERE client_.email = ? AND " +
                            "reservation.accepted = true ";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, clientEmail);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int chambreId = rs.getInt("chambre_id");
                String de = rs.getString("frm");
                int nbrPersonne = rs.getInt("nbrPersonnes");
                int nuits = rs.getInt("nbrDays");

                Reservation reservation = new Reservation();
                reservation.setId(id);
                reservation.setChambreId(chambreId);
                reservation.setNbrPersonnes(nbrPersonne);
                reservation.setNbrDays(nuits);
                reservation.setFrom(de);

                reservations.add(reservation);
            }
            return reservations;
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        return null;
    }
}
