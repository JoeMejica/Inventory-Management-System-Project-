package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import InventoryManagementSystem.SQLiteConnection;

public class Section {
	Item[] items = new Item[5];
	int numItems = 0;
	private Connection conn;
	private PreparedStatement ps = null;
	private 	ResultSet rs = null;

	public Section() {
	}

	public boolean checkSectionFull(String aisle, int section) throws SQLException {
		try {
			conn = SQLiteConnection.Connector();
			int i = 0;
			String query = "SELECT * FROM items WHERE aisle = ? AND section = ? AND barcode IS NOT NULL";
			ps = conn.prepareStatement(query);
			ps.setString(1, aisle);
			ps.setInt(1, section);
			rs = ps.executeQuery();
			while (rs.next()) {
				i++;
			}
			if (i >= 5) {
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

	public void addItem(Item newItem) {
		numItems++;
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				items[i] = newItem;
			}
		}
	}

	public Item findItem(Barcode barcode) {
		for (int i = 0; i < items.length; i++) {
			if (items[i].getBarcode().equals(barcode)) {
				return items[i];
			}
		}
		return null;
	}

	public int getNumItems() {
		return numItems;
	}

	public boolean sectionFull() {
		if (numItems < 5) {
			return false;
		} else
			return true;
	}

	public Item removeItem(Barcode barcode) {
		Item returnItem;
		numItems--;
		for (int i = 0; i < items.length; i++) {
			if (items[i].getBarcode().equals(barcode)) {
				returnItem = items[i];
				items[i] = null;
				return returnItem;
			}
		}
		return null;
	}
}
