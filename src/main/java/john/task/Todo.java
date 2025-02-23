package john.task;

/**
 * Todo class for storing a "todo" task
 */
public class Todo extends Task {

    public static final String TODO_FORMAT_ERROR =
        "Please enter a proper todo task "
            + "by formatting it as follows:"
            + "\n"
            + "todo read books";

    /**
     * Creates a new Todo object with the given description.
     *
     * @param description
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string format of the todo object.
     * Formats the event as "[T] {description}".
     * @return String format of the todo object
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
