package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InventoryInterface extends Remote{
	
	List<InventoryItem> getInventory() throws RemoteException;
	DatabaseManager getDatabaseManager() throws RemoteException;
	public DatabaseManager databaseManager() throws RemoteException;
	
   public void addInventoryItem(InventoryItem item) throws RemoteException;
   public void updateInventoryItem(InventoryItem item) throws RemoteException;
    void deleteInventoryItem(InventoryItem item) throws RemoteException;
public 	InventoryItem getInventoryItemById(int id) throws RemoteException;
	
}
