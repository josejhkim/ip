package john.task;

/**
 * Event class for storing user's task along with the duration of the event
 */
public class Event extends Task {

    public static final String EVENT_FORMAT_ERROR =
        "Please enter a proper event task "
        + "by formatting it as follows:"
        + "\n"
        + "event <description> /from <start> /to <end>"
        + "\n"
        + "The start and the end for the from and the to field "
        + "can be in any format.";

    protected String from;
    protected String to;

    /**
     * Creates a new event object with the given description and duration.
     * @param description
     * @param from
     * @param to
     */
    public Event(String description, String from, String to) {
        super(description);

        assert from.length() > 0 : "Event start shouldn't be null";
        assert to.length() > 0 : "Event end shouldn't be null";

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
                + " from: " + this.from
                + " to: " + this.to
                + " )"
                + " " + this.getExpenseString();
    }
}
