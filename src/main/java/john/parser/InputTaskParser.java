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

    private static final int OFFSET_DESC_TODO = "todo ".length();

    private static final int OFFSET_DESC_DEADLINE = "deadline ".length();
    private static final int OFFSET_DEADLINE = "/by ".length();
    private static final int LENGTH_DEADLINE = "2021-01-01".length();

    private static final int OFFSET_DESC_EVENT = "event ".length();
    private static final int OFFSET_FROM = "/from ".length();
    private static final int OFFSET_TO = "/to ".length();
    private static final int LENGTH_FROM = "2021-01-01".length();
    private static final int LENGTH_TO = "2021-01-01".length();

    /**
     * Returns a modified version of the user input without the expense.
     * If there is no expense, return the user input as is.
     * @param input
     * @return String for the user input without the expense.
     */
    private static String getInputWithoutExpense(String input) {
        String inputWithoutExpense = input;

        int expenseIndex = input.indexOf("${");

        if (expenseIndex >= 0) {
            inputWithoutExpense = input.substring(0, expenseIndex).trim();
        }

        return inputWithoutExpense;
    }

    /**
     * Reads the user input and creates a corresponding todo object.
     * If the input is invalid, throw an exception.
     * @param input
     * @return Todo object based on the user input
     * @throws JohnException if the input is invalid
     */
    public static Todo createTodo(String input) throws JohnException {
        try {
            String inputWithoutExpense = getInputWithoutExpense(input);

            String desc = inputWithoutExpense.substring(OFFSET_DESC_TODO).trim();
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
     * Get the deadline from the given input.
     * @param input
     * @return LocalDate for the deadline in the input
     */
    private static LocalDate getDeadlineLocalDate(String input, int deadlineIndex) {
        int deadlineStart = deadlineIndex + OFFSET_DEADLINE;
        int deadlineEnd = deadlineStart + LENGTH_DEADLINE;

        LocalDate deadline = LocalDate.parse(
            input.substring(deadlineStart, deadlineEnd));

        return deadline;
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
            String inputWithoutExpense = getInputWithoutExpense(input);

            int deadlineIndex = inputWithoutExpense.indexOf("/by");

            LocalDate deadline = getDeadlineLocalDate(inputWithoutExpense, deadlineIndex);

            String desc = inputWithoutExpense.substring(OFFSET_DESC_DEADLINE, deadlineIndex).trim();
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
     * Get the event start from the given input.
     * @param input
     * @return LocalDate for the 'from' field in the input
     */
    private static LocalDate getFromLocalDate(String input, int fromIndex) {
        int fromStart = fromIndex + OFFSET_FROM;
        int fromEnd = fromIndex + OFFSET_FROM + LENGTH_FROM;

        LocalDate from = LocalDate.parse(
            input.substring(fromStart, fromEnd));

        return from;
    }

    /**
     * Get the event end from the given input.
     * @param input
     * @return LocalDate for the 'to' field in the input
     */
    private static LocalDate getToLocalDate(String input, int fromIndex) {
        int toIndex = input.indexOf("/to", fromIndex + 1);
        int toStart = toIndex + OFFSET_TO;
        int toEnd = toStart + LENGTH_TO;

        LocalDate to = LocalDate.parse(
            input.substring(toStart, toEnd));

        return to;
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
            String inputWithoutExpense = getInputWithoutExpense(input);

            int fromIndex = inputWithoutExpense.indexOf("/from");

            LocalDate from = getFromLocalDate(inputWithoutExpense, fromIndex);

            LocalDate to = getToLocalDate(inputWithoutExpense, fromIndex);

            String desc = inputWithoutExpense.substring(OFFSET_DESC_EVENT, fromIndex).trim();
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
