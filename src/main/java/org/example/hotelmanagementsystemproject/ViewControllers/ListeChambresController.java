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
import org.example.hotelmanagementsystemproject.Rmi.RMIClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class ListeChambresController {
    RMIClient client = new RMIClient();

    @FXML
    private TableView<ChambreLine> chambreTableView;

    @FXML
    private TableColumn<ChambreLine, Integer> idColumn;

    @FXML
    private TableColumn<ChambreLine, Double> surfaceColumn;

    @FXML
    private TableColumn<ChambreLine, Integer> prixColumn;

    @FXML
    private TableColumn<ChambreLine, Boolean> isTakenColumn;

    @FXML
    private TableColumn<ChambreLine, Integer> createdByColumn;

    @FXML
    private TableColumn<ChambreLine, String> description;

    private void showDetailsPage(ChambreLine chambre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/org/example/hotelmanagementsystemproject/EmployeSpace/chambreDetails-view.fxml"));
            Parent root = loader.load();

            ChambreDetailsController detailsController = loader.getController();
            detailsController.setChambre(chambre);

            Stage stage = new Stage();
            stage.setTitle("Détails de la chambre");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() throws RemoteException {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        surfaceColumn.setCellValueFactory(new PropertyValueFactory<>("surface"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prixNuit"));
        isTakenColumn.setCellValueFactory(new PropertyValueFactory<>("taken"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        description.setCellValueFactory(new PropertyValueFactory<>("descr"));
        ObservableList<ChambreLine> chambreLignes = FXCollections.observableArrayList(fetchChambres());
        chambreTableView.setItems(chambreLignes);

        chambreTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ChambreLine selectedChambre = chambreTableView.getSelectionModel().getSelectedItem();
                if (selectedChambre != null) {
                    showDetailsPage(selectedChambre);
                }
            }
        });
    }

    public List<ChambreLine> fetchChambres() throws RemoteException {
        return client.fetchChambres();
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
}
