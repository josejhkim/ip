package john;

import john.task.Task;

public class Ui {

    /**
     * Print the hello message.
     */
    public void printHello() {
        linebreak();
        System.out.println("Hello! I'm John, " +
                "your personalized assistant chatbot");
        System.out.println("How can I help you today?");
    }

    /**
     * Print the goodbye message.
     */
    public void printGoodbye() {
        System.out.println("Goodbye and have a nice day!");
        linebreak();
    }

    /**
     * Print a visible line break
     */
    public void linebreak() {
        System.out.println("----------------------------" +
                "--------------------------------");
    }

    /**
     * Print the given task as being added
     * @param task
     */
    public void printTaskAddition(Task task) {
        System.out.println("added");
        System.out.println(task);
        System.out.println("to your list!");
    }

    /**
     * Print the given task as being deleted
     * @param task
     */
    public void printTaskDeletion(Task task) {
        System.out.println("removed");
        System.out.println(task);
        System.out.println("from your list!");
    }

    /**
     * Print the given task description as done
     * @param description
     */
    public void printMarkAsDone(String description) {
        System.out.println("marking \"" + description + "\" as done!");
    }

    /**
     * Print the given task description as not done
     * @param description
     */
    public void printUnmarkAsDone(String description) {
        System.out.println("marking \"" + description + "\" as not done!");
    }

    /**
     * Print a notification saying the list is empty
     */
    public void printEmptyList() {
        System.out.println("your list is currently empty!");
        System.out.println("type in any item to add it to your list!");
    }

    /**
     * Print a notification saying the command is invalid
     */
    public void printInvalidCommand() {
        System.out.println("please input a proper command!");
    }

    /**
     * Print a notification saying there was an error reading the file
     * @param filePath
     */
    public void printFileReadError(String filePath) {
        System.out.println("Error loading tasks " +
                "from the file location at " + filePath);
        System.out.println("Initializing an empty task list");
    }
}
