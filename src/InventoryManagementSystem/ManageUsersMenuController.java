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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Model.Admin;
import Model.Contact;
import Model.Employee;

public class ManageUsersMenuController implements Initializable {

	private ObservableList<String> userChoices = FXCollections.observableArrayList("Admin", "Employee");

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
	private Button viewAllUsersBtn;

	@FXML
	private Button submitFormBtn;

	@FXML
	private Button modifyBtn;

	@FXML
	private ComboBox<String> userTypeCBox;

	@FXML
	private TextField firstNameField;

	@FXML
	private TextField middleInitialField;

	@FXML
	private TextField lastNameField;

	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private TextField contactNumberField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField emergencyContactField;

	@FXML
	private TextField emergencyNumberField;

	@FXML
	private TextField emergencyEmailField;

	@FXML
	private Label status;

	// STAGE AND BUTTON NAVIGATION VARIABLES AND FUNCTIONS:

	private Stage stage;
	private Parent root;
	private Connection conn;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userTypeCBox.setVisibleRowCount(1);
		userTypeCBox.setItems(userChoices);
		userTypeCBox.setValue("Employee");
	}

	public void submitForm(ActionEvent actionEvent) {
		try {
			conn = SQLiteConnection.Connector();
			Employee hire = new Employee();
			Admin admin;

			Contact confirm;
			Contact confirmEmergency;
			String compareX = null;
			String compareY = null;
			String check = userTypeCBox.getSelectionModel().getSelectedItem();
			boolean level = false;
			if (check == "Admin") {
				level = true;
			}
			String x = "";
			if (!(firstNameField.getText().equals(x))) {
				status.setText(null);
				if (!(lastNameField.getText().equals(x))) {
					status.setText(null);
					if (!(usernameField.getText().equals(x))) {
						status.setText(null);
						if (!(passwordField.getText().equals(x))) {
							status.setText(null);
							if (!(emergencyContactField.getText().equals(x))) {
								status.setText(null);
								hire.setAdmin(level);
								hire.setFirstName(firstNameField.getText());
								hire.setMiddleInitial(middleInitialField.getText());
								hire.setLastName(lastNameField.getText());
								hire.setUsername(usernameField.getText());
								hire.setPassword(passwordField.getText());
								hire.setEmergencyContactName(emergencyContactField.getText());
								confirm = new Contact(contactNumberField.getText(), emailField.getText());
								confirmEmergency = new Contact(emergencyNumberField.getText(),
										emergencyEmailField.getText());
								compareX = confirm.getEmailAddress();
								compareY = emailField.getText();
								if (compareX.equals(compareY)) {
									compareX = confirm.getPhoneNumber();
									compareY = contactNumberField.getText();
									if (compareX.equals(compareY)) {
										compareX = confirmEmergency.getEmailAddress();
										compareY = emergencyEmailField.getText();
										if (compareX.equals(compareY)) {
											compareX = confirmEmergency.getPhoneNumber();
											compareY = emergencyNumberField.getText();
											if (compareX.equals(compareY)) {
												String number = contactNumberField.getText().replaceAll("[^0-9]+", "");
												String area = number.substring(0, 3);
												String three = number.substring(3, 6);
												String four = number.substring(6, 10);
												String phoneNumber = area + "-" + three + "-" + four;
												number = emergencyNumberField.getText().replaceAll("[^0-9]+", "");
												area = number.substring(0, 3);
												three = number.substring(3, 6);
												four = number.substring(6, 10);
												String emNumber = area + "-" + three + "-" + four;
												String query = "SELECT * FROM employee WHERE username = ?";
												ps = conn.prepareStatement(query);
												ps.setString(1, hire.getUsername());
												rs = ps.executeQuery();
												if (rs.next()) {
													status.setText("Username exists!");
												} else {
													query = "SELECT * FROM employee WHERE email = ?";
													ps = conn.prepareStatement(query);
													ps.setString(1, emailField.getText());
													rs = ps.executeQuery();
													if (rs.next()) {
														status.setText("Email exists!");
													} else {
														if (level) {
															admin = new Admin(hire);
															query = "INSERT INTO employee (firstname, middleinitial, lastname, username, password, phonenumber, email, admin, contactname, contactnumber, contactemail)"
																	+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
															ps = conn.prepareStatement(query);
															ps.setString(1, admin.getFirstName());
															ps.setString(2, admin.getMiddleInitial());
															ps.setString(3, admin.getLastName());
															ps.setString(4, admin.getUsername());
															ps.setString(5, admin.getPassword());
															ps.setString(6, phoneNumber);
															ps.setString(7, emailField.getText());
															ps.setBoolean(8, level);
															ps.setString(9, admin.getEmergencyContactName());
															ps.setString(10, emNumber);
															ps.setString(11, emergencyEmailField.getText());
															ps.executeUpdate();
															firstNameField.clear();
															middleInitialField.clear();
															lastNameField.clear();
															usernameField.clear();
															passwordField.clear();
															contactNumberField.clear();
															emailField.clear();
															emergencyContactField.clear();
															emergencyEmailField.clear();
															emergencyNumberField.clear();
															status.setText("New user created!");
														} else {
															query = "INSERT INTO employee (firstname, middleinitial, lastname, username, password, phonenumber, email, admin, contactname, contactnumber, contactemail)"
																	+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
															ps = conn.prepareStatement(query);
															ps.setString(1, hire.getFirstName());
															ps.setString(2, hire.getMiddleInitial());
															ps.setString(3, hire.getLastName());
															ps.setString(4, hire.getUsername());
															ps.setString(5, hire.getPassword());
															ps.setString(6, phoneNumber);
															ps.setString(7, emailField.getText());
															ps.setBoolean(8, hire.getAdmin());
															ps.setString(9, hire.getEmergencyContactName());
															ps.setString(10, emNumber);
															ps.setString(11, emergencyEmailField.getText());
															ps.executeUpdate();
															firstNameField.clear();
															middleInitialField.clear();
															lastNameField.clear();
															usernameField.clear();
															passwordField.clear();
															contactNumberField.clear();
															emailField.clear();
															emergencyContactField.clear();
															emergencyEmailField.clear();
															emergencyNumberField.clear();
															status.setText("New user created!");
														}
													}
												}
											} else {
												status.setText("Incorrect Field - Emergency Contact #");
											}
										} else {
											status.setText("Incorrect Field - Emergency Contact Email");
										}
									} else {
										status.setText("Incorrect Field - Phone #");
									}
								} else {
									status.setText("Incorrect Field - Email Address");
								}
							} else {
								status.setText("Empty field!");
							}
						} else {
							status.setText("Empty field!");
						}
					} else {
						status.setText("Empty field!");
					}
				} else {
					status.setText("Empty field!");
				}
			} else {
				status.setText("Empty field!");
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

	public void modifyUser(ActionEvent event) throws IOException {
		stage = (Stage) signOutIMS.getScene().getWindow();

		stage.setTitle("I.M.S. | Modify User Menu");

		root = FXMLLoader.load(getClass().getResource("modifyUsers.fxml"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void viewUserDB(ActionEvent actionEvent) throws IOException {
		stage = (Stage) signOutIMS.getScene().getWindow();

		stage.setTitle("I.M.S. | View User Menu");

		root = FXMLLoader.load(getClass().getResource("users.fxml"));

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
