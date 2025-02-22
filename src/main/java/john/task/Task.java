package john.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Task class for storing information regarding user's tasks
 */
public class Task {
    protected String description;
    protected boolean isDone;

    public static final String INVALID_FORMAT_ERROR =
        "The formatting of this task is incorrect";

    public static final String EMPTY_DESCRIPTION_ERROR =
        "Please input a proper task description.";

    private int expense;

    /**
     * Create a new task object with the given description
     * @param description
     */
    public Task(String description) {
        assert description.length() > 0 : "Description shouldn't be empty";

        this.description = description;
        this.isDone = false;
        this.expense = 0;
    }

    /**
     * Gets the expense from given string for the task
     * if there is one, and set it to this task's expense
     * If not, set this task's expense to 0
     *
     * @param taskString
     * @return
     */
    public void setExpenseFromTaskString(String taskString) {
        Pattern pattern = Pattern.compile("\\$\\{(\\d+)\\}");
        Matcher matcher = pattern.matcher(taskString);

        if (matcher.find()) {
            // Parse the captured group (number) into an integer.
            this.expense = Integer.parseInt(matcher.group(1));
        }
    }

    public int getExpense() {
        return this.expense;
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
     * Get the 'is done' status of this task
     * @return Boolean value for whether this task is done or not
     */
    public boolean getIsDone() {
        return this.isDone;
    }

    /**
     * Returns the task object in a string format.
     * Formats the task as "[{statusIcon}] {description}".
     * @return String representation of the task object
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] "
                + this.description
                + (this.expense > 0 ? "${" + this.expense + "}" : "");
    }
}
