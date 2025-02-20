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

    public String getResponse(String userInput) {
        if (userInput.equals("bye")) {
            storage.writeTaskListToFile(taskList.getTaskList());
            return ui.sayGoodbye();

        } else if (userInput.startsWith("mark ")) {
            int index = Integer.parseInt(userInput.substring(5)) - 1;
            try {
                taskList.markAsDoneFromTaskList(index);
                return ui.sayMarkAsDone(taskList.getDescription(index));

            } catch (IndexOutOfBoundsException ioobe) {
                return ui.sayOutOfBoundsError(taskList.getSize());

            } catch (NumberFormatException nfe) {
                return ui.sayNumberFormatError();
            }

        } else if (userInput.startsWith("unmark ")) {
            int index = Integer.parseInt(userInput.substring(7)) - 1;

            try {
                taskList.unmarkAsDoneFromTaskList(index);
                return ui.sayUnmarkAsDone(taskList.getDescription(index));
    
            } catch (IndexOutOfBoundsException ioobe) {
                return ui.sayOutOfBoundsError(taskList.getSize());

            } catch (NumberFormatException nfe) {
                return ui.sayNumberFormatError();
            }

        } else if (userInput.startsWith("delete ")) {
            try {
                int index = Integer.parseInt(userInput.substring(7)) - 1;
                Task task = taskList.deleteFromTaskList(index);
                return ui.sayTaskDeletion(task);

            } catch (IndexOutOfBoundsException ioobe) {
                return ui.sayOutOfBoundsError(taskList.getSize());

            } catch (NumberFormatException nfe) {
                return ui.sayNumberFormatError();
            }

        } else if (userInput.equals("list")) {
            if (taskList.isEmpty()) {
                return ui.sayEmptyList();
            } else {
                return taskList.getCurrentTaskListAsString();
            }
        } else if (userInput.startsWith("find ")) {
            String str = userInput.substring(5);

            return taskList.getTaskListAsString(
                taskList.getFilteredTaskList(str));
        } else {
            try {
                Task task = InputTaskParser.createTask(userInput);
                taskList.addTask(task);
                return ui.sayTaskAddition(task);
            } catch (JohnException je) {
                return ui.sayInvalidCommand();
            }
        }
    }
}
