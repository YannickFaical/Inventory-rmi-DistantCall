package inventaire;

import java.io.Serializable;

public class InventoryItem implements Serializable{
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String productName;
    private int quantity;
    private String description;

    public InventoryItem(String productName, int quantity, String description) {
        this.productName = productName;
        this.quantity = quantity;
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

}
