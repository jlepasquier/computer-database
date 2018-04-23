package main.java.com.excilys.computerdatabase.model;

/**
 * The Class Company.
 */
public class Company {

    /** The id. */
    private int id;

    /** The name. */
    private String name;

    /**
     * Instantiates a new company.
     */
    public Company() {
    }

    /**
     * Instantiates a new company.
     *
     * @param builder
     *            the company builder
     */
    public Company(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The private Class Builder, used to Instantiates a new company.
     */
    public static class Builder {

        /** The id. */
        private int id;

        /** The name. */
        private String name;

        /**
         * Instantiates a new builder.
         *
         * @param id
         *            the id, which is mandatory to build a new company
         */
        public Builder(int id) {
            this.id = id;
        }

        /**
         * Sets the builder name.
         *
         * @param name
         *            the name
         * @return the builder
         */
        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Builds the company.
         *
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

}
