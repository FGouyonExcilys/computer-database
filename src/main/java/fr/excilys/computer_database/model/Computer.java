package fr.excilys.computer_database.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Computer{

	@Id
	private int id;

	@Column(name="name")
	private String name;

	@Column(name="introduced")
	private LocalDate introduced;

	@Column(name="discontinued")
	private LocalDate discontinued;

	@JoinColumn(name="company_id")
	@ManyToOne
	private Company company;

	private Computer(ComputerBuilder builder) {
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		if (introduced != null) {
			if (this.discontinued != null) {
				if (this.discontinued.isAfter(introduced)) {
					this.introduced = introduced;
				}
			} else {
				this.introduced = introduced;
			}
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		if (discontinued != null) {
			if (this.introduced != null) {
				if (discontinued.isAfter(this.introduced)) {
					this.discontinued = discontinued;
				}
			} else {
				this.discontinued = discontinued;
			}
		}
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public static class ComputerBuilder {
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private Company company;

		public ComputerBuilder(String name) {
			this.name = name;
		}

		public ComputerBuilder setIntroduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerBuilder setDiscontinued(LocalDate discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public ComputerBuilder setCompany(Company company) {
			this.company = company;
			return this;
		}

		public Computer build() {
			return new Computer(this);
		}
	}

	@Override
	public String toString() {
		return "Computer [ id= " + id + ", name= " + name + ", introduced= " + introduced + ", discontinued= "
				+ discontinued + ", company= " + company.getName() + " ]";
	}

}
