package fr.excilys.computer_database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="user_role")
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
