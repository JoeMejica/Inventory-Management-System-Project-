package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import InventoryManagementSystem.SQLiteConnection;

public class Aisle {
	private Section sections[] = new Section[20];
	private boolean sectionsFull[] = new boolean[20];
	private Connection conn;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public Aisle() {
		for (int i = 0; i < 20; i++) {
			sectionsFull[i] = false;
		}
	}

	public boolean checkAisleFull(String aisle) throws SQLException {
		try {
			int i = 0;
			conn = SQLiteConnection.Connector();
			String query = "SELECT * FROM items WHERE aisle = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, aisle);
			rs = ps.executeQuery();
			while (rs.next()) {
				i++;
			}
			if (i >= 100) {
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

	public Item removeItem(Barcode barcode) {
		int sectionNumber = barcode.getSectionNumber();
		Item returnItem = sections[sectionNumber].removeItem(barcode);
		if (returnItem != null && sectionsFull[sectionNumber]) {
			sectionsFull[sectionNumber] = false;
		}
		return returnItem;
	}

	public void addItem(Item newItem) {
		int sectionNumber = newItem.getBarcode().getSectionNumber();
		sections[sectionNumber].addItem(newItem);
		if (sections[sectionNumber].getNumItems() == 5) {
			sectionsFull[sectionNumber] = true;
		}
	}

	public Item findItem(Barcode barcode) {
		int sectionNumber = barcode.getSectionNumber();
		return sections[sectionNumber].findItem(barcode);
	}
}
