package fr.excilys.computer_database.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	private String username;

	@Id
	private String user_role;

	public Role() {}
	
	private Role(RoleBuilder roleBuilder) {
		this.username = roleBuilder.username;
		this.user_role = roleBuilder.user_role;
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

	public static class RoleBuilder {
		private String username;
		private String user_role;

		public RoleBuilder() {
		}

		public RoleBuilder setUsername(String username) {
			this.username = username;
			return this;
		}

		public RoleBuilder setRoleName(String user_role) {
			this.user_role = user_role;
			return this;
		}

		public Role build() {
			return new Role(this);
		}
	}

}
