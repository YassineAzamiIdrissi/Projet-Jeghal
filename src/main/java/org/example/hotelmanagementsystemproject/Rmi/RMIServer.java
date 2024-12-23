package org.example.hotelmanagementsystemproject.Rmi;

import org.example.hotelmanagementsystemproject.Rmi.ServicesImplementations.ClientServiceImpl;
import org.example.hotelmanagementsystemproject.Rmi.ServicesImplementations.EmployeServiceImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            Naming.rebind("ClientService", new ClientServiceImpl());
            Naming.rebind("EmployeService", new EmployeServiceImpl());
            System.out.println("Serveur RMI démarré..");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}