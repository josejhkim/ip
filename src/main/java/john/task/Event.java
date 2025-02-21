package john.task;

/**
 * Event class for storing user's task along with the duration of the event
 */
public class Event extends Task {

    protected String from;
    protected String to;

    public static final String EVENT_FORMAT_ERROR = "Please enter a proper event for this task "
        + "by formatting it as follows:"
        + "\n"
        + "event wine party /from Sunday 8pm /to Sunday 10pm"
        + "\n"
        + "The from and the to field can be in any format.";


    /**
     * Create a new event object with the given description and duration.
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
                + " )";
    }
}
