package org.example.hotelmanagementsystemproject.ViewControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.hotelmanagementsystemproject.Rmi.RMIClient;
import org.example.hotelmanagementsystemproject.Rmi.SessionManagement.SessionManager;
import org.example.hotelmanagementsystemproject.Rmi.SessionManagement.UserType;

import java.io.IOException;

public class EmployeLoginController {

    private final RMIClient client = new RMIClient();

    @FXML
    private Label actionLabel;
    @FXML
    private TextField emailInput;

    @FXML
    private PasswordField passInput;

    @FXML
    private void handleClientSpaceLink(MouseEvent event) {
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

    private void moveToEmpSpace(MouseEvent event) {
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

    @FXML
    private void handleEmployeeLogin(MouseEvent event) {
        String email = emailInput.getText();
        String password = passInput.getText();
        try {
            SessionManager.setSession
                    (UserType.EMPLOYE, client.loginEmploye(email, password));
            System.out.println("SESSION INFO :: :: ");
            System.out.println(SessionManager.getSession().getId());
            moveToEmpSpace(event);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            actionLabel.setText(e.getMessage());
        }
    }
}
