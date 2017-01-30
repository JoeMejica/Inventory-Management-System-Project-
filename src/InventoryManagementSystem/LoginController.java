package InventoryManagementSystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.LoginModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

public class LoginController {
	private LoginModel loginModel = new LoginModel();

	@FXML
	private Button loginIssueBtn;

	@FXML
	private Button loginBtn;

	@FXML
	private Label lblStatus;

	@FXML
	private TextField txtUserName;

	@FXML
	private PasswordField loginPassword;

	private Stage stage;
	private Parent root;
	private Connection conn;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	// username: admin password: admin
	public void Login(ActionEvent actionEvent) throws IOException, SQLException {
		try {
			if (loginModel.isLogin(txtUserName.getText(), loginPassword.getText())) {
				if (loginModel.isLock(txtUserName.getText())) {
					lblStatus.setText("Account locked, please notify admin!");
				} else {
					resetLockCount();
					stage = (Stage) loginBtn.getScene().getWindow();

					stage.setTitle("I.M.S. | Main Menu");

					// load mainMenu scene
					root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));

					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.show();
				}
			} else if (loginModel.isUser(txtUserName.getText())) {
				if (loginModel.isLock(txtUserName.getText())) {
					lblStatus.setText("Account locked, please notify admin!");
				} else {
					// correct username wrong password get 4 tries before lockout
					lblStatus.setText("Incorrect password, please try again!");
					loginModel.AddLock(txtUserName.getText());
				}
			} else if(txtUserName.getText().equals("")) {
				lblStatus.setText("Please enter username!");
			} else {
				lblStatus.setText("Username does not exist!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void resetLockCount(){
    	try {
			conn = SQLiteConnection.Connector();
			String query = "UPDATE employee SET lockcount = ?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, 0);
			ps.executeUpdate();
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
	
    public void exitProgram(ActionEvent actionEvent) {
		Platform.exit();
    }
}
