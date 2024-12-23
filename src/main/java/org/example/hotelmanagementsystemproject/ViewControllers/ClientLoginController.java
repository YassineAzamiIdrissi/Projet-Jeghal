package org.example.hotelmanagementsystemproject.ViewControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.hotelmanagementsystemproject.Rmi.RMIClient;
import org.example.hotelmanagementsystemproject.Rmi.SessionManagement.SessionManager;
import org.example.hotelmanagementsystemproject.Rmi.SessionManagement.UserType;

import java.io.IOException;

public class ClientLoginController {

    private final RMIClient client = new RMIClient();
    @FXML
    private TextField emailInput;

    @FXML
    private Label actionLabel;
    @FXML
    private PasswordField passInput;

    @FXML
    private Button loginBtn;

    @FXML
    private Hyperlink linkToRegister;

    @FXML
    private Hyperlink linkToEmpSpace;

    @FXML
    private Label welcomeTitle;


    private void moveToClientSpace(MouseEvent event) {
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

    @FXML
    private void handleLogin(MouseEvent event) {
        try {
            String email = emailInput.getText();
            String password = passInput.getText();
            SessionManager.setSession
                    (UserType.CLIENT, client.loginClient(email, password));
            System.out.println(SessionManager.getSession().getType());
            moveToClientSpace(event);
        } catch (Exception e) {
            actionLabel.setText("Email ou mot de passe est incorrecte");
        }
    }


    @FXML
    private void handleRegisterLink(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/org/example/hotelmanagementsystemproject/clientRegister-view.fxml"));
            Parent registerRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(registerRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMoveEmpSpace(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/org/example/hotelmanagementsystemproject/employeLogin-view.fxml"));
            Parent registerRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(registerRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

