package john;

import john.task.Task;

/**
 * Ui class for printing messages for user on the CLI
 */
public class Ui {

    /**
     * Return a string for showing all available commands.
     * @return String showing all available commands
     */
    public String sayHelp() {
        return """
            Here are some commands you can use to get started!

            List commands

            "list" shows you your current task list
            "mark X" marks the X-th task as done
            "unmark X" marks the X-th task as NOT done
            "delete X" deletes the X-th task from your task list
            "find X" shows you your current task list only with tasks containing X
            "expense" shows you the total expense from all of your current tasks
            "bye" exits the app and saves your current task list.

            Task creation commands

            "todo X" creates a new todo task with the description X
            "deadline X /by yyyy-mm-dd" creates a new deadline task with the given deadline
            "event X /from start /to end" creates a new event task with the given duration start and end
            You can append ${Y} at the end of your task creation commands to save Y as the expense for that task
            """;
    }

    /**
     * Return a string for greeting the user.
     * @return String for greeting the user
     */
    public String sayHello() {
        return """
               Hello! I'm John, your personalized assistant chatbot!
               How can I help you today?
               """;
    }

    /**
     * Return a string saying bye to the user.
     * @return String saying goodbye
     */
    public String sayGoodbye() {
        return """
               Goodbye and have a nice day!
               """;
    }

    /**
     * Returns a string notifying that the given task has been added.
     * @param task
     * @return String representation of the newly added task
     */
    public String sayTaskAddition(Task task) {
        assert task != null : "The printed task shouldn't be null";

        return "Added"
                + "\n"
                + task.toString()
                + "\n"
                + "to your list!";
    }

    /**
     * Returns a string notifying that the given task has been deleted.
     * @param task
     * @return String representation of the deleted task
     */
    public String sayTaskDeletion(Task task) {
        assert task != null : "The printed task shouldn't be null";

        return "Removed"
                + "\n"
                + task.toString()
                + "\n"
                + "from your list!";
    }

    /**
     * Returns a string notifying that the given task is marked as done.
     * @param description
     * @return String representation of the marked task
     */
    public String sayMarkAsDone(String description) {
        assert description != null
            : "The printed description shouldn't be null";

        return "Marking \"" + description + "\" as done!";
    }

    /**
     * Returns a string notifying that the given task is marked as not done.
     * @param description
     * @return String representation of the marked task
     */
    public String sayUnmarkAsDone(String description) {
        assert description != null
            : "The printed description shouldn't be null";

        return "Marking \"" + description + "\" as not done!";
    }

    /**
     * Returns a string notifying of the current total expense.
     * @param expense
     * @return String representation of the current total expense
     */
    public String sayTotalExpense(double expense) {
        return "Your total expense from all the tasks are: $" + expense;
    }

    /**
     * Returns a string notifying that current list is empty.
     * @return String saying the current list is empty
     */
    public String sayEmptyList() {
        return """
                Your list is currently empty!
                type in any item to add it to your list!
                """;
    }

    /**
     * Returns a string alerting an exception with the given exception message.
     * @param exceptionMsg
     * @return String notifying of the given exception
     */
    public String sayException(String exceptionMsg) {
        return "There was an error :("
            + "\n"
            + exceptionMsg
            + "\n";
    }
}
