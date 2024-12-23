module org.example.hotelmanagementsystemproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.rmi;
    requires spring.security.crypto;
    requires mysql.connector.java;
    requires spring.security.core;


    opens org.example.hotelmanagementsystemproject to javafx.fxml;
    opens org.example.hotelmanagementsystemproject.Rmi.Models to javafx.base;
    exports org.example.hotelmanagementsystemproject;
    exports org.example.hotelmanagementsystemproject.Rmi.Services;
    exports org.example.hotelmanagementsystemproject.ViewControllers;
    opens org.example.hotelmanagementsystemproject.ViewControllers to javafx.fxml;
}