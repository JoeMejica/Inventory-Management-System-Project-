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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Model.ArrivalEvent;

public class incomingMenuController implements Initializable {

	@FXML
	private TableView<IncomingTable> table;

	@FXML
	private TableColumn<IncomingTable, Integer> idCol;

	@FXML
	private TableColumn<IncomingTable, String> itemNameCol;

	@FXML
	private TableColumn<IncomingTable, Double> weightCol;

	@FXML
	private TableColumn<IncomingTable, String> expirationCol;

	@FXML
	private TableColumn<IncomingTable, String> arrivalDateCol;

	@FXML
	private TableColumn<IncomingTable, Boolean> shippedCol;

	@FXML
	private TableColumn<IncomingTable, Boolean> arrivedCol;

	@FXML
	private TableColumn<IncomingTable, String> detailCol;
	
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
	private Button addBtn;

	@FXML
	private Button updateBtn;

	@FXML
	private Label tableName;

	@FXML
	private Label status;

	@FXML
	private TextField idField;

	// STAGE AND BUTTON NAVIGATION VARIABLES AND FUNCTIONS:

	private Stage stage;
	private Parent root;
	private Connection conn;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private ObservableList<IncomingTable> list = FXCollections.observableArrayList();
	private ArrivalEvent arrivalEvent = new ArrivalEvent();
	String item;
	String exp;
	Double weight;
	String detail;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idCol.setCellValueFactory(new PropertyValueFactory<IncomingTable, Integer>("id"));
		itemNameCol.setCellValueFactory(new PropertyValueFactory<IncomingTable, String>("itemName"));
		weightCol.setCellValueFactory(new PropertyValueFactory<IncomingTable, Double>("weight"));
		expirationCol.setCellValueFactory(new PropertyValueFactory<IncomingTable, String>("expiration"));
		arrivalDateCol.setCellValueFactory(new PropertyValueFactory<IncomingTable, String>("arrivalDate"));
		shippedCol.setCellValueFactory(new PropertyValueFactory<IncomingTable, Boolean>("shipped"));
		arrivedCol.setCellValueFactory(new PropertyValueFactory<IncomingTable, Boolean>("arrived"));
		detailCol.setCellValueFactory(new PropertyValueFactory<IncomingTable, String>("detail"));
		loadIncoming();
	}

	public void loadIncoming() {
		try {
			conn = SQLiteConnection.Connector();
			String query = "SELECT * FROM incoming WHERE added = ?";
			ps = conn.prepareStatement(query);
			ps.setBoolean(1, false);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new IncomingTable(rs.getInt("id"), rs.getString("itemname"), rs.getDouble("weight"),
						rs.getString("expirationdate"), rs.getString("arrivaldate"), rs.getBoolean("shipped"),
						rs.getBoolean("arrived"), rs.getString("itemdetail")));
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

	public void addItem(ActionEvent event) {
		try {
			if(idField.getText() != null){
				if (arrivalEvent.isIncomingItem(idField.getText())) {
					if (getIncomingData()) {
						conn = SQLiteConnection.Connector();
						String query = "INSERT INTO items (itemname, weight, expiration, itemdetail) VALUES (?, ?, ?, ?)";
						ps = conn.prepareStatement(query);
						ps.setString(1, item);
						ps.setDouble(2, weight);
						ps.setString(3, exp);
						ps.setString(4, detail);
						ps.executeUpdate();
						// query = "UPDATE incoming SET added = ? WHERE id = ?";
						query = "DELETE FROM incoming WHERE id = ?";
						ps = conn.prepareStatement(query);
						// ps.setBoolean(1, true);
						// ps.setString(2, idField.getText());
						ps.setString(1, idField.getText());
						ps.executeUpdate();
						list.removeAll(list);
						loadIncoming();
						idField.clear();
						status.setText("Item has been added!");
					}
				} else {
					status.setText("ID does not meet requirements!");
				}
			} else {
				status.setText("Enter ID!");
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

	public boolean getIncomingData() {
		try {
			if (arrivalEvent.isIncomingItem(idField.getText())) {
				if (arrivalEvent.arrivedDateChecker(idField.getText())) {
					conn = SQLiteConnection.Connector();
					String query = "SELECT * FROM incoming WHERE id = ?";
					ps = conn.prepareStatement(query);
					ps.setString(1, idField.getText());
					rs = ps.executeQuery();
					item = rs.getString("itemname");
					weight = rs.getDouble("weight");
					exp = rs.getString("expirationdate");
					detail = rs.getString("itemdetail");
					status.setText(null);
					return true;
				} else {
					status.setText("Item has not arrived!");
					return false;
				}
			} else {
				status.setText("ID does not meet requirements!");
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

	public void addAll(ActionEvent event) {
		try {
//			LocalDate today = LocalDate.now();
			conn = SQLiteConnection.Connector();
			String query = "SELECT * FROM incoming WHERE arrived = ?";
			ps = conn.prepareStatement(query);
			ps.setBoolean(1, true);
			rs = ps.executeQuery();
			int count = 0;
			while (rs.next()) {
//				LocalDate arrival = LocalDate.parse((rs.getString("arrivaldate")));
//				if (arrival.isBefore(today) || arrival.isEqual(today)) {
					count++;
					query = "INSERT INTO items (itemname, weight, expiration, itemdetail) VALUES (?, ?, ?, ?)";
					ps = conn.prepareStatement(query);
					ps.setString(1, rs.getString("itemname"));
					ps.setDouble(2, rs.getDouble("weight"));
					ps.setString(3, rs.getString("expirationdate"));
					ps.setString(4, rs.getString("itemdetail"));
					ps.executeUpdate();
//				}
			}
			if (count > 0) {
				status.setText("All arrived items added!");
				deleteAll();
			} else {
				status.setText("No arrived items to add!");
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

	public void deleteAll() {
		try {
//			LocalDate today = LocalDate.now();
			conn = SQLiteConnection.Connector();
//			String query = "DELETE FROM incoming WHERE arrived = ? AND arrivaldate = ?";
			String query = "DELETE FROM incoming WHERE arrived = ?";
			ps = conn.prepareStatement(query);
			ps.setBoolean(1, true);
//			ps.setString(2, today.toString());
			ps.executeUpdate();
			list.removeAll(list);
			loadIncoming();
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

	public void updateItem(ActionEvent event) {
		try {
			conn = SQLiteConnection.Connector();
			if (arrivalEvent.isIncomingItem(idField.getText())) {
				arrivalEvent.arriveEvent(idField.getText());
				status.setText("Update arrived status successful!");
				list.removeAll(list);
				loadIncoming();
				idField.clear();
			} else {
				status.setText("ID does not meet requirements!");
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

	// TODO: INSERT REMAINING METHODS HERE
}
