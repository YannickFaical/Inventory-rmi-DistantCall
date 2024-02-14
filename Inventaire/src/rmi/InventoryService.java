package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class InventoryService extends UnicastRemoteObject implements InventoryInterface {
	private static final long serialVersionUID = 1L;
	 private List<InventoryItem> inventory;
	 private static InventoryService instance;
	 
	 public DatabaseManager databaseManager =new DatabaseManager();
	 
	 
	    public InventoryService() throws RemoteException {
	        // Initialisation de la liste d'inventaire
	        this.inventory = new ArrayList<>();
	        this.inventory =databaseManager.getInventory();
	        
	    }
	    
	    

	    public InventoryService(DatabaseManager databaseManager) throws RemoteException {
	        super();
	        this.databaseManager = databaseManager;
	    }

//	    @Override
//	    public List<InventoryItem> getInventory() throws RemoteException {
//	        return new ArrayList<>(inventory);
//	    }
	    
	    @Override
	    public List<InventoryItem> getInventory() throws RemoteException {
	        System.out.println("Fetching inventory items from the database...");
	        List<InventoryItem> inventory = databaseManager.getInventory();
	        if (inventory != null && !inventory.isEmpty()) {
	            System.out.println("Inventory items fetched successfully. Total items: " + inventory.size());
	            return inventory;
	        } else {
	            System.out.println("No inventory items found in the database.");
	            return new ArrayList<>();
	        }
	    }


	    @Override
	    public void addInventoryItem(InventoryItem item) throws RemoteException {
	        // Logique d'ajout d'article dans la base de données
	        inventory.add(item);
	        databaseManager.addInventoryItem(item);
	        
	    }

	    @Override
	    public void updateInventoryItem(InventoryItem item) throws RemoteException {
	    	try {
	            databaseManager.updateInventoryItem(item);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    public void deleteInventoryItem(InventoryItem item) throws RemoteException {
	    	
	        // Logique de suppression d'article dans la base de données
	        inventory.remove(item);
	        databaseManager.deleteInventoryItem(item);
	    }



		@Override
		public DatabaseManager databaseManager() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}



		@Override
		public DatabaseManager getDatabaseManager() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public InventoryItem getInventoryItemById(int id) throws RemoteException {
		    System.out.println("Searching for inventory item with ID: " + id);
		    for (InventoryItem item : inventory) {
		        System.out.println("Checking inventory item with ID: " + item.getId());
		        if (item.getId() == id) {
		            System.out.println("Inventory item found with ID: " + id);
		            return item;
		        }
		    }
		    System.out.println("No inventory item found with ID: " + id);
		    return null;
		    
		}

		//@Override
//		public DatabaseManager databaseManager() throws RemoteException {
//			
//			// TODO Auto-generated method stub
//			return null;
//		}

}
