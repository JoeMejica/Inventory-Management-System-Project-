package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import InventoryManagementSystem.SQLiteConnection;

public class Item {
	private Barcode itemBarcode;
	private double itemWeight;
	private String itemName;
	private int itemNumber;
	private String expirationDate;// if null there is no expiration date
	private boolean reserved;
	private Connection conn;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public Item(String name, double weight, Barcode itemBarcode, String expirationDate) {
		reserved = false;
		this.itemBarcode = itemBarcode;
		itemWeight = weight;
		this.expirationDate = expirationDate;
		itemName = name;
		itemNumber = itemBarcode.getItemNumber();
	}

	public Item(double weight, Barcode itemBarcode) {
		reserved = false;
		this.itemBarcode = itemBarcode;
		itemWeight = weight;
	}

	public Item() {
	}

	public boolean isItem(String itemName) throws SQLException {
		try {
			conn = SQLiteConnection.Connector();
			String query = "SELECT * FROM items WHERE itemname = ? AND barcode IS NULL";
			ps = conn.prepareStatement(query);
			ps.setString(1, itemName);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					/* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					/* ignored */}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					/* ignored */}
			}
		}
	}
	
	public boolean isItemBarcode(String itemName) throws SQLException {
		try {
			conn = SQLiteConnection.Connector();
			String query = "SELECT * FROM items WHERE barcode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, itemName);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					/* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					/* ignored */}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					/* ignored */}
			}
		}
	}

	public void removeDups(String itemName, Double weight) {
		try {
			int check = 0;
			String query = "SELECT * FROM items WHERE itemname = ? AND weight = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, itemName);
			ps.setDouble(2, weight);
			rs = ps.executeQuery();
			while (rs.next()) {
				check++;
			}
			check -= check;
			for(int i = 0; i==check;i++ ){
				query = "UPDATE items SET barcode = NULL WHERE itemname = ? AND weight = ?";
				ps = conn.prepareStatement(query);
				ps.setString(1, itemName);
				ps.setDouble(2, weight);
				ps.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					/* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					/* ignored */}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					/* ignored */}
			}
		}
	}

	public Barcode getBarcode() {
		return itemBarcode;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public boolean getReserved() {
		return reserved;
	}

	public double getItemWeight() {
		return itemWeight;
	}

	public String getItemName() {
		return itemName;
	}

	public int getItemNumber() {
		return itemNumber;
	}

	public void setExpirationDate(String Date) {
		expirationDate = Date;
	}

	public void setReserved(boolean reserve) {
		reserved = reserve;
	}

	public void setItemWeight(double weight) {
		itemWeight = weight;
	}

	public void setItemName(String name) {
		itemName = name;
	}
}
