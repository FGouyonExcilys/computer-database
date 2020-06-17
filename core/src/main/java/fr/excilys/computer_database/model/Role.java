package fr.excilys.computer_database.model;

public class Role {

	private String username;
	private String user_role;
	
	public Role(String username, String user_role) {
		super();
		this.username = username;
		this.user_role = user_role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserRole() {
		return user_role;
	}

	public void setUserRole(String user_role) {
		this.user_role = user_role;
	}
	
}
