package john;

import john.task.Task;

/**
 * Ui class for printing messages for user on the CLI
 */
public class Ui {

    /**
     * Print the hello message.
     */
    public void printHello() {
        printLinebreak();
        System.out.println("Hello! I'm John, "
            + "your personalized assistant chatbot");
        System.out.println("How can I help you today?");
    }

    public String sayHello() {
        return """
               Hello! I'm John, your personalized assistant chatbot!
               How can I help you today?
               """;
    }

    /**
     * Print the goodbye message.
     */
    public void printGoodbye() {
        System.out.println("Goodbye and have a nice day!");
        printLinebreak();
    }

    public String sayGoodbye() {
        return """
                Goodbye and have a nice day!
                """;
    }

    /**
     * Print a visible line break
     */
    public void printLinebreak() {
        System.out.println("----------------------------"
                + "--------------------------------");
    }

    /**
     * Print the given task as being added
     * @param task
     */
    public void printTaskAddition(Task task) {
        assert task != null : "The printed task shouldn't be null";
        System.out.println("added");
        System.out.println(task);
        System.out.println("to your list!");
    }

    public String sayTaskAddition(Task task) {
        assert task != null : "The printed task shouldn't be null";

        return "added \n"
                + task.toString() + "\n"
                + "to your list!";
    }

    /**
     * Print the given task as being deleted
     * @param task
     */
    public void printTaskDeletion(Task task) {
        assert task != null : "The printed task shouldn't be null";

        System.out.println("removed");
        System.out.println(task);
        System.out.println("from your list!");
    }

    public String sayTaskDeletion(Task task) {
        assert task != null : "The printed task shouldn't be null";

        return "removed \n"
                + task.toString() + "\n"
                + "from your list!";
    }

    /**
     * Print the given task description as done
     * @param description
     */
    public void printMarkAsDone(String description) {
        assert description != null :
            "The printed description shouldn't be null";

        System.out.println("marking \"" + description + "\" as done!");
    }

    public String sayMarkAsDone(String description) {
        assert description != null :
            "The printed description shouldn't be null";

        return "marking \"" + description + "\" as done!";
    }

    /**
     * Print the given task description as not done
     * @param description
     */
    public void printUnmarkAsDone(String description) {
        assert description != null :
            "The printed description shouldn't be null";

        System.out.println("marking \"" + description + "\" as not done!");
    }

    public String sayUnmarkAsDone(String description) {
        assert description != null :
            "The printed description shouldn't be null";

        return "marking \"" + description + "\" as done!";
    }

    /**
     * Print a notification saying the list is empty
     */
    public void printEmptyList() {
        System.out.println("your list is currently empty!");
        System.out.println("type in any item to add it to your list!");
    }

    public String sayEmptyList() {
        return """
                your list is currently empty!
                type in any item to add it to your list!
                """;
    }

    /**
     * Print a notification saying the command is invalid
     */
    public void printInvalidCommand() {
        System.out.println("please input a proper command!");
    }

    public String sayInvalidCommand() {
        return "please input a proper command!";
    }

    /**
     * Print a notification saying there was an error reading the file
     * @param filePath
     */
    public void printFileReadError(String filePath) {
        assert filePath.length() > 0 : "The given filePath shouldn't be empty";

        System.out.println("Error loading tasks "
            + "from the file location at " + filePath);
        System.out.println("Initializing an empty task list");
    }

    public String sayPrintFileReadError(String filePath) {
        assert filePath.length() > 0 : "The given filePath shouldn't be empty";

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
