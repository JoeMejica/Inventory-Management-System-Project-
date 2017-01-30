package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

import InventoryManagementSystem.SQLiteConnection;

public class ArrivalEvent {

  private String trackingNumber;
    private Calendar expectedArrDate;
    private boolean statPending,statShipped,statArrived;
    private Connection conn;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    public ArrivalEvent(){
        trackingNumber = null;
        expectedArrDate = GregorianCalendar.getInstance();
        statPending = true;
        statShipped = false;
        statArrived = false;
    }
    
    public ArrivalEvent(String x){
       trackingNumber = x;
       expectedArrDate = GregorianCalendar.getInstance();
       statPending = true;
       statShipped = false;
       statArrived = false;
       
    }
    public boolean isIncomingItem(String id) {
		try {
			LocalDate today = LocalDate.now();
			conn = SQLiteConnection.Connector();
			String query = "SELECT * FROM incoming WHERE id = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, id);
			rs = ps.executeQuery();
			LocalDate arrival = LocalDate.parse((rs.getString("arrivaldate")));
			if (rs.next() && (arrival.isBefore(today) || arrival.isEqual(today))) {
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
    
    public boolean arrivedDateChecker(String id) {
		try {
			LocalDate today = LocalDate.now();
			conn = SQLiteConnection.Connector();
			String query = "SELECT * FROM incoming WHERE id = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, id);
			rs = ps.executeQuery();
			LocalDate arrival = LocalDate.parse((rs.getString("arrivaldate")));
				if (rs.getBoolean("arrived") == true && (arrival.isBefore(today) || arrival.isEqual(today))) {
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
    
    public void arriveEvent(String id) {
		try { 
			conn = SQLiteConnection.Connector();
			String query = "UPDATE incoming SET arrived = ? WHERE id = ?";
			ps = conn.prepareStatement(query);
			ps.setBoolean(1, true);
			ps.setString(2, id);
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
    
    public void addEvent(String id) {
 		try {
 			conn = SQLiteConnection.Connector();
 			String query = "UPDATE incoming SET added = ? WHERE id = ?";
 			ps = conn.prepareStatement(query);
 			ps.setBoolean(1, true);
 			ps.setString(2, id);
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
    
    public void removeArrived(String id){
    	try {
			conn = SQLiteConnection.Connector();
			String query = "DELETE FROM incoming WHERE id = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
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
    
    public void setTrackingNumber(String x){
        trackingNumber = x;
    }
    
    public String getTrackingNumber(){
        return  trackingNumber;
    }
    
    public void setExpArrDate(int month, int date, int year){
        expectedArrDate.set(year, month, date);
    }
    
    public Calendar getExpArrDate(){
        return expectedArrDate;
    }
    
    public void setPending(){
       statPending = true;
       statShipped = false;
       statArrived = false;
    }
    
    public void setShipped(){
       statPending = false;
       statShipped = true;
       statArrived = false; 
    }
    
    public void setArrived(){
       statPending = false;
       statShipped = false;
       statArrived = true; 
    }
    public String getStatus(){
        if (statPending)
            return "Pending";
        else if (statShipped)
             return "Shipped";
        else if (statArrived)
             return "Arrived";
        else return null;
    }
    
    
    @Override
     public String toString(){
         return  "Tracking number: " + trackingNumber + "\n"
                 + "Status: " + getStatus() + "\n" 
                 + "Expected Arrival Date: " + expectedArrDate+"\n";
     }
}
