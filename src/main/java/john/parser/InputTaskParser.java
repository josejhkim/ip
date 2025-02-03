package john.parser;

import john.exception.JohnException;
import john.task.Deadline;
import john.task.Event;
import john.task.Task;
import john.task.Todo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class InputTaskParser {

    /**
     * Reads the user input and creates a corresponding todo object
     * if the input is valid.
     * Otherwise, throw an exception.
     * @param input
     * @return Todo object based on the user input
     * @throws JohnException
     */
    public static Todo createTodo(String input) throws JohnException {
        try {
            String desc = input.substring(5);
            if (desc.isEmpty()) {
                throw new JohnException("empty task description");
            }
            return new Todo(desc);
        } catch (StringIndexOutOfBoundsException sioobe) {
            //This shouldn't happen but just in case
            throw new JohnException("Invalid todo formatting");
        }

    }

    /**
     * Reads the user input and creates a corresponding deadline object
     * if the input is valid.
     * Otherwise, throw an exception.
     * @param input
     * @return Deadline object based on the user input
     * @throws JohnException
     */
    public static Deadline createDeadline(String input) throws JohnException {
        try {
            int deadlineIndex = input.indexOf("/by");

            if (deadlineIndex == -1) {
                System.out.println("please enter a proper deadline " +
                        "for this task by formatting it as follows:");
                System.out.println("deadline return book /by 2025-01-30");
                throw new JohnException("invalid deadline formatting");
            }

            LocalDate deadline = LocalDate.parse(
                    input.substring(deadlineIndex + 4));

            String desc = input.substring(9, deadlineIndex);

            if (desc.isEmpty()) {
                System.out.println("please input a proper task description");
                throw new JohnException("empty task description");
            }

            return new Deadline(desc, deadline);

        } catch (DateTimeParseException | StringIndexOutOfBoundsException
                dtpe) {
            System.out.println("please enter a proper deadline for this task "
                    + "by formatting it as follows:");
            System.out.println("deadline return book /by 2025-01-30");
            throw new JohnException("invalid deadline formatting");

        }
    }

    /**
     * Reads the user input and creates a corresponding event object
     * if the input is valid.
     * Otherwise, throw an exception.
     * @param input
     * @return Event object based on the user input
     * @throws JohnException
     */
    public static Event createEvent(String input) throws JohnException {
        try {
            int fromIndex = input.indexOf("/from");
            int toIndex = input.indexOf("/to", fromIndex + 1);
            String desc = input.substring(6, fromIndex);
            String from = input.substring(fromIndex + 6, toIndex - 1);
            String to = input.substring(toIndex + 4);

            return new Event(desc, from, to);

        } catch (StringIndexOutOfBoundsException sioobe) {
            System.out.println("please enter a proper event for this task " +
                    "by formatting it as follows:");
            System.out.println("event wine party " +
                    "/from Sunday 8pm /to Sunday 10pm");
            throw new JohnException("invalid event formatting");
        }
    }

    /**
     * Reads the user input and creates a corresponding task object
     * if the input is valid.
     * Otherwise, throw an exception.
     * @param input
     * @return Task object based on the user input
     * @throws JohnException
     */
    public static Task createTask(String input) throws JohnException {
        if (input.startsWith("todo ")) {
            return createTodo(input);

        } else if (input.startsWith("deadline ")) {
            return createDeadline(input);

        } else if (input.startsWith("event ")) {
            return createEvent(input);

        } else {
            throw new JohnException("Please input a proper task command");
        }
    }
}
