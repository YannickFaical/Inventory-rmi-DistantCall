package Server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import rmi.DatabaseManager;
import rmi.InventoryService;

public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  try {
			  
//			  DatabaseManager databaseManager = new DatabaseManager();
//	            InventoryService inventoryService = new InventoryService();
//	            InventoryService stub = (InventoryService) UnicastRemoteObject.exportObject(inventoryService, 0);
//	            Registry registry = LocateRegistry.createRegistry(1099);
//	            registry.rebind("InventoryService", stub);
			  
//			  
//			  
			  
//	            System.out.println("Server ready");
//	            
			  
			  
			  
			  
			  
			// Créez une instance de DatabaseManager
	            DatabaseManager databaseManager = new DatabaseManager();
			  databaseManager.createInventoryTable();
			  
	            LocateRegistry.createRegistry(1099);
				InventoryService ob=new InventoryService();
				//DatabaseManager ob2=new DatabaseManager();
				
				Naming.rebind("rmi://localhost:1099/InventoryInterface", ob);
		
				 System.out.println("Server ready");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}


