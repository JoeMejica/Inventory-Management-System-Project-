package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import InventoryManagementSystem.SQLiteConnection;

public class DepartureEvent {
	private Connection conn;
	private boolean reserved;
	private boolean pending;
	private boolean ready;
	private boolean shipped;
	private String itemName;
	private DepartureEvent event;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public DepartureEvent(String itemName) {
		this.itemName = itemName;
		reserved = true;
		pending = false;
		ready = false;
		shipped = false;
	}

	public DepartureEvent() {
	}

	public String getItemName() {
		return itemName;
	}

	public boolean getReserved() {
		return reserved;
	}

	public boolean getPending() {
		return pending;
	}

	public boolean getReady() {
		return ready;
	}

	public boolean getShipped() {
		return shipped;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setReserved(boolean reserve) {
		reserved = reserve;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public void setShipped(boolean shipped) {
		this.shipped = shipped;
	}

	public void pendingEvent(String barcode) {
		try {
			conn = SQLiteConnection.Connector();
			event = new DepartureEvent(barcode);
			String query = "UPDATE \"main\".\"departures\" SET \"pending\" = ?1, \"ready\" = ?2, \"shipped\" = ?3 WHERE  \"barcode\" = ?4";
			ps = conn.prepareStatement(query);
			event.setPending(true);
			ps.setBoolean(1, event.getPending());
			ps.setBoolean(2, event.getReady());
			ps.setBoolean(3, event.getShipped());
			ps.setString(4, barcode);
			ps.executeUpdate();
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

	public void readyEvent(String barcode) {
		try {
			conn = SQLiteConnection.Connector();
			event = new DepartureEvent(barcode);
			String query = "UPDATE \"main\".\"departures\" SET \"pending\" = ?1, \"ready\" = ?2, \"shipped\" = ?3 WHERE  \"barcode\" = ?4";
			ps = conn.prepareStatement(query);
			event.setReady(true);
			ps.setBoolean(1, event.getPending());
			ps.setBoolean(2, event.getReady());
			ps.setBoolean(3, event.getShipped());
			ps.setString(4, barcode);
			ps.executeUpdate();
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

	public void shippedEvent(String barcode) {
		try {
			conn = SQLiteConnection.Connector();
			event = new DepartureEvent(barcode);
			String query = "UPDATE \"main\".\"departures\" SET \"pending\" = ?1, \"ready\" = ?2, \"shipped\" = ?3 WHERE  \"barcode\" = ?4";
			ps = conn.prepareStatement(query);
			event.setShipped(true);
			ps.setBoolean(1, event.getPending());
			ps.setBoolean(2, event.getReady());
			ps.setBoolean(3, event.getShipped());
			ps.setString(4, barcode);
			ps.executeUpdate();
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

	public boolean isDepartItem(String barcode) {
		try {
			conn = SQLiteConnection.Connector();
			String query = "SELECT * FROM departures WHERE barcode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, barcode);
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

	public void createDepartureTable() {
		try {
			conn = SQLiteConnection.Connector();
			String query = "CREATE  TABLE  IF NOT EXISTS \"main\".\"departures\" (\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , \"itemname\" TEXT, \"barcode\" TEXT, \"reserved\" BOOL, \"pending\" BOOL, \"ready\" BOOL, \"shipped\" BOOL)";
			ps = conn.prepareStatement(query);
			ps.execute();
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

	public void createDepartureEvent(String itemName, String barcode, String detail) {
		try {
			createDepartureTable();
			conn = SQLiteConnection.Connector();
			DepartureEvent outgoingItem = new DepartureEvent(itemName);
			String query = "INSERT INTO \"main\".\"departures\" (\"itemname\",\"barcode\",\"reserved\",\"pending\",\"ready\",\"shipped\", \"itemdetail\") VALUES (?1,?2,?3,?4,?5,?6,?7)";
			ps = conn.prepareStatement(query);
			ps.setString(1, outgoingItem.getItemName());
			ps.setString(2, barcode);
			ps.setBoolean(3, outgoingItem.getReserved());
			ps.setBoolean(4, outgoingItem.getPending());
			ps.setBoolean(5, outgoingItem.getReady());
			ps.setBoolean(6, outgoingItem.getShipped());
			ps.setString(7, detail);
			ps.executeUpdate();
			query = "UPDATE \"main\".\"items\" SET \"reserved\" = ?1 WHERE  \"barcode\" = ?2";
			ps = conn.prepareStatement(query);
			ps.setBoolean(1, outgoingItem.getReserved());
			ps.setString(2, barcode);
			ps.executeUpdate();
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

	public void removeDepartureItem(String barcode) {
		try {
			conn = SQLiteConnection.Connector();
			String query = "DELETE FROM departures WHERE barcode=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, barcode);
			ps.executeUpdate();
			String query2 = "DELETE FROM items WHERE barcode=?";
			ps = conn.prepareStatement(query2);
			ps.setString(1, barcode);
			ps.executeUpdate();
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
}
