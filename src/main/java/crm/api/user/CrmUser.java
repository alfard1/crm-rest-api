package crm.api.user;

public class CrmUser {

	private String userName;
	private String password;
	private String matchingPassword;
	private String email;
	
	public CrmUser() {
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public String getEmail() {
		return email;
	}
}
