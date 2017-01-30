package InventoryManagementSystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import Model.Contact;
import Model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

public class ModifyUsersController implements Initializable {
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
	private Button findUserBtn;

	@FXML
	private Button removeBtn;

	@FXML
	private Button backBtn;

	@FXML
	private Button unlockBtn;

	@FXML
	private TextField checkUserField;

	@FXML
	private Label curConEm;

	@FXML
	private Label curNum;

	@FXML
	private Label curEmail;

	@FXML
	private Label curConNum;

	@FXML
	private Label curCon;

	@FXML
	private Label curPass;

	@FXML
	private Label curUser;

	@FXML
	private Label curLast;

	@FXML
	private Label curMid;

	@FXML
	private Label curFirst;

	@FXML
	private Label curType;

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
	private TextField passwordField;

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

	public void findUser(ActionEvent event) {
		try {
			if (checkUserField.getText() != null) {
				if (!(checkUserField.getText().trim().isEmpty())) {
					conn = SQLiteConnection.Connector();
					String query = "SELECT * FROM employee WHERE username =?";
					ps = conn.prepareStatement(query);
					ps.setString(1, checkUserField.getText());
					rs = ps.executeQuery();
					if (rs.next()) {
						curFirst.setText(rs.getString("firstname"));
						curMid.setText(rs.getString("middleinitial"));
						curLast.setText(rs.getString("lastname"));
						curUser.setText(rs.getString("username"));
						curPass.setText(rs.getString("password"));
						curNum.setText(rs.getString("phonenumber"));
						curEmail.setText(rs.getString("email"));
						if (rs.getBoolean("admin") == true) {
							curType.setText("Admin");
						} else {
							curType.setText("Employee");
						}
						curCon.setText(rs.getString("contactname"));
						curConNum.setText(rs.getString("contactnumber"));
						curConEm.setText(rs.getString("contactemail"));
						firstNameField.setText(curFirst.getText());
						middleInitialField.setText(curMid.getText());
						lastNameField.setText(curLast.getText());
						usernameField.setText(curUser.getText());
						passwordField.setText(curPass.getText());
						contactNumberField.setText(curNum.getText());
						emailField.setText(curEmail.getText());
						userTypeCBox.setValue(curType.getText());
						emergencyContactField.setText(curCon.getText());
						emergencyNumberField.setText(curConNum.getText());
						emergencyEmailField.setText(curConEm.getText());
						status.setText(null);
					} else {
						status.setText("Username not found!");
						curFirst.setText(null);
						curMid.setText(null);
						curLast.setText(null);
						curUser.setText(null);
						curPass.setText(null);
						curNum.setText(null);
						curEmail.setText(null);
						curType.setText(null);
						curCon.setText(null);
						curConNum.setText(null);
						curConEm.setText(null);
					}
				} else {
					status.setText("Please enter user to modify!");
				}
			} else {
				status.setText("Please enter user to modify!");
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

	public void removeUser(ActionEvent actionEvent) {
		try {
			conn = SQLiteConnection.Connector();
			if (checkUserField.getText() != null) {
				if (!(checkUserField.getText().trim().isEmpty())) {
					String query = "SELECT * FROM employee WHERE username=?";
					ps = conn.prepareStatement(query);
					ps.setString(1, checkUserField.getText());
					rs = ps.executeQuery();
					if (rs.next()) {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("I.M.S. | Modify User Menu - Deleting user!");
						alert.setHeaderText("WARNING! You are about to delete a user!");
						alert.setContentText("Are you sure?");

						ButtonType buttonTypeOne = new ButtonType("Yes");
						ButtonType buttonTypeCancel = new ButtonType("No", ButtonData.CANCEL_CLOSE);

						alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == buttonTypeOne) {
							query = "DELETE FROM employee WHERE username=?";
							ps = conn.prepareStatement(query);
							ps.setString(1, checkUserField.getText());
							ps.executeUpdate();
							status.setText("User removed!");
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
							status.setText("User has been deleted!");
							curFirst.setText(null);
							curMid.setText(null);
							curLast.setText(null);
							curUser.setText(null);
							curPass.setText(null);
							curNum.setText(null);
							curEmail.setText(null);
							curType.setText(null);
							curCon.setText(null);
							curConNum.setText(null);
							curConEm.setText(null);
							checkUserField.setText(null);
						} else {
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
							status.setText(null);
							curFirst.setText(null);
							curMid.setText(null);
							curLast.setText(null);
							curUser.setText(null);
							curPass.setText(null);
							curNum.setText(null);
							curEmail.setText(null);
							curType.setText(null);
							curCon.setText(null);
							curConNum.setText(null);
							curConEm.setText(null);
							checkUserField.setText(null);
						}
					} else {
						status.setText("Username not found!");
					}
				} else {
					status.setText("Please enter user to modify!");
				}
			} else {
				status.setText("Please enter user to modify!");
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

	public void submitForm(ActionEvent actionEvent) {
		try {
			conn = SQLiteConnection.Connector();
			if (checkUserField.getText() != null) {
				if (!(checkUserField.getText().trim().isEmpty())) {
					Employee hire = new Employee();
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
														String number = contactNumberField.getText()
																.replaceAll("[^0-9]+", "");
														String area = number.substring(0, 3);
														String three = number.substring(3, 6);
														String four = number.substring(6, 10);
														String phoneNumber = "(" + area + ")" + "-" + three + "-"
																+ four;
														number = emergencyNumberField.getText().replaceAll("[^0-9]+",
																"");
														area = number.substring(0, 3);
														three = number.substring(3, 6);
														four = number.substring(6, 10);
														String emNumber = "(" + area + ")" + "-" + three + "-" + four;
														if (hire.getUsername().equals(curUser.getText())
																&& emailField.getText().equals(curEmail.getText())) {
															String query = "UPDATE employee SET firstname=?, middleinitial=?, lastname=?, password=?, phonenumber=?, admin=?, contactname=?, contactnumber=?, contactemail=? WHERE username=?";
															ps = conn.prepareStatement(query);
															ps.setString(1, hire.getFirstName());
															ps.setString(2, hire.getMiddleInitial());
															ps.setString(3, hire.getLastName());
															ps.setString(4, hire.getPassword());
															ps.setString(5, phoneNumber);
															ps.setBoolean(6, level);
															ps.setString(7, hire.getEmergencyContactName());
															ps.setString(8, emNumber);
															ps.setString(9, emergencyEmailField.getText());
															ps.setString(10, hire.getUsername());
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
															status.setText("User modified!");
															curFirst.setText(null);
															curMid.setText(null);
															curLast.setText(null);
															curUser.setText(null);
															curPass.setText(null);
															curNum.setText(null);
															curEmail.setText(null);
															curType.setText(null);
															curCon.setText(null);
															curConNum.setText(null);
															curConEm.setText(null);
															checkUserField.setText(null);
														} else if (hire.getUsername().equals(curUser.getText())
																&& !(emailField.getText().equals(curEmail.getText()))) {
															String query = "SELECT * FROM employee WHERE email = ?";
															ps = conn.prepareStatement(query);
															ps.setString(1, emailField.getText());
															rs = ps.executeQuery();
															if (rs.next()) {
																curFirst.setText(null);
																curMid.setText(null);
																curLast.setText(null);
																curUser.setText(null);
																curPass.setText(null);
																curNum.setText(null);
																curEmail.setText(null);
																curType.setText(null);
																curCon.setText(null);
																curConNum.setText(null);
																curConEm.setText(null);
																checkUserField.setText(null);
																status.setText("Email taken! Try again!");
															} else {
																query = "UPDATE employee SET firstname=?, middleinitial=?, lastname=?, password=?, phonenumber=?, admin=?, contactname=?, contactnumber=?, contactemail=?, email=? WHERE username=?";
																ps = conn.prepareStatement(query);
																ps.setString(1, hire.getFirstName());
																ps.setString(2, hire.getMiddleInitial());
																ps.setString(3, hire.getLastName());
																ps.setString(4, hire.getPassword());
																ps.setString(5, phoneNumber);
																ps.setBoolean(6, level);
																ps.setString(7, hire.getEmergencyContactName());
																ps.setString(8, emNumber);
																ps.setString(9, emergencyEmailField.getText());
																ps.setString(10, emailField.getText());
																ps.setString(11, hire.getUsername());
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
																status.setText("User modified!");
																curFirst.setText(null);
																curMid.setText(null);
																curLast.setText(null);
																curUser.setText(null);
																curPass.setText(null);
																curNum.setText(null);
																curEmail.setText(null);
																curType.setText(null);
																curCon.setText(null);
																curConNum.setText(null);
																curConEm.setText(null);
																checkUserField.setText(null);
															}
														} else if (!(hire.getUsername().equals(curUser.getText()))
																&& emailField.getText().equals(curEmail.getText())) {
															String query = "SELECT * FROM employee WHERE username = ?";
															ps = conn.prepareStatement(query);
															ps.setString(1, hire.getUsername());
															rs = ps.executeQuery();
															if (rs.next()) {
																curFirst.setText(null);
																curMid.setText(null);
																curLast.setText(null);
																curUser.setText(null);
																curPass.setText(null);
																curNum.setText(null);
																curEmail.setText(null);
																curType.setText(null);
																curCon.setText(null);
																curConNum.setText(null);
																curConEm.setText(null);
																checkUserField.setText(null);
																status.setText("Username taken! Try again!");
															} else {
																query = "UPDATE employee SET firstname=?, middleinitial=?, lastname=?, password=?, phonenumber=?, admin=?, contactname=?, contactnumber=?, contactemail=?, username=? WHERE email=?";
																ps = conn.prepareStatement(query);
																ps.setString(1, hire.getFirstName());
																ps.setString(2, hire.getMiddleInitial());
																ps.setString(3, hire.getLastName());
																ps.setString(4, hire.getPassword());
																ps.setString(5, phoneNumber);
																ps.setBoolean(6, level);
																ps.setString(7, hire.getEmergencyContactName());
																ps.setString(8, emNumber);
																ps.setString(9, emergencyEmailField.getText());
																ps.setString(10, hire.getUsername());
																ps.setString(11, emailField.getText());
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
																status.setText("User modified!");
																curFirst.setText(null);
																curMid.setText(null);
																curLast.setText(null);
																curUser.setText(null);
																curPass.setText(null);
																curNum.setText(null);
																curEmail.setText(null);
																curType.setText(null);
																curCon.setText(null);
																curConNum.setText(null);
																curConEm.setText(null);
																checkUserField.setText(null);
															}
														} else if (!(hire.getUsername().equals(curUser.getText()))
																&& !(emailField.getText().equals(curEmail.getText()))) {
															String query = "SELECT * FROM employee WHERE username = ?";
															ps = conn.prepareStatement(query);
															ps.setString(1, hire.getUsername());
															rs = ps.executeQuery();
															if (rs.next()) {
																curFirst.setText(null);
																curMid.setText(null);
																curLast.setText(null);
																curUser.setText(null);
																curPass.setText(null);
																curNum.setText(null);
																curEmail.setText(null);
																curType.setText(null);
																curCon.setText(null);
																curConNum.setText(null);
																curConEm.setText(null);
																checkUserField.setText(null);
																status.setText("Username taken! Try again!");
															} else {
																query = "SELECT * FROM employee WHERE email = ?";
																ps = conn.prepareStatement(query);
																ps.setString(1, emailField.getText());
																rs = ps.executeQuery();
																if (rs.next()) {
																	curFirst.setText(null);
																	curMid.setText(null);
																	curLast.setText(null);
																	curUser.setText(null);
																	curPass.setText(null);
																	curNum.setText(null);
																	curEmail.setText(null);
																	curType.setText(null);
																	curCon.setText(null);
																	curConNum.setText(null);
																	curConEm.setText(null);
																	checkUserField.setText(null);
																	status.setText("Email taken! Try again!");
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
																	ps.setBoolean(8, level);
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
																	status.setText("User modified!");
																	query = "DELETE FROM employee WHERE username=?";
																	ps = conn.prepareStatement(query);
																	ps.setString(1, curUser.getText());
																	ps.executeUpdate();
																	curFirst.setText(null);
																	curMid.setText(null);
																	curLast.setText(null);
																	curUser.setText(null);
																	curPass.setText(null);
																	curNum.setText(null);
																	curEmail.setText(null);
																	curType.setText(null);
																	curCon.setText(null);
																	curConNum.setText(null);
																	curConEm.setText(null);
																	checkUserField.setText(null);
																}
															}
														} else {
															status.setText("Something went wrong!");
														}
													} else {
														status.setText("Please check contact number field!");
													}
												} else {
													status.setText("Please check contact email field!");
												}
											} else {
												status.setText("Please check number field!");
											}
										} else {
											status.setText("Please check email field!");
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
				} else {
					status.setText("Please enter user to modify!");
				}
			} else {
				status.setText("Please enter user to modify!");
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

	public void unlockUser(ActionEvent actionEvent) {
		try {
			if(checkUserField.getText() != null) {
				if (!(checkUserField.getText().trim().isEmpty())) {
					conn = SQLiteConnection.Connector();
					String query = "SELECT * FROM employee WHERE username=?";
					ps = conn.prepareStatement(query);
					ps.setString(1, checkUserField.getText());
					rs = ps.executeQuery();
					if (rs.next()) {
						query = "UPDATE employee SET lock = ?, lockcount = ? WHERE username = ?";
						ps = conn.prepareStatement(query);
						ps.setBoolean(1, false);
						ps.setInt(2, 0);
						ps.setString(3, checkUserField.getText());
						ps.executeUpdate();
						status.setText("User unlocked!");
					} else {
						status.setText("Username not found!");
					}
				} else {
					status.setText("Please enter user to modify!");
				}
			} else {
				status.setText("Please enter user to modify!");
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

	public void backEvent(ActionEvent actionEvent) throws IOException {
		stage = (Stage) signOutIMS.getScene().getWindow();

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
}
