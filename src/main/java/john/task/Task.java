package john.task;

/**
 * Task class for storing information regarding user's tasks
 */
public class Task {
    protected String description;
    protected boolean isDone;

    public static final String INVALID_FORMAT_ERROR = "invalid task formatting";
    public static final String EMPTY_DESCRIPTION_ERROR = "please input a proper task description";

    /**
     * Create a new task object with the given description
     * @param description
     */
    public Task(String description) {
        assert description.length() > 0 : "Description shouldn't be empty";

        this.description = description;
        this.isDone = false;
    }

    /**
     * Get the status icon of whether the task is done or not
     * @return String showing whether the task is done or not
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Mark this task as done
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Mark this task as undone
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Get the description of this task
     * @return String containing the description for this task
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the task object in a string format.
     * Formats the task as "[{statusIcon}] {description}".
     * @return String representation of the task object
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] "
                + this.description;
    }
}
