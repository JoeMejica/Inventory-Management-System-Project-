package InventoryManagementSystem;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Model.Barcode;
import Model.DepartureEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CreateDepartureEventController implements Initializable {

	@FXML
	private TableView<ItemTable> table;

	@FXML
	private TableColumn<ItemTable, String> itemNameCol;

	@FXML
	private TableColumn<ItemTable, String> barcodeCol;

	@FXML
	private TableColumn<ItemTable, Double> weightCol;

	@FXML
	private TableColumn<ItemTable, Boolean> reserveCol;

	@FXML
	private TableColumn<ItemTable, String> expirationCol;
	
	@FXML
	private TableColumn<ItemTable, String> detailCol;

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
	private Button createBtn;

	@FXML
	private TextField barcode;

	@FXML
	private Label status;

	@FXML
	private Label txtFieldLbl;

	@FXML
	private Label tableName;

	// STAGE AND BUTTON NAVIGATION VARIABLES AND FUNCTIONS:

	private Stage stage;
	private Parent root;
	private Connection conn;
	private ObservableList<ItemTable> list = FXCollections.observableArrayList();
	private DepartureEvent departEvent = new DepartureEvent();
	private Barcode barcodeEvent = new Barcode();
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itemNameCol.setCellValueFactory(new PropertyValueFactory<ItemTable, String>("itemName"));
		barcodeCol.setCellValueFactory(new PropertyValueFactory<ItemTable, String>("barcode"));
		weightCol.setCellValueFactory(new PropertyValueFactory<ItemTable, Double>("weight"));
		reserveCol.setCellValueFactory(new PropertyValueFactory<ItemTable, Boolean>("reserved"));
		expirationCol.setCellValueFactory(new PropertyValueFactory<ItemTable, String>("expiration"));
		detailCol.setCellValueFactory(new PropertyValueFactory<ItemTable, String>("detail"));
		loadItems();
	}

	public void createEvent(ActionEvent event) {
		try {
			
			conn = SQLiteConnection.Connector();
			if (barcodeEvent.isBarcode(barcode.getText())) {
				departEvent.createDepartureTable();
				String query = "SELECT * FROM departures WHERE barcode = ?";
				ps = conn.prepareStatement(query);
				ps.setString(1, barcode.getText());
				rs = ps.executeQuery();
				if (rs.next()) {
					status.setText("Departure event exists!");
				} else {
					String name = null;
					String detail = null;
					query = "SELECT * FROM items WHERE barcode = ?";
					ps = conn.prepareStatement(query);
					ps.setString(1, barcode.getText());
					rs = ps.executeQuery();
					while (rs.next()) {
						name = rs.getString("itemname");
						detail = rs.getString("itemdetail");
					}
					departEvent.createDepartureEvent(name, barcode.getText(), detail);
					status.setText("Departure event successfully created!");
					list.removeAll(list);
					loadItems();
					barcode.clear();
				}
			} else {
				status.setText("Barcode not found!");
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

	public void loadItems() {
		try {
			conn = SQLiteConnection.Connector();
			String query = "SELECT * FROM items WHERE barcode IS NOT NULL AND reserved = ?";
			ps = conn.prepareStatement(query);
			ps.setBoolean(1, false);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new ItemTable(rs.getString("itemname"), rs.getString("barcode"), rs.getDouble("weight"),
						rs.getBoolean("reserved"), rs.getString("expiration"), rs.getString("itemdetail")));
				table.setItems(list);
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
