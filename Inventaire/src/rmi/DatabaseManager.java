package rmi;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager implements Serializable{
    private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName());

    private Connection connection;
//
//    public DatabaseManager() {
//        // Initialisation de la connexion à la base de données
//        try {
//            // Charger le pilote JDBC
//            Class.forName("com.mysql.jdbc.Driver");
//
//            // Établir la connexion (modifier les informations de connexion selon la configuration)
//            String url = "jdbc:mysql://localhost:3306/inventorydb";
//            String username = "root";
//            String password = "";
//            connection = DriverManager.getConnection(url, username, password);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }}
    
    private static  String URL = "jdbc:mysql://localhost:3306/db";
    private static  String USER = "root";
    private static  String PASSWORD = "";
    public DatabaseManager() {
		super();
	}
    
    
     

    public Connection connect() throws SQLException {
    	 try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public List<InventoryItem> getInventory() {
        List<InventoryItem> inventory = new ArrayList<>();

        try ( Connection connection = connect();
        		
        	PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM inventory");
             ResultSet resultSet = preparedStatement.executeQuery()
                		
     
        		) {

            while (resultSet.next()) {
                InventoryItem item = new InventoryItem(
                		resultSet.getInt("id"), 
                		resultSet.getString("name"),
                        resultSet.getInt("quantity"),
                        resultSet.getString("description")
                );
                
                inventory.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inventory;
    }

    public void addInventoryItem(InventoryItem item) {
    	System.out.println("fatou");
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO inventory (name, quantity, description) VALUES (?, ?, ?)")) {

            preparedStatement.setString(1, item.getName());
            preparedStatement.setInt(2, item.getQuantity());
            preparedStatement.setString(3, item.getDescription());
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateInventoryItem(InventoryItem item) {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE inventory SET name=?, quantity=?, description=? WHERE id=?")) {
        	
System.out.println("fatou");
            preparedStatement.setString(1, item.getName());
            preparedStatement.setInt(2, item.getQuantity());
            preparedStatement.setString(3, item.getDescription());
            preparedStatement.setInt(4, item.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteInventoryItem(InventoryItem item) {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM inventory WHERE id=?")) {

            preparedStatement.setInt(1, item.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createInventoryTable() {
        try (Connection connection = connect();
             java.sql.Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS inventory (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(255) NOT NULL," +
                    "quantity INT NOT NULL," +
                    "description VARCHAR(255)" +
                    ")");
            LOGGER.info("Table 'inventory' created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Error creating 'inventory' table", e);
        }
    }
}
    


