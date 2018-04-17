package com.excilys.computerdatabase.test.java.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.computerdatabase.main.java.dao.ComputerDAO;
import com.excilys.computerdatabase.main.java.model.Company;
import com.excilys.computerdatabase.main.java.model.Computer;
import com.excilys.computerdatabase.main.java.persistence.Database;

class ComputerDAOTest {
	
	private static ComputerDAO dao;
	private static int entriesInDatabase;
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		dao = new ComputerDAO(Database.INSTANCE);
		entriesInDatabase = 575;
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetComputerList() throws Exception {
		List<Computer> cpulist = dao.getComputerList();
		assertEquals(cpulist.size(), 575);
	}

	@Test
	void testGetComputer() throws Exception {
		Computer cpu = dao.getComputer(1);
		assertTrue(cpu.getName().equals("MacBook Pro 15.4 inch"));
		assertEquals(cpu.getCompany().getId(), 1);
		
	}

	@Test
	void testCreateComputer() {
		
	}

	@Test
	void testUpdateComputer() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteComputer() {
		fail("Not yet implemented");
	}

}
