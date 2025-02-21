package john;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import john.exception.JohnException;
import john.task.Task;

/**
 * TaskList class for storing tasks in a list
 */
public class TaskList {
    private List<Task> taskList;

    /**
     * Create a new empty task list
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Initializes a new TaskList object with the given List of Tasks
     * @param taskList
     */
    public TaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Get whether or not this taskList is empty.
     * @return boolean value showing if this list is empty
     */
    public boolean isEmpty() {
        return this.taskList.isEmpty();
    }

    public int getSize() {
        return this.taskList.size();
    }

    /**
     * Add the given task to the task list.
     * @param task
     */
    public void addTask(Task task) {
        this.taskList.add(task);
    }

    /**
     * Mark the task at the specified index as done.
     * @param index
     */
    public void markAsDoneFromTaskList(int index) throws JohnException {
        assert index >= 0 : "Index should not be negative";

        try {
            this.taskList.get(index).markAsDone();

        } catch (IndexOutOfBoundsException ioobe) {
            throw new JohnException("Please input a valid index less than or equal to " + this.getSize() + ".");

        } catch (NumberFormatException nfe) {
            throw new JohnException("Please input a valid numerical index.");
        }
    }

    /**
     * Mark the task at the specified index as not done.
     * @param index
     */
    public void unmarkAsDoneFromTaskList(int index) throws JohnException {
        assert index >= 0 : "Index should not be negative";

        try {
            this.taskList.get(index).unmarkAsDone();

        } catch (IndexOutOfBoundsException ioobe) {
            throw new JohnException("Please input a valid index less than " + this.getSize() + ".");

        } catch (NumberFormatException nfe) {
            throw new JohnException("Please input a valid numerical index.");
        }
    }
    
    /**
     * Delete the task from the task list at the specified index and return it.
     * @param index
     * @return Task deleted from the list at the specified index
     */
    public Task deleteFromTaskList(int index) throws JohnException{
        assert index >= 0 : "Index should not be negative";
        assert this.taskList.size() > 0 :
            "TaskList should have something to delete";

        try {
            Task task = this.taskList.remove(index);
            return task;

        } catch (IndexOutOfBoundsException ioobe) {
            throw new JohnException("Please input a valid index less than " + this.getSize() + ".");

        } catch (NumberFormatException nfe) {
            throw new JohnException("Please input a valid numerical index.");
        }
    }

    public String getTaskListAsString(List<Task> tasklist) {
        String taskListString = "";
        int index = 1;

        for (Task task : this.taskList) {
            String indexString = index++ + ". ";
            String taskStringWithNewLine = task.toString() + "\n";

            taskListString += indexString + taskStringWithNewLine;
        }

        return taskListString;
    }

    public String getCurrentTaskListAsString() {
        return getTaskListAsString(this.taskList);
    }

    /**
     * Returns an unmodifiable List of Tasks
     * based on the currently stored task list.
     * @return Unmodifiable List of Tasks based on the currently stored task list
     */
    public List<Task> getTaskList() {
        return Collections.unmodifiableList(this.taskList);
    }

    public int getTotalExpense() {
        int totalExpense = 0;

        for (Task task : this.taskList) {
            totalExpense += task.getExpense();
        }

        return totalExpense;
    }

    /**
     * Returns the filtered list of the current task list
     * based on the given keyword.
     * @param str
     * @return Filtered list of tasks based on the given keyword
     */
    public List<Task> getFilteredTaskList(String str) {
        return this.taskList.stream()
            .filter(task -> task.getDescription().contains(str))
            .collect(Collectors.toList());
    }

    /**
     * Gets the string description of the task stored at the specified index.
     * @param index
     * @return String description of the task stored at the given index
     */
    public String getDescription(int index) throws JohnException{
        assert index >= 0 : "Index should not be negative";

        try {
            return this.taskList.get(index).getDescription();

        //Shouldn't reach here as other methods will throw an exception first
        } catch (IndexOutOfBoundsException ioobe) {
            throw new JohnException("Please input a valid index less than " + this.getSize() + ".");

        } catch (NumberFormatException nfe) {
            throw new JohnException("Please input a valid numerical index.");
        }
    }
}
