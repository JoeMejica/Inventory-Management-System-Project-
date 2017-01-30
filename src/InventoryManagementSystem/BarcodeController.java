package InventoryManagementSystem;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Model.Barcode;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class BarcodeController implements Initializable {

	ObservableList<String> aisleChoices = FXCollections.observableArrayList("A", "B", "C", "D", "E", "F", "O");
	ObservableList<Integer> sectionChoices = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
			13, 14, 15, 16, 17, 18, 19, 20);

	@FXML
	private TableView<BarcodeItemTable> table;

	@FXML
	private TableColumn<BarcodeItemTable, Integer> idCol;

	@FXML
	private TableColumn<BarcodeItemTable, String> itemNameCol;

	@FXML
	private TableColumn<BarcodeItemTable, Double> weightCol;

	@FXML
	private TableColumn<BarcodeItemTable, String> expirationCol;
	
	@FXML
	private TableColumn<BarcodeItemTable, String> detailCol;

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
	private Button genBtn;;

	@FXML
	private TextField id;

	@FXML
	private Label itemNumber;

	@FXML
	private Label status;

	@FXML
	private Label aisleLbl;

	@FXML
	private Label sectionLbl;

	@FXML
	private Label numberLbl;

	@FXML
	private Label barcodeGen;

	@FXML
	private ComboBox<String> aisleBox;

	@FXML
	private ComboBox<Integer> sectionBox;

	// STAGE AND BUTTON NAVIGATION VARIABLES AND FUNCTIONS:
	private Stage stage;
	private Parent root;
	private Connection conn;
	private ObservableList<BarcodeItemTable> list = FXCollections.observableArrayList();
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private Barcode barcodeEvent = new Barcode();
	private final String Gen = "Barcode Generated!";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		aisleBox.setVisibleRowCount(4);
		aisleBox.setItems(aisleChoices);
		aisleBox.setValue("A");
		sectionBox.setVisibleRowCount(4);
		sectionBox.setItems(sectionChoices);
		sectionBox.setValue(1);
		idCol.setCellValueFactory(new PropertyValueFactory<BarcodeItemTable, Integer>("id"));
		itemNameCol.setCellValueFactory(new PropertyValueFactory<BarcodeItemTable, String>("itemName"));
		weightCol.setCellValueFactory(new PropertyValueFactory<BarcodeItemTable, Double>("weight"));
		expirationCol.setCellValueFactory(new PropertyValueFactory<BarcodeItemTable, String>("expiration"));
		detailCol.setCellValueFactory(new PropertyValueFactory<BarcodeItemTable, String>("detail"));
		loadBarcodeItems();
	}

	public void autoBarcode(ActionEvent event) {
		aisleGen();
		sectionGen(aisleBox.getSelectionModel().getSelectedItem(), 1);
	}

	public void aisleGen() {
		try {
			conn = SQLiteConnection.Connector();
			int count = 0;
			if (barcodeEvent.isItem(id.getText())) {
				if (!(barcodeEvent.hasBarcode(id.getText()))) {
					String query = "SELECT * FROM items WHERE id = ?";
					ps = conn.prepareStatement(query);
					ps.setString(1, id.getText());
					rs = ps.executeQuery();
					String expiration = rs.getString("expiration");
					Double weight = rs.getDouble("weight");
					if (expiration != null) {
						aisleBox.setValue("E");
						query = "SELECT * FROM items WHERE aisle = ?";
						ps = conn.prepareStatement(query);
						ps.setString(1, "E");
						rs = ps.executeQuery();
						while (rs.next()) {
							count++;
						}
						if (count >= 100) {
							aisleBox.setValue("F");
							count = 0;
							query = "SELECT * FROM items WHERE aisle = ?";
							ps = conn.prepareStatement(query);
							ps.setString(1, "F");
							rs = ps.executeQuery();
							while (rs.next()) {
								count++;
							}
							if (count >= 100) {
								aisleBox.setValue("O");
							}
						}
					} else if (weight >= 100) {
						aisleBox.setValue("A");
						query = "SELECT * FROM items WHERE aisle = ?";
						ps = conn.prepareStatement(query);
						ps.setString(1, "A");
						rs = ps.executeQuery();
						while (rs.next()) {
							count++;
						}
						if (count >= 100) {
							count = 0;
							aisleBox.setValue("B");
							query = "SELECT * FROM items WHERE aisle = ?";
							ps = conn.prepareStatement(query);
							ps.setString(1, "B");
							rs = ps.executeQuery();
							while (rs.next()) {
								count++;
							}
							if (count >= 100) {
								aisleBox.setValue("O");
							}
						}
					} else {
						aisleBox.setValue("C");
						query = "SELECT * FROM items WHERE aisle = ?";
						ps = conn.prepareStatement(query);
						ps.setString(1, "C");
						rs = ps.executeQuery();
						while (rs.next()) {
							count++;
						}
						if (count >= 100) {
							count = 0;
							aisleBox.setValue("D");
							query = "SELECT * FROM items WHERE aisle = ?";
							ps = conn.prepareStatement(query);
							ps.setString(1, "D");
							rs = ps.executeQuery();
							while (rs.next()) {
								count++;
							}
							if (count >= 100) {
								aisleBox.setValue("O");
							}
						}
					}
					status.setText(Gen);
					itemNumber.setText(id.getText());
				} else {
					status.setText("Item has a barcode!");
				}

			} else {
				status.setText("ID not found!");
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

	public void sectionGen(String aisle, int section) {
		try {
			if (barcodeEvent.isItem(id.getText())) {
				// System.out.println("Start: "+section);
				conn = SQLiteConnection.Connector();
				int count = 0;
				String query = "SELECT * FROM items WHERE aisle = ? AND section = ?";
				ps = conn.prepareStatement(query);
				ps.setString(1, aisle);
				ps.setInt(2, section);
				rs = ps.executeQuery();
				while (rs.next()) {
					count++;
					// System.out.println(section +": "+count);
				}
				if (count >= 5) {
					// System.out.println("Count >= 5");
					sectionBox.setValue(section++);
					sectionGen(aisle, section);
				} else {
					// System.out.println("Count < 5");
					sectionBox.setValue(section);
				}
			} else {
				status.setText("ID not found!");
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

	public void createBarcodeEvent(ActionEvent event) {
		try {
			conn = SQLiteConnection.Connector();
			Barcode barcode = new Barcode();
			if (status.getText().equals(Gen)) {
				if (barcodeEvent.isItem(id.getText())) {
					barcode.setAisleLetter(aisleBox.getSelectionModel().getSelectedItem().charAt(0));
					barcode.setSectionNumber(sectionBox.getSelectionModel().getSelectedItem());
					int itemnumber = Integer.parseInt(id.getText());
					barcode.setItemNumber(itemnumber);
					String query = "UPDATE items SET barcode=?, aisle=?, section=?, itemnumber=? WHERE id=?";
					ps = conn.prepareStatement(query);
					ps.setString(1, barcode.getBarcode());
					String aisle = String.valueOf(barcode.getAisleLetter());
					ps.setString(2, aisle);
					ps.setInt(3, barcode.getSectionNumber());
					ps.setInt(4, barcode.getItemNumber());
					ps.setString(5, id.getText());
					ps.executeUpdate();
					status.setText("Barcode created!");
					barcodeGen.setText(null);
					list.removeAll(list);
					loadBarcodeItems();
					id.clear();
				} else {
					status.setText("ID not found!");
				}
			} else {
				status.setText("Generate barcode!");
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

	public void loadBarcodeItems() {
		try {
			conn = SQLiteConnection.Connector();
			String query = "SELECT * FROM items WHERE barcode IS NULL";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new BarcodeItemTable(rs.getInt("id"), rs.getString("itemname"), rs.getDouble("weight"),
						rs.getString("expiration"), rs.getString("itemdetail")));
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