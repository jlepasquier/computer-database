package com.excilys.computerdatabase.main.java.dao;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {

	private List<T> list;
	
	public Page(int elementsPerPage, int offset) {
		list = new ArrayList<>();
	}
	
	
	
}
