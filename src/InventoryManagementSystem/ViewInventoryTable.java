package InventoryManagementSystem;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class ViewInventoryTable {
	private final SimpleStringProperty aisle;
	private final SimpleStringProperty itemName;
	private final SimpleStringProperty barcode;
	private final SimpleDoubleProperty weight;
	private final SimpleStringProperty expiration;
	private final SimpleStringProperty section;
	private final SimpleStringProperty number;
	private final SimpleStringProperty detail;

	public ViewInventoryTable(String aisle, String itemName, String barcode, Double weight, String expiration,
			String section, String number, String detail) {
		super();
		this.aisle = new SimpleStringProperty(aisle);
		this.itemName = new SimpleStringProperty(itemName);
		this.barcode = new SimpleStringProperty(barcode);
		this.weight = new SimpleDoubleProperty(weight);
		this.expiration = new SimpleStringProperty(expiration);
		this.section = new SimpleStringProperty(section);
		this.number = new SimpleStringProperty(number);
		this.detail = new SimpleStringProperty(detail);
	}

	public String getAisle() {
		return aisle.get();
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

	public String getExpiration() {
		return expiration.get();
	}

	public String getSection() {
		return section.get();
	}

	public String getNumber() {
		return number.get();
	}
	
	public String getDetail() {
		return detail.get();
	}


}
