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
        ui.printHello();
        try {
            taskList = storage.getTaskListFromFile();
        } catch (FileNotFoundException e) {
            taskList = new TaskList();
            ui.printFileReadError(filePath);
            ui.printLinebreak();
        }
    }

    /**
     * Run the while loop for running the chatbot.
     * Takes in user input as a command on each iteration of the loop
     * and performs the specified action.
     * Exits once the user inputs 'bye'
     * @param args
     */
    public static void main(String[] args) {
        John john = new John("./data/john.txt");

        Scanner myObj = new Scanner(System.in);

        while (true) {
            john.ui.printLinebreak();
            String userInput = myObj.nextLine();
            john.ui.printLinebreak();

            if (userInput.equals("bye")) {
                break;

            } else if (userInput.startsWith("mark ")) {
                int index = Integer.parseInt(userInput.substring(5)) - 1;
                john.taskList.markAsDoneFromTaskList(index);
                john.ui.printMarkAsDone(john.taskList.getDescription(index));

            } else if (userInput.startsWith("unmark ")) {
                int index = Integer.parseInt(userInput.substring(7)) - 1;
                john.taskList.unmarkAsDoneFromTaskList(index);
                john.ui.printUnmarkAsDone(john.taskList.getDescription(index));

            } else if (userInput.startsWith("delete ")) {
                int index = Integer.parseInt(userInput.substring(7)) - 1;
                Task task = john.taskList.deleteFromTaskList(index);
                john.ui.printTaskDeletion(task);

            } else if (userInput.equals("list")) {
                if (john.taskList.isEmpty()) {
                    john.ui.printEmptyList();
                } else {
                    System.out.println("your list currently contains");
                    john.taskList.printCurrentTaskList();
                }
            } else if (userInput.startsWith("find ")) {
                String str = userInput.substring(5);

                System.out.println("below are your tasks " +
                    "that contain the word " +
                    "\"" + str + "\"");
                john.taskList.printTaskList(
                    john.taskList.getFilteredTaskList(str));
            } else {
                try {
                    Task task = InputTaskParser.createTask(userInput);
                    john.taskList.addTask(task);
                    john.ui.printTaskAddition(task);
                } catch (JohnException je) {
                    john.ui.printInvalidCommand();
                }
            }
        }

        john.storage.writeTaskListToFile(john.taskList.getTaskList());

        john.ui.printGoodbye();
    }

    public String getResponse(String userInput) {
        if (userInput.equals("bye")) {
            return ui.sayGoodbye();

        } else if (userInput.startsWith("mark ")) {
            int index = Integer.parseInt(userInput.substring(5)) - 1;
            try {
                taskList.markAsDoneFromTaskList(index);
                return ui.sayMarkAsDone(taskList.getDescription(index));

            } catch (IndexOutOfBoundsException ioobe) {
               return ("please input a proper index "
                    + "less than or equal to " + taskList.getSize());
    
            } catch (NumberFormatException nfe) {
                return ("please input a proper index "
                    + "in a numerical format");
            }

        } else if (userInput.startsWith("unmark ")) {
            int index = Integer.parseInt(userInput.substring(7)) - 1;

            try {
                taskList.unmarkAsDoneFromTaskList(index);
                return ui.sayUnmarkAsDone(taskList.getDescription(index));
    
            } catch (IndexOutOfBoundsException ioobe) {
                return ("please input a proper index "
                    + "less than or equal to " + taskList.getSize());
    
            } catch (NumberFormatException nfe) {
                return ("please input a proper index "
                    + "in a numerical format");
            }

        } else if (userInput.startsWith("delete ")) {
            try {
                int index = Integer.parseInt(userInput.substring(7)) - 1;
                Task task = taskList.deleteFromTaskList(index);
                return ui.sayTaskDeletion(task);

            } catch (IndexOutOfBoundsException ioobe) {
                return ("please input a proper index "
                    + "less than or equal to " + taskList.getSize());
                    
            } catch (NumberFormatException nfe) {
                return ("please input a proper index "
                    + "in a numerical format");
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
