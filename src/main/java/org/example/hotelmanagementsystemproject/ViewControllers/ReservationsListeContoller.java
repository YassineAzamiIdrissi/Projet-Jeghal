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
import org.example.hotelmanagementsystemproject.Rmi.Models.ClientLine;
import org.example.hotelmanagementsystemproject.Rmi.RMIClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

public class ReservationsListeContoller {
    RMIClient client = new RMIClient();

    @FXML
    private TableView<ClientLine> chambreTableView;

    @FXML
    private TableColumn<ChambreLine, String> fullname;

    @FXML
    private TableColumn<ChambreLine, Integer> id;

    @FXML
    private TableColumn<ChambreLine, Integer> chambre;

    @FXML
    private TableColumn<ChambreLine, String> de;

    @FXML
    private TableColumn<ChambreLine, Integer> nuits;

    @FXML
    public void initialize() throws RemoteException {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullname.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        chambre.setCellValueFactory(new PropertyValueFactory<>("chambre"));
        de.setCellValueFactory(new PropertyValueFactory<>("de"));
        nuits.setCellValueFactory(new PropertyValueFactory<>("nbrNuits"));
        ObservableList<ClientLine> chambreLignes = FXCollections.observableArrayList(fetchReservations());
        chambreTableView.setItems(chambreLignes);
        chambreTableView.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);

        chambreTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ClientLine selectedChambre = chambreTableView.getSelectionModel().getSelectedItem();
                if (selectedChambre != null) {
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Décision");
                    VBox vbox = new VBox();
                    vbox.setSpacing(10);
                    vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");
                    Label label = new Label("Décision");
                    label.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
                    Button acceptButton = new Button("Accepter");
                    acceptButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 14;");
                    acceptButton.setOnAction(e -> {
                        try {
                            client.accepterReservation(selectedChambre.getId());
                            initialize();
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }
                        dialogStage.close();
                    });
                    Button refuseButton = new Button("Refuser");
                    refuseButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 14;");
                    refuseButton.setOnAction(e -> {
                        try {
                            client.refuserReservation(selectedChambre.getId());
                            initialize();
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }
                        dialogStage.close();
                    });
                    HBox buttonBox = new HBox(10, acceptButton, refuseButton);
                    buttonBox.setStyle("-fx-alignment: center;");
                    vbox.getChildren().addAll(label, buttonBox);
                    Scene scene = new Scene(vbox, 300, 150);
                    dialogStage.setScene(scene);
                    dialogStage.showAndWait();
                }
            }
        });

    }

    public List<ClientLine> fetchReservations() throws RemoteException {
        return client.getAllReservations();
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
