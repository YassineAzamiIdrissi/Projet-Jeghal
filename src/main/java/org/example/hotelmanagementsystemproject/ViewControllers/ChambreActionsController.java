package org.example.hotelmanagementsystemproject.ViewControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.hotelmanagementsystemproject.Rmi.Models.ChambreLine;
import org.example.hotelmanagementsystemproject.Rmi.Models.Reservation;
import org.example.hotelmanagementsystemproject.Rmi.RMIClient;
import org.example.hotelmanagementsystemproject.Rmi.SessionManagement.SessionManager;

import java.io.IOException;

public class ChambreActionsController {
    private final RMIClient client = new RMIClient();

    int rwId;

    @FXML
    private DatePicker fromPicker;

    @FXML
    private Label chambreId;
    @FXML
    private TextField nbrJours;

    @FXML
    private TextField nbrPersonnes;

    @FXML
    private Label feedbackLabel;

    public void setChambre(ChambreLine chambre) {
        // chambreId.setText(String.valueOf(chambre.getId()));
        // surfaceInput.setText(String.valueOf(chambre.getSurface()));
        // descriptionInput.setText(String.valueOf(chambre.getDescr()));
        // prixNuitInput.setText(String.valueOf(chambre.getPrixNuit()));
        rwId = chambre.getId();
    }


    @FXML
    public void reserverChmbr() throws IOException {
        String from = fromPicker.getValue().toString();
        Integer nbrPer = Integer.parseInt(nbrPersonnes.getText());
        Integer nbrJrs = Integer.parseInt(nbrJours.getText());
        if (client.reserverChambre(new Reservation(SessionManager.getSession().getId(),
                rwId, false, false, nbrPer, from, nbrJrs))) {
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Info");
            VBox vbox = new VBox();
            vbox.setSpacing(10);
            vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");
            Label label = new Label("La demande est envoyée à l'administrateur");
            label.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
            Button acceptButton = new Button("Terminer");
            acceptButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 14;");
            acceptButton.setOnAction(e -> dialogStage.close());
            HBox buttonBox = new HBox(10, acceptButton);
            buttonBox.setStyle("-fx-alignment: center;");
            vbox.getChildren().addAll(label, buttonBox);
            Scene scene = new Scene(vbox, 300, 150);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } else {
            feedbackLabel.setText("Une erreur est survenue");
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
