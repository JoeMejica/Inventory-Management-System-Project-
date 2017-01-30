package InventoryManagementSystem;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UsersController implements Initializable {

	@FXML
	private TableView<UserTable> table;

	@FXML
	private TableColumn<UserTable, String> firstNameCol;

	@FXML
	private TableColumn<UserTable, String> middleInitCol;

	@FXML
	private TableColumn<UserTable, String> lastNameCol;

	@FXML
	private TableColumn<UserTable, String> userNameCol;

	@FXML
	private TableColumn<UserTable, String> passwordCol;

	@FXML
	private TableColumn<UserTable, String> phoneCol;

	@FXML
	private TableColumn<UserTable, String> emailCol;

	@FXML
	private TableColumn<UserTable, String> contactNameCol;

	@FXML
	private TableColumn<UserTable, String> contactEmailCol;

	@FXML
	private TableColumn<UserTable, String> contactNumCol;

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
	private Button backBtn;

	// STAGE AND BUTTON NAVIGATION VARIABLES AND FUNCTIONS:

	private Stage stage;
	private Parent root;
	private Connection conn;
	private ObservableList<UserTable> list = FXCollections.observableArrayList();
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		firstNameCol.setCellValueFactory(new PropertyValueFactory<UserTable, String>("firstName"));
		middleInitCol.setCellValueFactory(new PropertyValueFactory<UserTable, String>("middleInit"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<UserTable, String>("lastName"));
		userNameCol.setCellValueFactory(new PropertyValueFactory<UserTable, String>("userName"));
		passwordCol.setCellValueFactory(new PropertyValueFactory<UserTable, String>("password"));
		phoneCol.setCellValueFactory(new PropertyValueFactory<UserTable, String>("phoneNumber"));
		emailCol.setCellValueFactory(new PropertyValueFactory<UserTable, String>("email"));
		contactNameCol.setCellValueFactory(new PropertyValueFactory<UserTable, String>("contactName"));
		contactEmailCol.setCellValueFactory(new PropertyValueFactory<UserTable, String>("contactEmail"));
		contactNumCol.setCellValueFactory(new PropertyValueFactory<UserTable, String>("contactNumber"));
		loadUsers();
	}

	public void loadUsers() {
		try {
			conn = SQLiteConnection.Connector();
			String query = "SELECT * FROM employee";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new UserTable(rs.getString("firstname"), rs.getString("middleinitial"),
						rs.getString("lastname"), rs.getString("username"), rs.getString("password"),
						rs.getString("phonenumber"), rs.getString("email"), rs.getString("contactname"),
						rs.getString("contactnumber"), rs.getString("contactemail")));
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

	public void backEvent(ActionEvent actionEvent) throws IOException {

		stage = (Stage) backBtn.getScene().getWindow();

		stage.setTitle("I.M.S. | Manage Users Menu");

		root = FXMLLoader.load(getClass().getResource("manageUsers.fxml"));

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

	// TODO: INSERT REMAINING METHODS HERE

}
