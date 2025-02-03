package john.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Deadline class for storing user's task along with a deadline
 */
public class Deadline extends Task {

    protected LocalDate by;

    /**
     * Create a new Deadline object with the given description and deadline.
     *
     * @param description
     * @param by
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the deadline object in a string format.
     * Formats the deadline object as "[D] {description} (by: {deadline})"
     * Formats the deadline itself as dd-mm-yyyy (eg 31-01-2025).
     * @return String representation of the deadline object
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + by.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                + ")";
    }
}
