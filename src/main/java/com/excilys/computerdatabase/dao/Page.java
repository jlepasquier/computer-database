package main.java.com.excilys.computerdatabase.dao;


import java.util.List;

public class Page<T> {

	private int elementsPerPage;
	private int offset;
	private List<T> elements;
	
	public Page(int elementsPerPage, int offset, List<T> elements) {
		this.elementsPerPage = elementsPerPage;
		this.offset = offset;
		this.elements = elements;
	}

	public int getElementsPerPage() {
		return elementsPerPage;
	}

	public void setElementsPerPage(int elementsPerPage) {
		this.elementsPerPage = elementsPerPage;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public List<T> getElements() {
		return elements;
	}

	public void setElements(List<T> elements) {
		this.elements = elements;
	}
	
	
	
	
	
}
