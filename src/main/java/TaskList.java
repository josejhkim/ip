import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            System.out.println("marking \"" + this.taskList.get(index).getDescription() + "\" as done!");
            this.taskList.get(index).markAsDone();

        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("please input a proper index less than or equal to " + this.taskList.size());

        } catch (NumberFormatException nfe) {
            System.out.println("please input a proper index in a numerical format");
        }
    }

    public void unmarkAsDoneFromTaskList(int index) {
        try {
            System.out.println("marking \"" + taskList.get(index).getDescription() + "\" as not done!");
            taskList.get(index).unmarkAsDone();

        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("please input a proper index less than or equal to " + taskList.size());

        } catch (NumberFormatException nfe) {
            System.out.println("please input a proper index in a numerical format");
        }
    }

    public void deleteFromTaskList(int index) {
        try {
            this.taskList.remove(index);
        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("please input a proper index less than or equal to " + this.taskList.size());
        } catch (NumberFormatException nfe) {
            System.out.println("please input a proper index in a numerical format");
        }
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
}
