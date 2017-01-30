package InventoryManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	Connection conn;
	PreparedStatement ps = null;
	ResultSet rs = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
    	createTables();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("I.M.S. | Login");
		primaryStage.setScene(new Scene(root, 1200, 700));

		//kept stage dimensions static
		primaryStage.setWidth(1200.0);
		primaryStage.setHeight(700.0);

		//disabled window resizing
		primaryStage.setResizable(false);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    
    public void createTables() {
    	try {
    		conn = SQLiteConnection.Connector();
    		// items
    		String query = "CREATE TABLE  IF NOT EXISTS \"items\" (\"id\" INTEGER PRIMARY KEY  NOT NULL ,\"itemname\" ,\"barcode\" ,\"weight\" DOUBLE DEFAULT (null) ,\"expiration\" ,\"reserved\" BOOL NOT NULL  DEFAULT (0) ,\"aisle\" CHAR DEFAULT (null) ,\"section\" INTEGER DEFAULT (null) ,\"itemnumber\" INTEGER DEFAULT (0), \"itemdetail\" TEXT)";
    		ps = conn.prepareStatement(query);
    		ps.execute();
    		// departures
    		query = "CREATE TABLE  IF NOT EXISTS \"departures\" (\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , \"itemname\" TEXT, \"barcode\" TEXT, \"reserved\" BOOL, \"pending\" BOOL, \"ready\" BOOL, \"shipped\" BOOL, \"itemdetail\" TEXT)";
    		ps = conn.prepareStatement(query);
    		ps.execute();
    		// incoming
    		query = "CREATE TABLE  IF NOT EXISTS \"incoming\" (\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , \"itemname\" TEXT, \"weight\" DOUBLE, \"expirationdate\" DATETIME, \"arrivaldate\" DATETIME, \"shipped\" BOOL NOT NULL  DEFAULT 1, \"arrived\" BOOL NOT NULL  DEFAULT 0, \"added\" BOOL NOT NULL  DEFAULT 0, \"itemdetail\" TEXT)";
    		ps = conn.prepareStatement(query);
    		ps.execute();
    		/// employee
    		query = "SELECT name FROM sqlite_master WHERE type='table' AND name='employee'";
    		ps = conn.prepareStatement(query);
    		rs = ps.executeQuery();
    		if(rs.next()){
//    			System.out.println("Table exists");
    			// nothing goes here
    		} else {
//    			System.out.println("Table does not exists");
    			query = "CREATE TABLE  IF NOT EXISTS \"employee\" (\"id\" INTEGER PRIMARY KEY  NOT NULL ,\"firstname\" TEXT,\"middleinitial\" Text DEFAULT (null) ,\"lastname\" TEXT,\"username\" TEXT,\"password\" TEXT,\"phonenumber\" TEXT,\"email\" TEXT,\"admin\" BOOL DEFAULT (0) ,\"contactname\" TEXT,\"contactnumber\" TEXT, \"contactemail\" TEXT, \"lock\" BOOL NOT NULL  DEFAULT 0, \"lockcount\" INTEGER NOT NULL  DEFAULT 0)";
        		ps = conn.prepareStatement(query);
        		ps.execute();
        		query = "INSERT INTO employee (firstname, middleinitial, lastname, username, password, phonenumber, email, admin, contactname, contactnumber, contactemail)"
    					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        		// if first time running creates Username: admin Password: admin
        		String temporaryCredentials= "admin";
    			ps = conn.prepareStatement(query);
    			ps.setString(1, temporaryCredentials);
    			ps.setString(2, "A");
    			ps.setString(3, temporaryCredentials);
    			ps.setString(4, temporaryCredentials);
    			ps.setString(5, temporaryCredentials);
    			ps.setString(6, "000-000-0000");
    			ps.setString(7, "IMS@admin.com");
    			ps.setBoolean(8, true);
    			ps.setString(9, "Creator");
    			ps.setString(10, "666-666-6666");
    			ps.setString(11, "IMS@Creator.com");
    			ps.executeUpdate();
    			populateTables();
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
    
    public void populateTables() {
		try {
			LocalDate today = LocalDate.now();
			LocalDate tomorrow = today.plusDays(5);
			LocalDate after = today.plusDays(9);
			conn = SQLiteConnection.Connector();
			String query = "INSERT INTO incoming (itemname, weight, expirationdate, arrivaldate, shipped, arrived, added, itemdetail) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			for (int i = 1; i < 31; i++) {
				ps = conn.prepareStatement(query);
				ps.setString(1, "Item " + String.valueOf(i));
				if (i >= 15) {
					ps.setDouble(2, 100);
				} else {
					ps.setDouble(2, 50);
				}
				if (i >= 20) {
					ps.setString(3, after.toString());
				} else {
					ps.setString(3, null);
				}
				if(i>=15){
					ps.setString(4, tomorrow.toString());
				} else {
					ps.setString(4, today.toString());
				}
				ps.setBoolean(5, true);
				ps.setBoolean(6, false);
				ps.setBoolean(7, false);
				ps.setString(8, "Description...");
				ps.executeUpdate();
			}
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
    
}


