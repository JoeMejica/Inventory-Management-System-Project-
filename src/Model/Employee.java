package Model;

public class Employee {
	protected String firstName;
	protected String middleInitial;
	protected String lastName;
	protected String username;
	protected String password;
	protected Contact contactInformation;
	protected Contact emergencyContact;
	protected String emergencyContactName;
	protected boolean admin;

	public Employee() {
	}

	public Employee(String firstName, String middleInitial, String lastName, String username, String PhoneNumber,
			String Email, String emergencyContactName, String emergencyNumber, String emergencyEmail) {
		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
		this.username = username;
		password = "tempPass";
		contactInformation = new Contact(PhoneNumber, Email);
		emergencyContact = new Contact(emergencyNumber, emergencyEmail);
		this.emergencyContactName = emergencyContactName;
		admin = false;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return contactInformation.getEmailAddress();
	}

	public String getPhoneNumber() {
		return contactInformation.getPhoneNumber();
	}

	public String getEmergencyContactName() {
		return emergencyContactName;
	}

	public String getEmergencyContactNumber() {
		return emergencyContact.getPhoneNumber();
	}

	public String getEmergencyContactEmail() {
		return emergencyContact.getEmailAddress();
	}

	public boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean userType) {
		admin = userType;
	}

	public void setFirstName(String newName) {
		firstName = newName;
	}

	public void setMiddleInitial(String newMiddle) {
		middleInitial = newMiddle;
	}

	public void setLastName(String newLastName) {
		lastName = newLastName;
	}

	public void setUsername(String newUser) {
		username = newUser;
	}

	public void setPassword(String newPass) {
		password = newPass;
	}

	public void setEmail(String newEmail) {
		contactInformation.setEmailAddress(newEmail);
	}

	public void setPhoneNumber(String newNumber) {
		contactInformation.setPhoneNumber(newNumber);
	}

	public void setEmergencyContactName(String newName) {
		emergencyContactName = newName;
	}

	public void setEmergencyContactNumber(String newNumber) {
		emergencyContact.setPhoneNumber(newNumber);
	}

	public void setEmergencyContactEmail(String newEmail) {
		emergencyContact.setEmailAddress(newEmail);
	}
	/*
	 * assignArrivalStatus(); assignDepartureStatus();
	 */

}
