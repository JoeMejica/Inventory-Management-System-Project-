package InventoryManagementSystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateIncomingController {
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
	@FXML
	private Button signOutIMS;

	@FXML
	private Label status;

	@FXML
	private TextField itemField;

	@FXML
	private TextField itemDetail;

	@FXML
	private TextField weight;

	@FXML
	private DatePicker exp;

	@FXML
	private DatePicker arrival;

	private Stage stage;
	private Parent root;
	private Connection conn;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public static boolean isNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

	public void CreateArrival(ActionEvent event) {
		try {
			LocalDate today = LocalDate.now();
			if (itemField.getText().isEmpty()) {
				status.setText("Enter item name!");
			} else if (weight.getText().isEmpty()) {
				status.setText("Enter weight!");
			} else if (!(isNumeric(weight.getText()))) {
				status.setText("Incorrect format for weight!");
			} else if (arrival.getValue() == null) {
				status.setText("Enter arrival date!");
			} else if (exp.getValue() != null && exp.getValue().isBefore(today)) {
				status.setText("Invalid expiration date!");
			} else if (arrival.getValue().isBefore(today)) {
				status.setText("Invalid arrival date!");
			} else {
				if (exp.getValue() != null && (exp.getValue().isBefore(arrival.getValue())
						|| exp.getValue().isEqual(arrival.getValue()))) {
					status.setText("Item will be expired on arrival!");
				} else {
					conn = SQLiteConnection.Connector();
					String query = "INSERT INTO incoming (itemname, weight, expirationdate, arrivaldate, shipped, arrived, added, itemdetail) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
					ps = conn.prepareStatement(query);
					ps.setString(1, itemField.getText());
					int i = Integer.parseInt(weight.getText());
					ps.setDouble(2, i);
					if (exp.getValue() == null) {
						ps.setString(3, null);
					} else {
						ps.setString(3, exp.getValue().toString());
					}
					ps.setString(4, arrival.getValue().toString());
					ps.setBoolean(5, true);
					ps.setBoolean(6, false);
					ps.setBoolean(7, false);
					ps.setString(8, itemDetail.getText());
					ps.executeUpdate();
					status.setText("Arrival event created!");
					itemField.clear();
					weight.clear();
					itemDetail.clear();
					exp.setValue(null);
					arrival.setValue(null);
				}
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

}