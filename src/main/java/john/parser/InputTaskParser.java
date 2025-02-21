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

    private static final int START_DESC_TODO = "todo ".length();

    private static final int START_DESC_DEADLINE = "deadline ".length();
    private static final int START_DEADLINE = "/by ".length();

    private static final int START_DESC_EVENT = "event ".length();
    private static final int START_FROM = "/from ".length();
    private static final int START_TO = "/to ".length();

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

            String inputWithoutExpense = input;

            int expenseIndex = input.indexOf("${");
            if (expenseIndex >= 0) {
                inputWithoutExpense = input.substring(0, expenseIndex);
            }

            String desc = inputWithoutExpense.substring(START_DESC_TODO);
            if (desc.isEmpty()) {
                throw new JohnException(Task.EMPTY_DESCRIPTION_ERROR);
            }

            Todo todo = new Todo(desc);
            todo.setExpenseFromTaskString(input);
            return todo;
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
            String inputWithoutExpense = input;

            int expenseIndex = input.indexOf("${");
            if (expenseIndex >= 0) {
                inputWithoutExpense = input.substring(0, expenseIndex);
            }

            int deadlineIndex = inputWithoutExpense.indexOf("/by");

            LocalDate deadline = LocalDate.parse(
                    inputWithoutExpense.substring(deadlineIndex + START_DEADLINE));

            String desc = inputWithoutExpense.substring(START_DESC_DEADLINE, deadlineIndex);
            if (desc.isEmpty()) {
                throw new JohnException(Task.EMPTY_DESCRIPTION_ERROR);
            }

            Deadline dl = new Deadline(desc, deadline);
            dl.setExpenseFromTaskString(input);
            return dl;
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
            String inputWithoutExpense = input;

            int expenseIndex = input.indexOf("${");
            if (expenseIndex >= 0) {
                inputWithoutExpense = input.substring(0, expenseIndex);
            }

            int fromIndex = inputWithoutExpense.indexOf("/from");
            int toIndex = inputWithoutExpense.indexOf("/to", fromIndex + 1);

            String from = inputWithoutExpense.substring(fromIndex + START_FROM, toIndex - 1);
            String to = inputWithoutExpense.substring(toIndex + START_TO);

            String desc = inputWithoutExpense.substring(START_DESC_EVENT, fromIndex);

            Event event = new Event(desc, from, to);
            event.setExpenseFromTaskString(input);
            return event;
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
        String lowerCaseInput = input.toLowerCase();

        if (lowerCaseInput.startsWith("todo")) {
            return createTodo(input);
        }

        if (lowerCaseInput.startsWith("deadline")) {
            return createDeadline(input);
        }

        if (lowerCaseInput.startsWith("event")) {
            return createEvent(input);
        }

        throw new JohnException("Please input a proper task command");
    }
}
