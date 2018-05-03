package main.java.com.excilys.computerdatabase.validator;

import java.time.LocalDate;

public class DateValidationResult {
    private boolean isValid;
    private LocalDate introduced;
    private LocalDate discontinued;

    public DateValidationResult() {
    }

    public DateValidationResult(boolean isValid, LocalDate introduced, LocalDate discontinued) {
        super();
        this.isValid = isValid;
        this.introduced = introduced;
        this.discontinued = discontinued;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public LocalDate getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

}
