package InventoryManagementSystem;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ItemTable {
	private final SimpleStringProperty itemName;
	private final SimpleStringProperty barcode;
	private final SimpleDoubleProperty weight;
	private final SimpleStringProperty expiration;
	private final SimpleBooleanProperty reserved;
	private final SimpleStringProperty detail;
	public ItemTable(String itemName, String barcode, Double weight, boolean reserved, String expiration, String detail) {
		super();
		this.itemName = new SimpleStringProperty(itemName);
		this.barcode = new SimpleStringProperty(barcode);
		this.weight = new SimpleDoubleProperty(weight);
		this.reserved = new SimpleBooleanProperty(reserved);
		this.expiration = new SimpleStringProperty(expiration);
		this.detail = new SimpleStringProperty(detail);
		
	}
	
	public String getItemName() {
		return itemName.get();
	}
	public String getBarcode() {
		return barcode.get();
	}
	public Double getWeight() {
		return weight.get();
	}
	public boolean isReserved() {
		return reserved.get();
	}
	public String getExpiration() {
		return expiration.get();
	}
	
	public String getDetail() {
		return detail.get();
	}

}
