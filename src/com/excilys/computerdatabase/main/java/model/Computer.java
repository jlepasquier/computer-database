package com.excilys.computerdatabase.main.java.model;

import java.sql.Timestamp;
import java.util.Date;

public class Computer {
	private int id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private String company;

	public Computer() {
	}

	/**
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param company
	 */
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

	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public static class Builder {
		private int id;
		private String name;
		private Timestamp introduced;
		private Timestamp discontinued;
		private String company;

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

		public Builder withIntroduced(Timestamp introduced) {
			this.introduced = introduced;
			return this;
		}

		public Builder withDiscontinued(Timestamp discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public Builder withCompany(String company) {
			this.company = company;
			return this;
		}

		public Computer build() {
			return new Computer(this);
		}
	}

}
