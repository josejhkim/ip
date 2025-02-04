package john;

import john.task.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TaskList {
    private List<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public TaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public boolean isEmpty() {
        return this.taskList.isEmpty();
    }

    public void addTask(Task task) {
        this.taskList.add(task);
    }

    public void markAsDoneFromTaskList(int index) {
        try {
            this.taskList.get(index).markAsDone();

        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("please input a proper index less than or equal to " + this.taskList.size());

        } catch (NumberFormatException nfe) {
            System.out.println("please input a proper index in a numerical format");
        }
    }

    public void unmarkAsDoneFromTaskList(int index) {
        try {
            taskList.get(index).unmarkAsDone();

        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("please input a proper index less than or equal to " + taskList.size());

        } catch (NumberFormatException nfe) {
            System.out.println("please input a proper index in a numerical format");
        }
    }

    public Task deleteFromTaskList(int index) {
        try {
            Task task = this.taskList.get(index);
            this.taskList.remove(index);
            return task;
        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("please input a proper index less than or equal to " + this.taskList.size());
        } catch (NumberFormatException nfe) {
            System.out.println("please input a proper index in a numerical format");
        }
        return null;
    }

    public void printTaskList(List<Task> taskList) {
        int index = 1;
        for (Task task : taskList) {
            System.out.println(
                    index++ + ". " + task.toString()
            );
        }
    }

    public void printCurrentTaskList() {
        printTaskList(this.taskList);
    }

    public List<Task> getTaskList() {
        return Collections.unmodifiableList(taskList);
    }

    public List<Task> getFilteredTaskList(String str) {
        return this.taskList.stream()
            .filter(task -> task.getDescription().contains(str)).collect(Collectors.toList());

    }

    public String getDescription(int index) {
        try {
            return this.taskList.get(index).getDescription();

        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("please input a proper index less than or equal to " + taskList.size());

        } catch (NumberFormatException nfe) {
            System.out.println("please input a proper index in a numerical format");
        }
        return "";
    }
}
