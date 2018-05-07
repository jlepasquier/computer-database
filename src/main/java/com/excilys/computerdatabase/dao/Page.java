package main.java.com.excilys.computerdatabase.dao;

import java.util.List;

/**
 * The template Class Page.
 * @param <T> the generic type
 */
public class Page<T> {

    private int elementsPerPage;
    private int offset;
    private List<T> elements;

    /**
     * Instantiates a new page.
     */
    public Page() {
    }

    /**
     * Instantiates a new page.
     * @param elementsPerPage the elements per page
     * @param offset the offset
     * @param elements the elements
     */
    public Page(int elementsPerPage, int offset, List<T> elements) {
        this.elementsPerPage = elementsPerPage;
        this.offset = offset;
        this.elements = elements;
    }

    /**
     * Gets the elements per page.
     * @return the elements per page
     */
    public int getElementsPerPage() {
        return elementsPerPage;
    }

    /**
     * Sets the elements per page.
     * @param elementsPerPage the new elements per page
     */
    public void setElementsPerPage(int elementsPerPage) {
        this.elementsPerPage = elementsPerPage;
    }

    /**
     * Gets the offset.
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Sets the offset.
     * @param offset the new offset
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Gets the elements.
     * @return the elements
     */
    public List<T> getElements() {
        return elements;
    }

    /**
     * Sets the elements.
     * @param elements the new elements
     */
    public void setElements(List<T> elements) {
        this.elements = elements;
    }

}
