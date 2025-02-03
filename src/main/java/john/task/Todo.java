package john.task;

public class Todo extends Task {
    /**
     * Create a new Todo object with the given description.
     *
     * @param description
     */
    public Todo (String description) {
        super(description);
    }

    /**
     * Return the string format of the todo object.
     * Formats the event as "[T] {description}".
     * @return String format of the todo object
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
