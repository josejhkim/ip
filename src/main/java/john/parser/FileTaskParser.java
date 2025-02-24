package john.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import john.exception.JohnException;
import john.task.Deadline;
import john.task.Event;
import john.task.Task;
import john.task.Todo;

/**
 * Parser class for reading tasks from a file and parsing tasks
 */
public class FileTaskParser {
    private static final int OFFSET_DESC = "[D][X] ".length();
    private static final int IS_DONE_INDEX = 4;

    private static final int OFFSET_DEADLINE = "( by: ".length();
    private static final int LENGTH_DEADLINE = "01 DEC 2021".length();

    private static final int OFFSET_FROM = "( from: ".length();
    private static final int OFFSET_TO = "to: ".length();
    private static final int LENGTH_FROM = "01 DEC 2021".length();
    private static final int LENGTH_TO = "01 DEC 2021".length();

    private static final DateTimeFormatter FORMATTER_DEADLINE =
        DateTimeFormatter.ofPattern("dd MMM yyyy");

    private static final DateTimeFormatter FORMATTER_EVENT =
        DateTimeFormatter.ofPattern("dd MMM yyyy");

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
     * Returns a todo task from a file after reading it.
     * @param input
     * @return Todo object from the given file line input
     * @throws JohnException if the todo can't be read properly
     */
    public static Todo readTodo(String input) throws JohnException {
        try {
            String inputWithoutExpense = getInputWithoutExpense(input);

            String desc = inputWithoutExpense.substring(OFFSET_DESC).trim();

            if (desc.isEmpty()) {
                throw new JohnException("empty task description");
            }

            Todo todo = new Todo(desc);

            boolean isDone = inputWithoutExpense.charAt(IS_DONE_INDEX) == ('X');
            if (isDone) {
                todo.markAsDone();
            }

            todo.setExpenseFromTaskString(input);

            return todo;
        } catch (StringIndexOutOfBoundsException sioobe) {
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

        LocalDate deadline = LocalDate.parse(input.substring(
            deadlineStart,
            deadlineEnd
        ), FORMATTER_DEADLINE);

        return deadline;
    }

    /**
     * Returns a deadline task from a file after reading it.
     * @param input
     * @return Deadline object from the given file line input
     * @throws JohnException if the deadline can't be read properly
     */
    public static Deadline readDeadline(String input) throws JohnException {
        try {
            String inputWithoutExpense = getInputWithoutExpense(input);

            int deadlineIndex = input.indexOf("( by:");

            String desc = inputWithoutExpense.substring(OFFSET_DESC, deadlineIndex).trim();
            if (desc.isEmpty()) {
                throw new JohnException("empty task description");
            }

            LocalDate deadline = getDeadlineLocalDate(inputWithoutExpense, deadlineIndex);

            Deadline dl = new Deadline(desc, deadline);

            boolean isDone = inputWithoutExpense.charAt(IS_DONE_INDEX) == ('X');
            if (isDone) {
                dl.markAsDone();
            }

            dl.setExpenseFromTaskString(input);

            return dl;
        } catch (DateTimeParseException | StringIndexOutOfBoundsException
                invalidFormattingException) {
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
        int fromEnd = fromStart + LENGTH_FROM;

        LocalDate from = LocalDate.parse(input.substring(
            fromStart,
            fromEnd
        ), FORMATTER_EVENT);

        return from;
    }

    /**
     * Get the event end from the given input.
     * @param input
     * @return LocalDate for the 'to' field in the input
     */
    private static LocalDate getToLocalDate(String input, int fromIndex) {
        int toIndex = input.indexOf("to:", fromIndex);
        int toStart = toIndex + OFFSET_TO;
        int toEnd = toStart + LENGTH_TO;

        LocalDate to = LocalDate.parse(input.substring(
            toIndex + OFFSET_TO,
            toEnd
        ), FORMATTER_EVENT);

        return to;
    }

    /**
     * Returns an event task from a file after reading it.
     * @param input
     * @return Event object from the given file line input
     * @throws JohnException if the event can't be read properly
     */
    public static Event readEvent(String input) throws JohnException {
        try {
            String inputWithoutExpense = getInputWithoutExpense(input);

            int fromIndex = input.indexOf("( from: ");

            String desc = inputWithoutExpense.substring(OFFSET_DESC, fromIndex).trim();
            if (desc.isEmpty()) {
                throw new JohnException("empty task description");
            }

            LocalDate from = getFromLocalDate(inputWithoutExpense, fromIndex);
            LocalDate to = getToLocalDate(inputWithoutExpense, fromIndex);

            Event event = new Event(desc, from, to);

            boolean isDone = inputWithoutExpense.charAt(IS_DONE_INDEX) == ('X');
            if (isDone) {
                event.markAsDone();
            }

            event.setExpenseFromTaskString(input);

            return event;
        } catch (DateTimeParseException | StringIndexOutOfBoundsException
            invalidFormattingException) {
            throw new JohnException(Event.EVENT_FORMAT_ERROR);
        }
    }

    /**
     * Returns a task from a file after reading it.
     * The task to be returned can be a todo, deadline, or an event.
     * @param input
     * @return Task object from the given file line input
     * @throws JohnException if the task can't be read properly
     */
    public static Task readTask(String input) throws JohnException {
        if (input.startsWith("[T]")) {
            try {
                return readTodo(input);
            } catch (JohnException je) {
                System.out.println(je.getMessage());
            }
        }

        if (input.startsWith("[D]")) {
            try {
                return readDeadline(input);
            } catch (JohnException je) {
                System.out.println(je.getMessage());
            }
        }

        if (input.startsWith("[E]")) {
            try {
                return readEvent(input);
            } catch (JohnException je) {
                System.out.println(je.getMessage());
            }
        }

        throw new JohnException("Can't parse task from storage file");
    }
}
