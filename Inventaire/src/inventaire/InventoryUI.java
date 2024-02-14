package inventaire;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

public class InventoryUI extends JFrame {
	private JTable inventoryTable;
    private InventoryTableModel tableModel;

    public InventoryUI() {
    	setTitle("Inventory Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        // Créer un modèle de table pour gérer les données de l'inventaire
        tableModel = new InventoryTableModel();
        inventoryTable = new JTable();

        // Associer le modèle à la table
        inventoryTable.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(inventoryTable);

        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Mettre à jour l'interface utilisateur avec une nouvelle liste d'articles
    public void updateInventory(List<InventoryItem> inventoryItems) {
        tableModel.setInventoryItems(inventoryItems);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InventoryUI inventoryUI = new InventoryUI();
            
            // Exemple : Mettre à jour l'inventaire avec des données fictives
            List<InventoryItem> sampleInventory = List.of(
                    new InventoryItem("Produit1", 10, "Description du produit 1"),
                    new InventoryItem("Produit2", 15, "Description du produit 2"),
                    new InventoryItem("Produit3", 5, "Description du produit 3")
            );

            inventoryUI.updateInventory(sampleInventory);
        });
    }
}

// Modèle de table pour gérer les données de l'inventaire
class InventoryTableModel extends AbstractTableModel {
	private List<InventoryItem> inventoryItems;
    private final String[] columnNames = {"Produit", "Quantité", "Description"};

    public void setInventoryItems(List<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
        fireTableDataChanged(); // Informer la table que les données ont changé
    }

    @Override
    public int getRowCount() {
        return (inventoryItems == null) ? 0 : inventoryItems.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (inventoryItems == null || rowIndex >= inventoryItems.size()) {
            return null;
        }

        InventoryItem item = inventoryItems.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return item.getProductName();
            case 1:
                return item.getQuantity();
            case 2:
                return item.getDescription();
            default:
                return null;
        }
    }}
