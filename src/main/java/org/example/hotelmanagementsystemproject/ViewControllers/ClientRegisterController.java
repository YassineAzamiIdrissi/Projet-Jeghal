package org.example.hotelmanagementsystemproject.ViewControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.hotelmanagementsystemproject.Rmi.Models.Client_;
import org.example.hotelmanagementsystemproject.Rmi.RMIClient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientRegisterController implements Initializable {

    private final RMIClient client = new RMIClient();
    @FXML
    private Hyperlink moveToLoginClient;
    @FXML
    private TextField mailInput;
    @FXML
    private TextField prenomInput;
    @FXML
    private TextField nomInput;
    @FXML
    private PasswordField passInput;
    @FXML
    private PasswordField confirmInput;
    @FXML
    private ComboBox<String> statutCombo;
    @FXML
    private Label feedback;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        statutCombo.getItems().addAll("Mr", "Mme");
    }

    @FXML
    private void handleRegister(MouseEvent event) {
        feedback.setStyle("-fx-font-weight: bold;");
        String email = mailInput.getText();
        String password = passInput.getText();
        String confirm = confirmInput.getText();
        String firstName = prenomInput.getText();
        String lastName = nomInput.getText();
        String statut = statutCombo.getSelectionModel().getSelectedItem();
        if (email.isEmpty() || password.isEmpty()
                || confirm.isEmpty()
                || firstName.isEmpty()
                || lastName.isEmpty()) {
            feedback.setStyle("-fx-text-fill: yellow;" +
                    "-fx-alignment: center;" +
                    "-fx-font-weight: bold");
            feedback.setText("Des données nécéssaires qui manquent");
            return;
        }
        if (password.length() < 7) {
            feedback.setStyle("-fx-text-fill: yellow;" +
                    "-fx-alignment: center;" +
                    "-fx-font-weight: bold");
            feedback.setText("Le mot de passe doit contenir au moins 7 caractéres !");
            return;
        }
        if (!confirm.equals(password)) {
            feedback.setStyle("-fx-text-fill: yellow;" +
                    "-fx-alignment: center;" +
                    "-fx-font-weight: bold");
            feedback.setText("Les mots de passe ne sont pas similaire !");
            return;
        }
        if (client.registerClient(new Client_(firstName, lastName, email, password, statut))) {
            feedback.setStyle("-fx-text-fill: lightGreen;" +
                    "-fx-alignment: center;" +
                    "-fx-font-weight: bold");
            feedback.setText("Compte crée avec succés");
        } else {
            feedback.setStyle("-fx-text-fill: yellow;" +
                    "-fx-alignment: center;");
            System.out.println("Une erreur est survenue au service..essayez plus tard ");
        }
    }

    @FXML
    private void handleMoveLoginClient(MouseEvent event) {
        try {
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
}
