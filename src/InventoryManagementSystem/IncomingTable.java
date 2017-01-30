package InventoryManagementSystem;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class IncomingTable {
	private final SimpleIntegerProperty id;
	private final SimpleStringProperty itemName;
	private final SimpleDoubleProperty weight;
	private final SimpleStringProperty expiration;
	private final SimpleStringProperty arrivalDate;
	private final SimpleBooleanProperty shipped;
	private final SimpleBooleanProperty arrived;
	private final SimpleStringProperty detail;

	public IncomingTable(Integer id, String itemName, Double weight, String expiration, String arrivalDate, boolean shipped,
			boolean arrived, String detail) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.itemName = new SimpleStringProperty(itemName);
		this.weight = new SimpleDoubleProperty(weight);
		this.expiration = new SimpleStringProperty(expiration);
		this.arrivalDate = new SimpleStringProperty(arrivalDate);
		this.shipped = new SimpleBooleanProperty(shipped);
		this.arrived = new SimpleBooleanProperty(arrived);
		this.detail = new SimpleStringProperty(detail);

	}
	
	public int getId(){
		return id.get();
	}

	public String getItemName() {
		return itemName.get();
	}

	public Double getWeight() {
		return weight.get();
	}

	public String getExpiration() {
		return expiration.get();
	}

	public String getArrivalDate() {
		return arrivalDate.get();
	}

	public boolean isShipped() {
		return shipped.get();
	}

	public boolean isArrived() {
		return arrived.get();
	}
	
	public String getDetail() {
		return detail.get();
	}

}
