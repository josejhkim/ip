package john;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
     * Initializes a new TaskList object with the given List<Task>
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
        try {
            this.taskList.get(index).markAsDone();

        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("please input a proper index " +
                    "less than or equal to " + this.taskList.size());

        } catch (NumberFormatException nfe) {
            System.out.println("please input a proper index " +
                    "in a numerical format");
        }
    }

    /**
     * Mark the task at the specified index as not done.
     * @param index
     */
    public void unmarkAsDoneFromTaskList(int index) {
        try {
            taskList.get(index).unmarkAsDone();

        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("please input a proper index " +
                    "less than or equal to " + taskList.size());

        } catch (NumberFormatException nfe) {
            System.out.println("please input a proper index " +
                    "in a numerical format");
        }
    }

    /**
     * Delete the task from the task list at the specified index.
     * @param index
     * @return Task deleted from the list at the specified index
     */
    public Task deleteFromTaskList(int index) {
        try {
            Task task = this.taskList.get(index);
            this.taskList.remove(index);
            return task;
        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("please input a proper index " +
                    "less than or equal to " + this.taskList.size());
        } catch (NumberFormatException nfe) {
            System.out.println("please input a proper index " +
                    "in a numerical format");
        }
        return null;
    }

    /**
     * Prints any given Task<List>.
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

    /**
     * Returns an unmodifiable Task<List>
     * based on the currently stored task list.
     * @return Unmodifiable List<Task> based on the currently stored task list
     */
    public List<Task> getTaskList() {
        return Collections.unmodifiableList(taskList);
    }

    /**
     * Gets the string description of the task stored at the specified index.
     * @param index
     * @return String description of the task stored at the given index
     */
    public String getDescription(int index) {
        try {
            return this.taskList.get(index).getDescription();

        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("please input a proper index " +
                    "less than or equal to " + taskList.size());

        } catch (NumberFormatException nfe) {
            System.out.println("please input a proper index " +
                    "in a numerical format");
        }
        return "";
    }
}
