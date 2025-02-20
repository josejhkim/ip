package john;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public void markAsDoneFromTaskList(int index) {
        assert index >= 0 : "Index should not be negative";
        this.taskList.get(index).markAsDone();
    }

    /**
     * Mark the task at the specified index as not done.
     * @param index
     */
    public void unmarkAsDoneFromTaskList(int index) {
        assert index >= 0 : "Index should not be negative";
        taskList.get(index).unmarkAsDone();
    }
    
    /**
     * Delete the task from the task list at the specified index.
     * @param index
     * @return Task deleted from the list at the specified index
     */
    public Task deleteFromTaskList(int index) {
        assert index >= 0 : "Index should not be negative";
        assert this.taskList.size() > 0 :
            "TaskList should have something to delete";

        Task task = this.taskList.get(index);
        this.taskList.remove(index);
        return task;
    }

    /**
     * Prints any given List of Tasks.
     * @param taskList
     */
    public void printTaskList(List<Task> taskList) {
        int index = 1;
        for (Task task : taskList) {
            System.out.println(
                    index++ + ". " + task.toString()
            );
        }
    }

    /**
     * Prints the currently stored task list.
     */
    public void printCurrentTaskList() {
        printTaskList(this.taskList);
    }

    public String getTaskListAsString(List<Task> tasklist) {
        String ret = "";
        int index = 1;
        for (Task task : taskList) {
            ret += index++ + ". " + task.toString() + "\n";
        }
        return ret;
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
        return Collections.unmodifiableList(taskList);
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
    public String getDescription(int index) {
        assert index >= 0 : "Index should not be negative";

        try {
            return this.taskList.get(index).getDescription();

        //Shouldn't reach here as other methods will throw an exception first
        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("please input a proper index "
                + "less than or equal to " + taskList.size());

        } catch (NumberFormatException nfe) {
            System.out.println("please input a proper index "
                + "in a numerical format");
        }
        return "";
    }
}
