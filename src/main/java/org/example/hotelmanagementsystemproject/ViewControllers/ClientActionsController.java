package org.example.hotelmanagementsystemproject.ViewControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.hotelmanagementsystemproject.Rmi.RMIClient;
import org.example.hotelmanagementsystemproject.Rmi.SessionManagement.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ClientActionsController implements Initializable {
    private RMIClient client = new RMIClient();

    @FXML
    private Label clientName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("THIS IS THE CURRENT SESSION ID " + SessionManager.getSession().getId());
            clientName.setText(client.getClinetName(SessionManager.getSession().getId()));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void toDispChambresListe(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/org/example/hotelmanagementsystemproject/ClientSpace/chambresDispo-view.fxml"));
            Parent registerRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(registerRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toReservations(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/org/example/hotelmanagementsystemproject/ClientSpace/reservationsListe-view.fxml"));
            Parent registerRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(registerRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logoutClient(MouseEvent event) {
        try {
            SessionManager.logout();
            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/org/example/hotelmanagementsystemproject/clientLogin-view.fxml"));
            Parent registerRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(registerRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void seeResponses(MouseEvent mouseEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/org/example/hotelmanagementsystemproject/ClientSpace/reservationsResponses-view.fxml"));
            Parent registerRoot = loader.load();
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(registerRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toDispHist(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/org/example/hotelmanagementsystemproject/ClientSpace/HistoriqueReservationsListe-view.fxml"));
            Parent registerRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(registerRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
