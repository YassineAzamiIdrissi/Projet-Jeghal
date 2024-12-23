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

public class ListeClientsController {
    RMIClient client = new RMIClient();
    @FXML
    private TableView<ClientLine> chambreTableView;


    @FXML
    private TableColumn<ChambreLine, String> firstname;

    @FXML
    private TableColumn<ChambreLine, String> lastname;

    @FXML
    private TableColumn<ChambreLine, String> email;

    @FXML
    private TableColumn<ChambreLine, Integer> chambre;

    @FXML
    private TableColumn<ChambreLine, String> de;


    @FXML
    private TableColumn<ChambreLine, Integer> nbrNuits;

    @FXML
    public void initialize() throws RemoteException {
        firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        chambre.setCellValueFactory(new PropertyValueFactory<>("chambre"));
        de.setCellValueFactory(new PropertyValueFactory<>("de"));
        nbrNuits.setCellValueFactory(new PropertyValueFactory<>("nbrNuits"));
        ObservableList<ClientLine> chambreLignes = FXCollections.observableArrayList(getAllClients());
        chambreTableView.setItems(chambreLignes);

        chambreTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ClientLine selectedChambre = chambreTableView.getSelectionModel().getSelectedItem();
                if (selectedChambre != null) {
                    showHistoriquePage(selectedChambre);
                }
            }
        });
    }

    public List<ClientLine> getAllClients() throws RemoteException {
        return client.getAllClients();
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

    public void showHistoriquePage(ClientLine selectedChambre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/org/example/hotelmanagementsystemproject/EmployeSpace/HistoriqueReservations-view.fxml"));
            Parent root = loader.load();

            HistoriqueReservationsController detailsController = loader.getController();
            detailsController.setClient(selectedChambre);

            Stage stage = new Stage();
            stage.setTitle("Historique : ");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
