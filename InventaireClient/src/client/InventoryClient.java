package client;

import rmi.InventoryInterface;
import rmi.InventoryItem;
//import rmi.InventoryService;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.List;

public class InventoryClient implements InventoryInterface {
    private JFrame frame;
    private JTextArea inventoryTextArea;
    private InventoryInterface inventoryService;
    
//    public InventoryClient() throws RemoteException {
//    	  inventoryService = new InventoryService();
//    	  initializeUI();
//    	}

   

	public InventoryClient(InventoryInterface inventoryService)throws RemoteException {
		// TODO Auto-generated constructor stub
		
	       this.inventoryService = inventoryService;
	       
	        //initializeUI();
	}



	private void initializeUI() {
    	 System.out.println("Initializing UI...");
        frame = new JFrame("Gestion d'Inventaire");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        inventoryTextArea = new JTextArea();
        inventoryTextArea.setEditable(false);
        frame.add(new JScrollPane(inventoryTextArea), BorderLayout.CENTER);

        
       
        
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton refreshButton = new JButton("Actualiser");
        JButton addButton = new JButton("Ajouter");
        JButton updateButton = new JButton("Mettre à jour");
        JButton deleteButton = new JButton("Supprimer");

        buttonPanel.add(refreshButton);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> {
            try {
                refreshInventory();
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        });

        addButton.addActionListener(e -> {
            try {
                addInventoryItem();
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        });

        updateButton.addActionListener(e -> {
            try {
                updateInventoryItem();
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                deleteInventoryItem();
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        });

        frame.setVisible(true);
        System.out.println("UI initialized successfully.");
    }

    public  void startUI() throws RemoteException {
    	System.out.println("Starting UI...");
    	 SwingUtilities.invokeLater(() -> {
             try {
                 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
             } catch (Exception e) {
                 e.printStackTrace();
             }
        initializeUI();
    	  });
    }

    private void refreshInventory() throws RemoteException {
        System.out.println("Refreshing inventory...");
        List<InventoryItem> inventory = getInventory();
        if (inventory != null) {
            String[] columnNames = {"ID", "Nom", "Quantité", "Description"};
            Object[][] data = new Object[inventory.size()][4];

            for (int i = 0; i < inventory.size(); i++) {
                InventoryItem item = inventory.get(i);
                // Utiliser le véritable ID de l'élément de l'inventaire
                data[i][0] = item.getId();
                data[i][1] = item.getName();
                data[i][2] = item.getQuantity();
                data[i][3] = item.getDescription();
            }

            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);

            JFrame tableFrame = new JFrame("Inventaire");
            tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tableFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
            tableFrame.setSize(600, 400); // Ajustez la taille selon vos besoins
            tableFrame.setVisible(true);
            System.out.println("Inventory refreshed successfully.");
        } else {
            JOptionPane.showMessageDialog(frame, "Erreur lors de la récupération de l'inventaire.");
            System.err.println("Error refreshing inventory.");
        }
    }




    private void addInventoryItem() throws RemoteException {
    	System.out.println("Adding inventory item...");
        JTextField nameField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField descriptionField = new JTextField();

        Object[] message = {
                "Nom:", nameField,
                "Quantité:", quantityField,
                "Description:", descriptionField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Ajouter un article", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                String description = descriptionField.getText();

                InventoryItem newItem = new InventoryItem(name, quantity, description);
                addInventoryItem(newItem);
                JOptionPane.showMessageDialog(frame, "Article ajouté avec succès !");
                refreshInventory();
                System.out.println("Inventory item added successfully.");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Veuillez saisir une quantité valide.");
                System.err.println("Error adding inventory item - Invalid quantity format.");
            }
        }else {
            System.out.println("Add inventory item operation cancelled.");
        }
    }
  
