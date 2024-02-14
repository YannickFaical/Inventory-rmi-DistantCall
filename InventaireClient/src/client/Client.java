package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import rmi.InventoryInterface;

public class Client {
	public static void main(String[] args) {
		
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e ) {
                e.printStackTrace();
            }

            try {
                // Connexion au registre RMI
            	
            	
            	
            	 System.out.println("en attente de connexion au RMI..");
            	
            	//InventoryInterface stub = (InventoryInterface) Naming.lookup("rmi://localhost:1099/InventoryInterface");
            	InventoryInterface stub = (InventoryInterface) Naming.lookup("rmi://localhost:1099/InventoryInterface");
            	 System.out.println("Connecte au registre RMI avec succes..");
            	  
            	 InventoryClient client= new InventoryClient(stub);
            	 
            	 System.out.println("Demarrage de l'interface utilisateur..");
            	 client.startUI();
            	 System.out.println("interface utilisateur chargee avec succes..");
  
            	// startClientUI();
            	//InventoryInterface stu =new InventoryClient();
            	 
            	
              
           // 	InventoryInterface.startUI();
            	 
            } catch (NotBoundException | MalformedURLException | RemoteException e) {
                e.printStackTrace();
            }
        });
        
	}
        
    }
	

