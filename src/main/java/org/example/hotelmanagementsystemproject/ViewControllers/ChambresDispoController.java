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

public class ChambresDispoController {
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
    private TableColumn<ChambreLine, Integer> createdByColumn;

    @FXML
    private TableColumn<ChambreLine, String> description;

    public List<ChambreLine> fetchChambres() throws RemoteException {
        return client.fetchNonReserveChambres();
    }

    @FXML
    public void initialize() throws RemoteException {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        surfaceColumn.setCellValueFactory(new PropertyValueFactory<>("surface"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prixNuit"));
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

    private void showDetailsPage(ChambreLine chambre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/org/example/hotelmanagementsystemproject/ClientSpace/chambreActions-view.fxml"));
            Parent root = loader.load();

            ChambreActionsController actionsController = loader.getController();
            actionsController.setChambre(chambre);

            Stage stage = new Stage();
            stage.setTitle("Détails de la chambre");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
