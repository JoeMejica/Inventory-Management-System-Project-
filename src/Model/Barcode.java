package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import InventoryManagementSystem.SQLiteConnection;

public class Barcode {
	private char AisleLetter;
	private int sectionNumber;
	private int ItemNumber;
	private Connection conn;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public Barcode(char AisleLetter, int sectionNumber, int ItemNumber) {
		this.AisleLetter = AisleLetter;
		this.sectionNumber = sectionNumber;
		this.ItemNumber = ItemNumber;
	}

	public Barcode() {
	}

	public boolean isItem(String id) {
		try {
			conn = SQLiteConnection.Connector();
			String query = "SELECT * FROM items WHERE id = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, id);
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

	public boolean hasBarcode(String id) {
		try {
			conn = SQLiteConnection.Connector();
			String query = "SELECT * FROM items WHERE id = ? AND barcode IS NOT NULL";
			ps = conn.prepareStatement(query);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	public boolean isBarcode(String barcode) {
		try {
			conn = SQLiteConnection.Connector();
			String query = "SELECT * FROM items WHERE barcode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, barcode);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	public String getBarcode() {
		return AisleLetter + "-" + sectionNumber + "-" + ItemNumber;
	}

	public char getAisleLetter() {
		return AisleLetter;
	}

	public int getSectionNumber() {
		return sectionNumber;
	}

	public int getItemNumber() {
		return ItemNumber;
	}

	public void setAisleLetter(char aisle) {
		AisleLetter = aisle;
	}

	public void setSectionNumber(int section) {
		sectionNumber = section;
	}

	public void setItemNumber(int number) {
		ItemNumber = number;
	}

}
