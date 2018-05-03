package main.java.com.excilys.computerdatabase.dto;

public class CompanyDTO {

    private int id;
    private String name;

    public CompanyDTO() {
    }

    public CompanyDTO(int id, String name) {
        super();
        this.id = id;
        this.name = name;
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
}
