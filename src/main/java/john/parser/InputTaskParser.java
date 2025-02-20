package john.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import john.exception.JohnException;
import john.task.Deadline;
import john.task.Event;
import john.task.Task;
import john.task.Todo;

/**
 * Parser class for parsing user's inputs and creating corresponding tasks
 */
public class InputTaskParser {

    private static final int START_DESC_TODO = "todo ".length() + 1;

    private static final int START_DESC_DEADLINE = "deadline ".length() + 1;
    private static final int START_DEADLINE = "/by ".length() + 1;

    private static final int START_DESC_EVENT = "event ".length() + 1;
    private static final int START_FROM = "/from ".length() + 1;
    private static final int START_TO = "/to ".length() + 1;

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
            String desc = input.substring(START_DESC_TODO);
            if (desc.isEmpty()) {
                throw new JohnException(Task.EMPTY_DESCRIPTION_ERROR);
            }

            return new Todo(desc);
        } catch (StringIndexOutOfBoundsException sioobe) {
            //This shouldn't happen but just in case
            throw new JohnException(Task.INVALID_FORMAT_ERROR);
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

            LocalDate deadline = LocalDate.parse(
                    input.substring(START_DEADLINE));

            String desc = input.substring(START_DESC_DEADLINE, deadlineIndex);
            if (desc.isEmpty()) {
                throw new JohnException(Task.EMPTY_DESCRIPTION_ERROR);
            }

            return new Deadline(desc, deadline);
        } catch (DateTimeParseException | StringIndexOutOfBoundsException
            dtpe) {
            throw new JohnException(Deadline.DEADLINE_FORMAT_ERROR);
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

            String from = input.substring(fromIndex + START_FROM, toIndex - 1);
            String to = input.substring(toIndex + START_TO);

            String desc = input.substring(START_DESC_EVENT, fromIndex);

            return new Event(desc, from, to);
        } catch (StringIndexOutOfBoundsException sioobe) {
            throw new JohnException(Event.EVENT_FORMAT_ERROR);
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
        }

        if (input.startsWith("deadline ")) {
            return createDeadline(input);
        }

        if (input.startsWith("event ")) {
            return createEvent(input);
        }

        throw new JohnException("Please input a proper task command");
    }
}
