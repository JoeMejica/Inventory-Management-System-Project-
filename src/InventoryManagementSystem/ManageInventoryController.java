package InventoryManagementSystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManageInventoryController implements Initializable {

	private ObservableList<String> aisleChoices = FXCollections.observableArrayList("All aisles", "A", "B", "C", "D", "E", "F", "O");
	private ObservableList<String> sectionChoices = FXCollections.observableArrayList("All sections", "01", "02", "03",
			"04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20");

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

	@FXML
	private Button viewBtn;

	@FXML
	private Button countBtn;

	@FXML
	private ComboBox<String> aisleBox;

	@FXML
	private ComboBox<String> aisleBox2;

	@FXML
	private ComboBox<String> sectionBox;

	@FXML
	private Label viewLbl;

	@FXML
	private Label countLbl;

	@FXML
	private Label countedLbl;

	// STAGE AND BUTTON NAVIGATION VARIABLES AND FUNCTIONS:

	private Stage stage;
	private Parent root;
	private Connection conn;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		aisleBox.setVisibleRowCount(4);
		aisleBox.setItems(aisleChoices);
		aisleBox.setValue("All aisles");
		aisleBox2.setVisibleRowCount(4);
		aisleBox2.setItems(aisleChoices);
		aisleBox2.setValue("All aisles");
		sectionBox.setVisibleRowCount(4);
		sectionBox.setItems(sectionChoices);
		sectionBox.setValue("All sections");
	}

	public void cycleCount(ActionEvent event) {
		try {
			conn = SQLiteConnection.Connector();
			if (sectionBox.getSelectionModel().getSelectedItem() == "All sections"
					&& aisleBox2.getSelectionModel().getSelectedItem() == "All aisles") {
				String query = "SELECT * FROM items WHERE aisle IS NOT NULL";
				int i = 0;
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				while (rs.next()) {
					i++;
				}
				countedLbl.setText("Count: " + i);
			} else if (sectionBox.getSelectionModel().getSelectedItem() == "All sections"
					&& !(aisleBox2.getSelectionModel().getSelectedItem() == "All aisles")) {
				String query = "SELECT * FROM items WHERE aisle=?";
				int i = 0;
				ps = conn.prepareStatement(query);
				ps.setString(1, aisleBox2.getSelectionModel().getSelectedItem());
				rs = ps.executeQuery();
				while (rs.next()) {
					i++;
				}
				countedLbl.setText("Count: " + i);
			} else if (!(sectionBox.getSelectionModel().getSelectedItem() == "All sections")
					&& (aisleBox2.getSelectionModel().getSelectedItem() == "All aisles")) {
				String query = "SELECT * FROM items WHERE section=? AND aisle IS NOT NULL";
				int i = 0;
				ps = conn.prepareStatement(query);
				ps.setString(1, sectionBox.getSelectionModel().getSelectedItem());
				rs = ps.executeQuery();
				while (rs.next()) {
					i++;
				}
				countedLbl.setText("Count: " + i);
			} else {
				String query = "SELECT * FROM items WHERE aisle=? AND section =?";
				int i = 0;
				ps = conn.prepareStatement(query);
				ps.setString(1, aisleBox2.getSelectionModel().getSelectedItem());
				ps.setString(2, sectionBox.getSelectionModel().getSelectedItem());
				rs = ps.executeQuery();
				while (rs.next()) {
					i++;
				}
				countedLbl.setText("Count: " + i);
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

	public void viewInventory(ActionEvent event) throws IOException {
		stage = (Stage) viewBtn.getScene().getWindow();
		String aisle = aisleBox.getSelectionModel().getSelectedItem();

		stage.setTitle("I.M.S. | View Inventory - Aisle " + aisle);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewInventory.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		ViewInventoryController con = fxmlLoader.<ViewInventoryController> getController();
		con.initAisle(aisle);

		Scene scene = new Scene(root);
		stage.setScene(scene);

		stage.show();
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