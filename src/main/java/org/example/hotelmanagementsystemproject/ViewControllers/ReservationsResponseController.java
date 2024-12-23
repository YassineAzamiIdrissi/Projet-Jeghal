package org.example.hotelmanagementsystemproject.ViewControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.hotelmanagementsystemproject.Rmi.Models.ChambreLine;
import org.example.hotelmanagementsystemproject.Rmi.Models.Reservation;
import org.example.hotelmanagementsystemproject.Rmi.RMIClient;
import org.example.hotelmanagementsystemproject.Rmi.SessionManagement.SessionManager;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

public class ReservationsResponseController {
    RMIClient client = new RMIClient();

    @FXML
    private TableView<Reservation> chambreTableView;

    @FXML
    private TableColumn<Reservation, String> from;

    @FXML
    private TableColumn<Reservation, Integer> id;

    @FXML
    private TableColumn<Reservation, Integer> chambre;

    @FXML
    private TableColumn<Reservation, Integer> nbrPersonnes;

    @FXML
    private TableColumn<Reservation, Integer> nbrDays;

    @FXML
    private TableColumn<Reservation, String> status;

    @FXML
    public void initialize() throws RemoteException {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        from.setCellValueFactory(new PropertyValueFactory<>("from"));
        chambre.setCellValueFactory(new PropertyValueFactory<>("chambreId"));
        nbrDays.setCellValueFactory(new PropertyValueFactory<>("nbrDays"));
        nbrPersonnes.setCellValueFactory(new PropertyValueFactory<>("nbrPersonnes"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        ObservableList<Reservation> chambreLignes = FXCollections.observableArrayList(fetchClientReponses());
        chambreTableView.setItems(chambreLignes);
        chambreTableView.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
    }

    List<Reservation> fetchClientReponses() throws RemoteException {
        return client.getAllResponses(SessionManager.getSession().getId());
    }

    @FXML
    private void backToMain(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/org/example/hotelmanagementsystemproject/ClientSpace/clientActions-view.fxml"));
            Parent registerRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(registerRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
