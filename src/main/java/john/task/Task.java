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

    private double expense;

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
     * Extracts the first double found for the given task string formatted as ${double},
     * and sets it as the expense for the task.
     * If there isn't any, simply return
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

    public double getExpense() {
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
