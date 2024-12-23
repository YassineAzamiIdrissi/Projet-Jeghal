package org.example.hotelmanagementsystemproject.ViewControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.hotelmanagementsystemproject.Rmi.Models.Chambre;
import org.example.hotelmanagementsystemproject.Rmi.RMIClient;
import org.example.hotelmanagementsystemproject.Rmi.SessionManagement.SessionManager;

import java.io.IOException;
import java.rmi.RemoteException;

public class NewRoomController {

    private RMIClient client = new RMIClient();

    @FXML
    private TextField prixNuit;
    @FXML
    private TextField surface;
    @FXML
    private TextField description;
    @FXML
    private Label actionMessage;

    @FXML
    private void handleAddNewRoom() {
        try {
            int prix = Integer.parseInt(prixNuit.getText());
            double surf = Double.parseDouble(surface.getText());
            String desc = description.getText();
            int createdBy = SessionManager.getSession().getId();
            client.addRoom(new Chambre(desc, prix, surf, createdBy));
            actionMessage.setStyle("-fx-text-fill: yellow");
            actionMessage.setText("Room added successfully..");
        } catch (RemoteException e) {
            actionMessage.setStyle("-fx-text-fill: red");
            actionMessage.setText(e.getMessage());
            throw new RuntimeException(e);
        }
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
