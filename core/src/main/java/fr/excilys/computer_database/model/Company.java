package fr.excilys.computer_database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;

	private Company(CompanyBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
	}

	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static class CompanyBuilder {
		private int id;
		private String name;

		public CompanyBuilder() {
		}

		public CompanyBuilder setId(int id) {
			this.id = id;
			return this;
		}

		public CompanyBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public Company build() {
			return new Company(this);
		}
	}

	@Override
	public String toString() {
		return "Company [ id= " + id + ", name= " + name + " ]";
	}

}
