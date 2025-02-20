package john;

import john.task.Task;

/**
 * Ui class for printing messages for user on the CLI
 */
public class Ui {

    public String sayHello() {
        return """
               Hello! I'm John, your personalized assistant chatbot!
               How can I help you today?
               """;
    }

    public String sayGoodbye() {
        return """
                Goodbye and have a nice day!
                """;
    }

    public String sayTaskAddition(Task task) {
        return "added \n"
                + task.toString() + "\n"
                + "to your list!";
    }

    public String sayTaskDeletion(Task task) {
        return "removed \n"
                + task.toString() + "\n"
                + "from your list!";
    }

    public String sayMarkAsDone(String description) {
        return "marking \"" + description + "\" as done!";
    }

    public String sayUnmarkAsDone(String description) {
        return "marking \"" + description + "\" as done!";
    }

    public String sayEmptyList() {
        return """
                your list is currently empty!
                type in any item to add it to your list!
                """;
    }

    public String sayInvalidCommand() {
        return "please input a proper command!";
    }

    public String sayPrintFileReadError(String filePath) {
        return "Error loading tasks from the file location at "
                + filePath + "\n"
                + "Initializing an empty task list";
    }

    public String sayOutOfBoundsError(int limit) {
        return ("please input a proper index "
        + "less than or equal to " + limit);
    }

    public String sayNumberFormatError() {
        return ("please enter a proper input "
        + "in a numerical format");
    }
}
