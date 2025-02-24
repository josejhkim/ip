package john.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Event class for storing user's task along with the duration of the event
 */
public class Event extends Task {

    public static final String EVENT_FORMAT_ERROR =
        "Please enter a proper event task "
        + "by formatting it as follows:"
        + "\n"
        + "event <description> /from <YYYY-MM-DD> /to <YYYY-MM-DD>"
        + "\n"
        + "The start and the end for the from and the to field "
        + "should be in a YYYY-MM-DD format."
        + "\n"
        + "(i.e. 2025-01-20)";

    private static final DateTimeFormatter EVENT_FORMATTER =
        DateTimeFormatter.ofPattern("dd MMM yyyy");

    protected LocalDate from;
    protected LocalDate to;

    /**
     * Creates a new event object with the given description and duration.
     * @param description
     * @param from
     * @param to
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);

        assert from != null : "Event start shouldn't be null";
        assert to != null : "Event end shouldn't be null";

        this.from = from;
        this.to = to;
    }

    /**
     * Returns the string format of the event object.
     * Formats the event as "[E] {description} (from: {duration start} to: {duration end})".
     * @return String representation of the event object
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " ("
                + " from: " + this.from.format(EVENT_FORMATTER)
                + " to: " + this.to.format(EVENT_FORMATTER)
                + " )"
                + " " + this.getExpenseString();
    }
}
