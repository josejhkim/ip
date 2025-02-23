package john.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Task class for storing information regarding user's tasks
 */
public class Task {

    public static final String INVALID_FORMAT_ERROR =
        "The formatting of this task is incorrect";

    public static final String EMPTY_DESCRIPTION_ERROR =
        "Please input a proper task description.";

    protected String description;
    protected boolean isDone;

    private double expense;

    /**
     * Creates a new task object with the given description.
     * @param description
     */
    public Task(String description) {
        assert description.length() > 0 : "Description shouldn't be empty";

        this.description = description;
        this.isDone = false;
        this.expense = 0;
    }


    /**
     * Sets the expense for the task if it's found in the given task string.
     * If there isn't any, simply return.
     * @param taskString
     */
    public void setExpenseFromTaskString(String taskString) {
        Pattern pattern = Pattern.compile("\\$\\{([\\d,]+(?:\\.\\d+)?)\\}");
        Matcher matcher = pattern.matcher(taskString);

        if (!matcher.find()) {
            return;
        }

        String numberStr = matcher.group(1).replaceAll(",", "");
        try {
            double value = Double.parseDouble(numberStr);
            // Round to the nearest hundredth (two decimal places).
            this.expense = Math.round(value * 100.0) / 100.0;
        } catch (NumberFormatException e) {
            this.expense = 0.0;
        }
    }

    /**
     * Gets the expense of this task.
     * @return Double representing the expense of this task
     */
    public double getExpense() {
        return this.expense;
    }

    /**
     * Gets the status icon of whether the task is done or not.
     * @return String showing whether the task is done or not
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as undone.
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Gets the description of this task.
     * @return String containing the description for this task
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the 'is done' status of this task.
     * @return Boolean value for whether this task is done or not
     */
    public boolean getIsDone() {
        return this.isDone;
    }

    /**
     * Return the expense in a string format.
     * @return String representing the expense
     */
    public String getExpenseString() {
        return (this.getExpense() > 0 ? "${" + this.getExpense() + "}" : "");
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
