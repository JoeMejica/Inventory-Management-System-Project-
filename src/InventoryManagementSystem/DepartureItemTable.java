package InventoryManagementSystem;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class DepartureItemTable {
	private final SimpleStringProperty itemName;
	private final SimpleBooleanProperty reserved;
	private final SimpleBooleanProperty pending;
	private final SimpleBooleanProperty ready;
	private final SimpleBooleanProperty shipped;
	private final SimpleStringProperty barcode;
	private final SimpleStringProperty detail;
	public DepartureItemTable(String itemName, String barcode, boolean reserved, boolean pending, boolean ready, boolean shipped, String detail) {
		super();
		this.itemName = new SimpleStringProperty(itemName);
		this.barcode = new SimpleStringProperty(barcode);
		this.reserved = new SimpleBooleanProperty(reserved);
		this.pending = new SimpleBooleanProperty(pending);
		this.ready = new SimpleBooleanProperty(ready);
		this.shipped = new SimpleBooleanProperty(shipped);
		this.detail = new SimpleStringProperty(detail);
	}
	
	public String getItemName() {
		return itemName.get();
	}
	
	public String getBarcode(){
		return barcode.get();
	}
	
	public boolean isReserved() {
		return reserved.get();
	}
	
	public boolean isPending() {
		return pending.get();
	}
	
	public boolean isReady() {
		return ready.get();
	}
	
	public boolean isShipped() {
		return shipped.get();
	}
	
	public String getDetail(){
		return detail.get();
	}

}
