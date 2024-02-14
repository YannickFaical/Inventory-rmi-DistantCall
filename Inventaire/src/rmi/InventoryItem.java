package rmi;

import java.io.Serializable;

public class InventoryItem implements Serializable{
	 private static final long serialVersionUID = 1L;
         
	    private int id;
	    private String name;
	    private int quantity;
	    private String description;

	    // Constructeur
	    public InventoryItem(int id,String name, int quantity, String description) {
	    	this.id=id;
	        this.name = name;
	        this.quantity = quantity;
	        this.description = description;
	    }

	    // Getters et Setters
	    public int getId() {
	        return id;
	    }
	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public int getQuantity() {
	        return quantity;
	    }

	    public void setQuantity(int quantity) {
	        this.quantity = quantity;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    @Override
	    public String toString() {
	        return "InventoryItem{" +
	                "name='" + name + '\'' +
	                ", quantity=" + quantity +
	                ", description='" + description + '\'' +
	                '}';
	    }
}
