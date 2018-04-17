package com.excilys.computerdatabase.main.java.model;

import java.sql.Date;

public class Computer {
	private int id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Company company;

	public Computer() {
	}

	public Computer(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
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

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company=" + company + "]";
	}

	public static class Builder {
		private int id;
		private String name;
		private Date introduced;
		private Date discontinued;
		private Company company;

		public Builder(String name) {
			if (name == null) {
				throw new IllegalArgumentException("name can not be null");
			}
			this.name = name;
		}

		public Builder withId(int id) {
			this.id = id;
			return this;
		}

		public Builder withIntroduced(Date introduced) {
			this.introduced = introduced;
			return this;
		}

		public Builder withDiscontinued(Date discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public Builder withCompany(Company company) {
			this.company = company;
			return this;
		}

		public Computer build() {
			return new Computer(this);
		}
	}

}
