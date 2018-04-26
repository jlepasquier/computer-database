package main.java.com.excilys.computerdatabase.model;

import java.time.LocalDate;

/**
 * The Class Computer.
 */
public class Computer {

    private long id;
    private String name;
    private LocalDate introduced;
    private LocalDate discontinued;
    private Company company;

    /**
     * Instantiates a new computer.
     * @param builder the company builder
     */
    public Computer(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }

    /**
     * Gets the id.
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     * @param id the new id
     */
    public void setId(long id) {
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
     * Gets the date the computer was introduced.
     * @return the introduced
     */
    public LocalDate getIntroduced() {
        return introduced;
    }

    /**
     * Sets the date the computer was introduced.
     * @param introduced the new introduced
     */
    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    /**
     * Gets the date the computer was discontinued.
     * @return the date the computer was discontinued
     */
    public LocalDate getDiscontinued() {
        return discontinued;
    }

    /**
     * Sets the date the computer was discontinued.
     * @param discontinued the new date the computer was discontinued
     */
    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    /**
     * Gets the company.
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Sets the company.
     * @param company the new company
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * The private Class Builder, used to instantiate a new computer.
     */
    public static class Builder {

        /** The id. */
        private long id;

        /** The name. */
        private String name;

        /** The introduced. */
        private LocalDate introduced;

        /** The discontinued. */
        private LocalDate discontinued;

        /** The company. */
        private Company company;

        /**
         * Instantiates a new builder.
         * @param name the name
         */
        public Builder(String name) {
            if (name == null) {
                throw new IllegalArgumentException("name can not be null");
            }
            this.name = name;
        }

        /**
         * Sets the builder id.
         * @param id the id
         * @return the builder
         */
        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the builder introduced.
         * @param introduced the introduced
         * @return the builder
         */
        public Builder withIntroduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }

        /**
         * Sets the builder discontinued.
         * @param discontinued the discontinued
         * @return the builder
         */
        public Builder withDiscontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        /**
         * Sets the builder company.
         * @param company the company
         * @return the builder
         */
        public Builder withCompany(Company company) {
            this.company = company;
            return this;
        }

        /**
         * Builds the computer.
         * @return the computer
         */
        public Computer build() {
            return new Computer(this);
        }
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
                + ", company=" + company + "]";
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
        result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
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
        Computer other = (Computer) obj;
        if (company == null) {
            if (other.company != null) {
                return false;
            }
        } else if (!company.equals(other.company)) {
            return false;
        }
        if (discontinued == null) {
            if (other.discontinued != null) {
                return false;
            }
        } else if (!discontinued.equals(other.discontinued)) {
            return false;
        }
        if (introduced == null) {
            if (other.introduced != null) {
                return false;
            }
        } else if (!introduced.equals(other.introduced)) {
            return false;
        }
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