    private void updateInventoryItem() throws RemoteException {
        System.out.println("Updating inventory item...");
        JTextField idField = new JTextField();

        Object[] message = {"ID de l'article à mettre à jour:", idField};

        int option = JOptionPane.showConfirmDialog(frame, message, "Mettre à jour un article", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int id = Integer.parseInt(idField.getText());

            System.out.println("ID entered by user: " + id);

            try {
                // Récupérer l'article existant par son ID à partir du service distant
                System.out.println("Fetching existing item from the inventory for ID: " + id);
                InventoryItem existingItem = inventoryService.getInventoryItemById(id);

                if (existingItem != null) {
                    System.out.println("Existing item found: " + existingItem);

                    JTextField newNameField = new JTextField(existingItem.getName());
                    JTextField newQuantityField = new JTextField(String.valueOf(existingItem.getQuantity()));
                    JTextField newDescriptionField = new JTextField(existingItem.getDescription());

                    Object[] updateMessage = {
                            "Nouveau nom:", newNameField,
                            "Nouvelle quantité:", newQuantityField,
                            "Nouvelle description:", newDescriptionField
                    };

                    int updateOption = JOptionPane.showConfirmDialog(frame, updateMessage, "Mettre à jour un article", JOptionPane.OK_CANCEL_OPTION);
                    if (updateOption == JOptionPane.OK_OPTION) {
                        System.out.println("User has confirmed the update.");

                        try {
                            String newName = newNameField.getText();
                            int newQuantity = Integer.parseInt(newQuantityField.getText());
                            String newDescription = newDescriptionField.getText();

                            // Mettre à jour les propriétés de l'article
                            existingItem.setName(newName);
                            existingItem.setQuantity(newQuantity);
                            existingItem.setDescription(newDescription);

                            // Mettre à jour l'article dans la base de données en utilisant le service distant
                            System.out.println("Updating inventory item in the database...");
                            inventoryService.updateInventoryItem(existingItem);

                            JOptionPane.showMessageDialog(frame, "Article mis à jour avec succès !");
                            refreshInventory();
                            System.out.println("Inventory item updated successfully.");

                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(frame, "Veuillez saisir une quantité valide.");
                            System.err.println("Error updating inventory item - Invalid quantity format.");

                        }
                    } else {
                        System.out.println("Update cancelled by user.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Aucun article trouvé avec l'ID spécifié.");
                    System.out.println("No item found with the specified ID.");
                }
            } catch (RemoteException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur lors de la récupération de l'inventaire.");
                System.err.println("Error updating inventory item - Remote Exception.");
            }
        } else {
            System.out.println("Update operation cancelled by user.");
        }
    }
   

    private void deleteInventoryItem() throws RemoteException {
    	System.out.println("Deleting inventory item...");
        JTextField nameField = new JTextField();

        Object[] message = {
                "Nom de l'article à supprimer:", nameField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Supprimer un article", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();

            // Récupérer l'article existant
            List<InventoryItem> inventory = getInventory();
            InventoryItem existingItem = null;
            for (InventoryItem item : inventory) {
                if (item.getName().equals(name)) {
                    existingItem = item;
                    break;
                }
            }

            if (existingItem != null) {
                // Supprimer l'article
                deleteInventoryItem(existingItem);
                JOptionPane.showMessageDialog(frame, "Article supprimé avec succès !");
                refreshInventory();
                System.out.println("Inventory item deleted successfully.");

            } else {
                JOptionPane.showMessageDialog(frame, "Aucun article trouvé avec le nom spécifié.");
                System.out.println("No inventory item found with the specified name.");

            }
        }
    }

	@Override
	public List<InventoryItem> getInventory() throws RemoteException {
		// TODO Auto-generated method stub
		 return inventoryService.getInventory();
	
	}

	@Override
	public void addInventoryItem(InventoryItem item) throws RemoteException {
		// TODO Auto-generated method stub
		inventoryService.addInventoryItem(item);
	}

	@Override
	public void updateInventoryItem(InventoryItem item) throws RemoteException {
		// TODO Auto-generated method stub
		inventoryService.updateInventoryItem(item);
		
	}

	@Override
	public void deleteInventoryItem(InventoryItem item) throws RemoteException {
		// TODO Auto-generated method stub
		inventoryService.deleteInventoryItem(item);
		
	}



	@Override
	public InventoryItem getInventoryItemById(int id) throws RemoteException {
	    try {
	        return inventoryService.getInventoryItemById(id);
	    } catch (RemoteException e) {
	        e.printStackTrace();
	        throw e;
	    }
	}

}
