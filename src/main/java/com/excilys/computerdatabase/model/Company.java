package main.java.com.excilys.computerdatabase.model;

/**
 * The Class Company.
 */
public class Company {

    private Long id;
    private String name;

    /**
     * Instantiates a new company.
     * @param builder the company builder
     */
    public Company(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    /**
     * Gets the id.
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The private Class Builder, used to Instantiates a new company.
     */
    public static class Builder {

        private Long id;
        private String name;

        /**
         * Instantiates a new builder.
         * @param id the id, which is mandatory to build a new company
         */
        public Builder(Long id) {
            if (id == 0) {
                throw new IllegalArgumentException("id can not be equal to zero");
            }
            this.id = id;
        }

        /**
         * Sets the builder name.
         * @param name the name
         * @return the builder
         */
        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Builds the company.
         * @return the company
         */
        public Company build() {
            return new Company(this);
        }
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Company [id=" + id + ", name=" + name + "]";
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Company other = (Company) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
