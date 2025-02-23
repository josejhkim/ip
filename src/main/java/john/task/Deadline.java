package john.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Deadline class for storing user's task along with a deadline
 */
public class Deadline extends Task {

    public static final String DEADLINE_FORMAT_ERROR =
        "Please enter a proper deadline task "
        + "by formatting it as follows: "
        + "\n"
        + "deadline <description> /by <YYYY-MM-DD>"
        + "\n"
        + "The deadline for the by field should be in a YYYY-MM-DD format."
        + "\n"
        + "(i.e. 2025-01-20)";

    private static final DateTimeFormatter DEADLINE_FORMATTER =
        DateTimeFormatter.ofPattern("dd MMM yyyy");

    protected LocalDate by;

    /**
     * Creates a new Deadline object with the given description and deadline.
     *
     * @param description
     * @param by
     */
    public Deadline(String description, LocalDate by) {
        super(description);

        assert by != null : "The deadline shouldn't be null";

        this.by = by;
    }

    /**
     * Returns the deadline object in a string format.
     * Formats the deadline object as "[D] {description} (by: {deadline})".
     * Formats the deadline itself as dd-mm-yyyy (eg 31-01-2025).
     * @return String representation of the deadline object
     */
    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: "
                + by.format(DEADLINE_FORMATTER)
                + ")"
                + " " + this.getExpenseString();
    }
}
