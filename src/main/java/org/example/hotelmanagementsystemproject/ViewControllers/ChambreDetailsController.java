package org.example.hotelmanagementsystemproject.ViewControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.hotelmanagementsystemproject.Rmi.Models.Chambre;
import org.example.hotelmanagementsystemproject.Rmi.Models.ChambreLine;
import org.example.hotelmanagementsystemproject.Rmi.RMIClient;

import java.io.IOException;
import java.rmi.RemoteException;

public class ChambreDetailsController {

    private RMIClient client = new RMIClient();
    @FXML
    private Label chmbrNumLabel;

    @FXML
    private TextField surfaceInput;
    @FXML
    private TextField descriptionInput;
    @FXML
    private TextField prixNuitInput;
    @FXML
    private Label feedbackLabel;

    private boolean reserved;
    @FXML
    private Button deleteBtn;

    private int chambreId;

    public void setChambre(ChambreLine chambre) throws RemoteException {
        chmbrNumLabel.setText(String.valueOf(chambre.getId()));
        surfaceInput.setText(String.valueOf(chambre.getSurface()));
        descriptionInput.setText(String.valueOf(chambre.getDescr()));
        prixNuitInput.setText(String.valueOf(chambre.getPrixNuit()));
        reserved = client.isReserved(chambre.getId());
        deleteBtn.setVisible(!reserved);
        deleteBtn.setManaged(!reserved);
        chambreId = chambre.getId();
    }


    @FXML
    public void updateChambre(MouseEvent event) throws IOException {
        Chambre chambre = new Chambre(Integer.parseInt(
                chmbrNumLabel.getText()),
                descriptionInput.getText(),
                Integer.parseInt(prixNuitInput.getText()),
                0,
                Float.parseFloat(surfaceInput.getText())
        );
        if (client.updateRoom(chambre)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/org/example/hotelmanagementsystemproject/EmployeSpace/ListeChambres-view.fxml"));
            Parent registerRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(registerRoot));
            stage.show();
        } else {
            feedbackLabel.setText("Une erreur est survenue !");
        }
    }

    @FXML
    public void deleteChambre(MouseEvent event) throws IOException {
        if (client.deleteChambre(chambreId)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/org/example/hotelmanagementsystemproject/EmployeSpace/ListeChambres-view.fxml"));
            Parent registerRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(registerRoot));
            stage.show();
        } else {
            feedbackLabel.setText("Une erreur est survenue !");
        }
    }


}
