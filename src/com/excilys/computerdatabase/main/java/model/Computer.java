package com.excilys.computerdatabase.main.java.model;

import java.util.Date;

public class Computer {
	private String name;
	private Date introduced;
	private Date discontinued;
	private String company;

	public Computer() {
	}

	/**
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param company
	 */
	public Computer(String name, Date introduced, Date discontinued, String company) {
		super();
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the date the computer was introduced
	 */
	public Date getIntroduced() {
		return introduced;
	}

	/**
	 * @param introduced
	 *            the date the computer was introduced to set
	 */
	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	/**
	 * @return the date the computer was discontinued
	 */
	public Date getDiscontinued() {
		return discontinued;
	}

	/**
	 * @param discontinued
	 *            the date the computer was discontinued to set
	 */
	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

}
