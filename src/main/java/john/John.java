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
            ui.linebreak();
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
            john.ui.linebreak();
            String userInput = myObj.nextLine();
            john.ui.linebreak();

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
}
