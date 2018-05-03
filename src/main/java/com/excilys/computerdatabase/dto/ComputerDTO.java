package main.java.com.excilys.computerdatabase.dto;

public class ComputerDTO {

    long id;
    String name;
    String introduced;
    String discontinued;
    String companyName;

    public ComputerDTO() {
    }

    /**
     * @param computerName
     * @param introduced
     * @param discontinued
     * @param company
     */
    public ComputerDTO(long computerId, String computerName, String introduced, String discontinued, String company) {
        this.id = computerId;
        this.name = computerName;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.companyName = company;
    }

    /**
     * @return the computerId
     */
    public long getId() {
        return id;
    }

    /**
     * @param computerId the computerId to set
     */
    public void setId(long computerId) {
        this.id = computerId;
    }

    /**
     * @return the computerName
     */
    public String getName() {
        return name;
    }

    /**
     * @param computerName the computerName to set
     */
    public void setName(String computerName) {
        this.name = computerName;
    }

    /**
     * @return the introduced
     */
    public String getIntroduced() {
        return introduced;
    }

    /**
     * @param introduced the introduced to set
     */
    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    /**
     * @return the discontinued
     */
    public String getDiscontinued() {
        return discontinued;
    }

    /**
     * @param discontinued the discontinued to set
     */
    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    /**
     * @return the company
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param company the company to set
     */
    public void setCompanyName(String company) {
        this.companyName = company;
    }

}
