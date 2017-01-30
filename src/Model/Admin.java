package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import InventoryManagementSystem.SQLiteConnection;

public class Admin extends Employee {
	private Connection conn;
	private PreparedStatement ps = null;
	public Admin(String firstName, String middleInitial, String lastName, String username, String PhoneNumber,
			String Email, String emergencyContactName, String emergencyNumber, String emergencyEmail) {
		super();
		admin = true;
	}

	public Admin(Employee employee) {
		this.firstName = employee.firstName;
		this.middleInitial = employee.middleInitial;
		this.lastName = employee.lastName;
		this.username = employee.username;
		this.emergencyContactName = employee.emergencyContactName;
		this.password = employee.password;
		this.emergencyContact = employee.emergencyContact;
		this.contactInformation = employee.contactInformation;
	}

	public Employee addEmployee(String firstName, String middleInitial, String lastName, String username,
			String PhoneNumber, String Email, String emergencyContactName, String emergencyNumber,
			String emergencyEmail) {
		return new Employee(firstName, middleInitial, lastName, username, PhoneNumber, Email, emergencyContactName,
				emergencyNumber, emergencyEmail);
	}

	public void removeEmployee(String firstName, String lastName) {
		try {
			conn = SQLiteConnection.Connector();
			String query = "DELETE FROM employee WHERE firstname=? AND lastname=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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

//	public ArrivalEvent modifyArrivalEvent() {
//
//	}

}
