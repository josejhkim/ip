import java.io.FileNotFoundException;
import java.util.Scanner;

public class John {

    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    public John(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        ui.printHello();
        try {
            taskList = new TaskList(storage.getTaskListFromFile());
        } catch (FileNotFoundException e) {
            taskList = new TaskList();
            ui.printFileReadError(filePath);
            ui.linebreak();
        }
    }

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
