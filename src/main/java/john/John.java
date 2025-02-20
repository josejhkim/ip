package john;

import java.io.FileNotFoundException;
import java.util.Scanner;

import john.exception.JohnException;
import john.parser.InputTaskParser;
import john.task.Task;

/**
 * Chatbot for storing user's tasks
 */
public class John {

    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    /**
     * Initialize a new John chatbot
     * with the given filePath as the location for storing  user's task list.
     * @param filePath
     */
    public John(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            taskList = storage.getTaskListFromFile();
        } catch (FileNotFoundException e) {
            taskList = new TaskList();
        }
    }

    public String createTask(String userInput) {
        try {
            Task task = InputTaskParser.createTask(userInput);
            taskList.addTask(task);
            return ui.sayTaskAddition(task);
        } catch (JohnException je) {
            return ui.sayInvalidCommand();
        }
    }

    public String markTask(String userInput) {
        int index = Integer.parseInt(userInput.substring(5)) - 1;
        try {
            taskList.markAsDoneFromTaskList(index);
            return ui.sayMarkAsDone(taskList.getDescription(index));

        } catch (IndexOutOfBoundsException ioobe) {
            return ui.sayOutOfBoundsError(taskList.getSize());

        } catch (NumberFormatException nfe) {
            return ui.sayNumberFormatError();
        }
    }

    public String unmarkTask(String userInput) {
        int index = Integer.parseInt(userInput.substring(7)) - 1;

        try {
            taskList.unmarkAsDoneFromTaskList(index);
            return ui.sayUnmarkAsDone(taskList.getDescription(index));

        } catch (IndexOutOfBoundsException ioobe) {
            return ui.sayOutOfBoundsError(taskList.getSize());

        } catch (NumberFormatException nfe) {
            return ui.sayNumberFormatError();
        }
    }

    public String deleteTask(String userInput) {
        try {
            int index = Integer.parseInt(userInput.substring(7)) - 1;
            Task task = taskList.deleteFromTaskList(index);
            return ui.sayTaskDeletion(task);

        } catch (IndexOutOfBoundsException ioobe) {
            return ui.sayOutOfBoundsError(taskList.getSize());

        } catch (NumberFormatException nfe) {
            return ui.sayNumberFormatError();
        }
    }

    public String getTasklistString() {
        if (taskList.isEmpty()) {
            return ui.sayEmptyList();
        } else {
            return taskList.getCurrentTaskListAsString();
        }
    }

    public String filterTasklist(String userInput) {
        String str = userInput.substring(5);

        return taskList.getTaskListAsString(
            taskList.getFilteredTaskList(str));
    }

    public String exitJohn() {
        storage.writeTaskListToFile(taskList.getTaskList());
        return ui.sayGoodbye();
    }

    public String getResponse(String userInput) {
        if (userInput.equals("bye")) {
            return exitJohn();

        } else if (userInput.equals("list")) {
            return getTasklistString();

        } else if (userInput.startsWith("mark ")) {
            return markTask(userInput);

        } else if (userInput.startsWith("unmark ")) {
            return unmarkTask(userInput);

        } else if (userInput.startsWith("delete ")) {
            return deleteTask(userInput);

        } else if (userInput.startsWith("find ")) {
            return filterTasklist(userInput);

        } else {
            return createTask(userInput);
        }
    }
}
