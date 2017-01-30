package InventoryManagementSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class mainMenuController implements Initializable {
	private Connection conn;

	@FXML
	private Button signOutIMS;

	@FXML
	private Button mainMenuBtn;

	@FXML
	private Button outgoingBtn;

	@FXML
	private Button incomingBtn;

	@FXML
	private Button manageBtn;

	@FXML
	private Button settingsBtn;

	// Dashboard Label Counts

	@FXML
	private Label totalInventoryLbl;

	@FXML
	private Label totalIncomingInventoryLbl;

	@FXML
	private Label totalOutgoingInventoryLbl;

	@FXML
	private Label totalPendingInventoryLbl; 
	
	@FXML
	private Label totalItemsLbl;

	// STAGE AND BUTTON NAVIGATION VARIABLES AND FUNCTIONS:

	private Stage stage;
	private Parent root;
	PreparedStatement ps = null;
	ResultSet rs = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		totalIncoming();
		totalOutgoing();
		totalPending();
		totalItemsNotReserved();
		int total = Integer.parseInt(totalIncomingInventoryLbl.getText()); 
		total += Integer.parseInt(totalOutgoingInventoryLbl.getText()); 
		total += Integer.parseInt(totalItemsLbl.getText());
		totalInventoryLbl.setText(String.valueOf(total));
		
	}

//	public void totalInventory() {
//		try {
//			conn = SQLiteConnection.Connector();
//			int count = 0;
//			String query = "SELECT * FROM items WHERE reserved = ?"; 
//			ps = conn.prepareStatement(query);
//			ps.setBoolean(1, false);
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				count++;
//			}
//			int total = Integer.parseInt(totalIncomingInventoryLbl.getText()); 
//			total += Integer.parseInt(totalOutgoingInventoryLbl.getText()); 
//			total += count;
//			totalInventoryLbl.setText(String.valueOf(total));
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					/* ignored */}
//			}
//			if (ps != null) {
//				try {
//					ps.close();
//				} catch (SQLException e) {
//					/* ignored */}
//			}
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					/* ignored */}
//			}
//		}
//
//	}
	
	public void totalItemsNotReserved() {
		try {
			conn = SQLiteConnection.Connector();
			int count = 0;
			String query = "SELECT * FROM items WHERE reserved = ?";
			ps = conn.prepareStatement(query);
			ps.setBoolean(1, false);
			rs = ps.executeQuery();
			while (rs.next()) {
				count++;
			}
			totalItemsLbl.setText(String.valueOf(count));
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

	public void totalIncoming() {
		try {
			conn = SQLiteConnection.Connector();
			int count = 0;
			String query = "SELECT * FROM incoming";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				count++;
			}
			totalIncomingInventoryLbl.setText(String.valueOf(count));
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

	public void totalOutgoing() {
		try {
			conn = SQLiteConnection.Connector();
			int count = 0;
			String query = "SELECT * FROM departures";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				count++;
			}
			totalOutgoingInventoryLbl.setText(String.valueOf(count));
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

	public void totalPending() {
		try {
			conn = SQLiteConnection.Connector();
			int count = 0;
			String query = "SELECT * FROM items WHERE barcode IS NULL";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				count++;
			}
//			int total = Integer.parseInt(totalIncomingInventoryLbl.getText());
//			total += count;
			totalPendingInventoryLbl.setText(String.valueOf(count));
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

	public void signOut(ActionEvent actionEvent) throws IOException {

		stage = (Stage) signOutIMS.getScene().getWindow();

		stage.setTitle("I.M.S. | Login");

		root = FXMLLoader.load(getClass().getResource("login.fxml"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

	public void mainMenu(ActionEvent actionEvent) throws IOException {

		stage = (Stage) mainMenuBtn.getScene().getWindow();

		stage.setTitle("I.M.S. | Main Menu");

		root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void outgoingMenu(ActionEvent actionEvent) throws IOException {

		stage = (Stage) outgoingBtn.getScene().getWindow();

		stage.setTitle("I.M.S. | Outgoing Shipments Menu");

		root = FXMLLoader.load(getClass().getResource("outgoing.fxml"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void incomingMenu(ActionEvent actionEvent) throws IOException {

		stage = (Stage) incomingBtn.getScene().getWindow();

		stage.setTitle("I.M.S. | Incoming Shipments Menu");

		root = FXMLLoader.load(getClass().getResource("incomingSubMenu.fxml"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

	public void manageMenu(ActionEvent actionEvent) throws IOException {

		stage = (Stage) manageBtn.getScene().getWindow();

		stage.setTitle("I.M.S. | Manage Inventory Menu");

		root = FXMLLoader.load(getClass().getResource("manageInventory.fxml"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void settingsMenu(ActionEvent actionEvent) throws IOException {

		stage = (Stage) settingsBtn.getScene().getWindow();

		stage.setTitle("I.M.S. | Settings Menu");

		root = FXMLLoader.load(getClass().getResource("settings.fxml"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	// DASHBOARD COUNT FUNCTIONS:

	// TODO: INSERT REMAINING METHODS HERE
}
