package org.example.hotelmanagementsystemproject.ViewControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.hotelmanagementsystemproject.Rmi.Models.ChambreLine;
import org.example.hotelmanagementsystemproject.Rmi.Models.ClientLine;
import org.example.hotelmanagementsystemproject.Rmi.Models.Reservation;
import org.example.hotelmanagementsystemproject.Rmi.RMIClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

public class HistoriqueReservationsController {
    RMIClient client = new RMIClient();

    private String clientEmail;

    @FXML
    private TableView<Reservation> chambreTableView;


    @FXML
    private TableColumn<Reservation, Integer> id;

    @FXML
    private TableColumn<Reservation, Integer> chambre;

    @FXML
    private TableColumn<Reservation, String> de;

    @FXML
    private TableColumn<Reservation, Integer> nuits;

    @FXML
    public void initialize() throws RemoteException {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        chambre.setCellValueFactory(new PropertyValueFactory<>("chambre"));
        de.setCellValueFactory(new PropertyValueFactory<>("de"));
        nuits.setCellValueFactory(new PropertyValueFactory<>("nbrNuits"));
        ObservableList<Reservation> chambreLignes = FXCollections.observableArrayList(fetchReservations());
        chambreTableView.setItems(chambreLignes);
        chambreTableView.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
    }



    public List<Reservation> fetchReservations() throws RemoteException {
        return client.getReservationsByClientId(clientEmail);
    }

    @FXML
    private void backToMain(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/org/example/hotelmanagementsystemproject/EmployeSpace/employeActions-view.fxml"));
            Parent registerRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(registerRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setClient(ClientLine selectedChambre) {
        clientEmail = selectedChambre.getEmail();
    }
}
