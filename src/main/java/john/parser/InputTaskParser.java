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
    private static final int LENGTH_FROM = "2021-01-01".length();
    private static final int LENGTH_TO = "2021-01-01".length();

    /**
     * Reads the user input and creates a corresponding todo object.
     * If the input is invalid, throw an exception.
     * @param input
     * @return Todo object based on the user input
     * @throws JohnException if the input is invalid
     */
    public static Todo createTodo(String input) throws JohnException {
        try {

            String inputWithoutExpense = input;

            int expenseIndex = input.indexOf("${");
            if (expenseIndex >= 0) {
                inputWithoutExpense = input.substring(0, expenseIndex).trim();
            }

            String desc = inputWithoutExpense.substring(START_DESC_TODO).trim();
            if (desc.isEmpty()) {
                throw new JohnException(Task.EMPTY_DESCRIPTION_ERROR);
            }

            Todo todo = new Todo(desc);
            todo.setExpenseFromTaskString(input);
            return todo;
        } catch (StringIndexOutOfBoundsException sioobe) {
            //This shouldn't happen but just in case
            throw new JohnException(Todo.TODO_FORMAT_ERROR);
        }

    }

    /**
     * Reads the user input and creates a corresponding deadline object.
     * If the input is invalid, throw an exception.
     * @param input
     * @return Deadline object based on the user input
     * @throws JohnException if the input is invalid
     */
    public static Deadline createDeadline(String input) throws JohnException {
        try {
            String inputWithoutExpense = input;

            int expenseIndex = input.indexOf("${");
            if (expenseIndex >= 0) {
                inputWithoutExpense = input.substring(0, expenseIndex).trim();
            }

            int deadlineIndex = inputWithoutExpense.indexOf("/by");

            LocalDate deadline = LocalDate.parse(
                    inputWithoutExpense.substring(deadlineIndex + START_DEADLINE));

            String desc = inputWithoutExpense.substring(START_DESC_DEADLINE, deadlineIndex).trim();
            if (desc.isEmpty()) {
                throw new JohnException(Task.EMPTY_DESCRIPTION_ERROR);
            }

            Deadline dl = new Deadline(desc, deadline);
            dl.setExpenseFromTaskString(input);
            return dl;
        } catch (DateTimeParseException | StringIndexOutOfBoundsException
            invalidFormatException) {
            throw new JohnException(Deadline.DEADLINE_FORMAT_ERROR);
        }
    }

    /**
     * Reads the user input and returns a corresponding event object.
     * If the input is invalid, throw an exception.
     * @param input
     * @return Event object based on the user input
     * @throws JohnException if the input is invalid
     */
    public static Event createEvent(String input) throws JohnException {
        try {
            String inputWithoutExpense = input;

            int expenseIndex = input.indexOf("${");
            if (expenseIndex >= 0) {
                inputWithoutExpense = input.substring(0, expenseIndex).trim();
            }

            int fromIndex = inputWithoutExpense.indexOf("/from");
            int toIndex = inputWithoutExpense.indexOf("/to", fromIndex + 1);

            LocalDate from = LocalDate.parse(
                inputWithoutExpense.substring(fromIndex + START_FROM, fromIndex + START_FROM + LENGTH_FROM));
            LocalDate to = LocalDate.parse(
                inputWithoutExpense.substring(toIndex + START_TO, toIndex + START_TO + LENGTH_TO));

            String desc = inputWithoutExpense.substring(START_DESC_EVENT, fromIndex).trim();
            if (desc.isEmpty()) {
                throw new JohnException(Task.EMPTY_DESCRIPTION_ERROR);
            }

            Event event = new Event(desc, from, to);
            event.setExpenseFromTaskString(input);
            return event;
        } catch (DateTimeParseException | StringIndexOutOfBoundsException
            invalidFormattingException) {
            throw new JohnException(Event.EVENT_FORMAT_ERROR);
        }
    }

    /**
     * Reads the user input and returns a corresponding task object.
     * If the input is invalid, throw an exception.
     * @param input
     * @return Task object based on the user input
     * @throws JohnException if the input is invalid
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
