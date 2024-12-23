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
import org.example.hotelmanagementsystemproject.Rmi.Models.Reservation;
import org.example.hotelmanagementsystemproject.Rmi.RMIClient;
import org.example.hotelmanagementsystemproject.Rmi.SessionManagement.SessionManager;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

public class ClientReservationsListeController {
    RMIClient client = new RMIClient();

    @FXML
    private TableView<Reservation> chambreTableView;

    @FXML
    private TableColumn<ChambreLine, String> from;

    @FXML
    private TableColumn<ChambreLine, Integer> id;

    @FXML
    private TableColumn<ChambreLine, Integer> chambre;

    @FXML
    private TableColumn<ChambreLine, Integer> nbrPersonnes;

    @FXML
    private TableColumn<ChambreLine, Integer> nbrDays;


    @FXML
    public void initialize() throws RemoteException {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        from.setCellValueFactory(new PropertyValueFactory<>("from"));
        chambre.setCellValueFactory(new PropertyValueFactory<>("chambreId"));
        nbrDays.setCellValueFactory(new PropertyValueFactory<>("nbrDays"));
        nbrPersonnes.setCellValueFactory(new PropertyValueFactory<>("nbrPersonnes"));
        ObservableList<Reservation> chambreLignes = FXCollections.observableArrayList(fetchClientReservations());
        chambreTableView.setItems(chambreLignes);
        chambreTableView.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);

        chambreTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Reservation reservation = chambreTableView.getSelectionModel().getSelectedItem();
                if (reservation != null) {
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Annulation");
                    VBox vbox = new VBox();
                    vbox.setSpacing(10);
                    vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");
                    Label label = new Label("Annuler résérvation ?");
                    label.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
                    Button acceptButton = new Button("oui");
                    acceptButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 14;");
                    acceptButton.setOnAction(e -> {
                        try {
                            client.annulerReservation(reservation.getId());
                            initialize();
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }
                        dialogStage.close();
                    });
                    HBox buttonBox = new HBox(10, acceptButton);
                    buttonBox.setStyle("-fx-alignment: center;");
                    vbox.getChildren().addAll(label, buttonBox);
                    Scene scene = new Scene(vbox, 300, 150);
                    dialogStage.setScene(scene);
                    dialogStage.showAndWait();
                }
            }
        });

    }

    List<Reservation> fetchClientReservations() throws RemoteException {
        return client.fetchClientReservations(SessionManager.getSession().getId());
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
