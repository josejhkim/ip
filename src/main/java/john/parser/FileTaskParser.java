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
    private static final int START_DESC = "[D][X] ".length();
    private static final int IS_DONE_INDEX = 4;

    private static final int START_DEADLINE = "( by: ".length();
    private static final int LENGTH_DEADLINE = "01 DEC 2021".length();

    private static final int START_FROM = "( from: ".length();
    private static final int START_TO = "to: ".length();
    private static final int LENGTH_FROM = "01 DEC 2021".length();
    private static final int LENGTH_TO = "01 DEC 2021".length();


    private static final DateTimeFormatter FORMATTER_DEADLINE =
        DateTimeFormatter.ofPattern("dd MMM yyyy");

    private static final DateTimeFormatter FORMATTER_EVENT =
        DateTimeFormatter.ofPattern("dd MMM yyyy");

    /**
     * Returns a todo task from a file after reading it.
     * @param input
     * @return Todo object from the given file line input
     * @throws JohnException if the todo can't be read properly
     */
    public static Todo readTodo(String input) throws JohnException {
        try {
            String inputWithoutExpense = input;

            int expenseIndex = input.indexOf("${");
            if (expenseIndex >= 0) {
                inputWithoutExpense = input.substring(0, expenseIndex).trim();
            }

            String desc = inputWithoutExpense.substring(START_DESC).trim();

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
     * Returns a deadline task from a file after reading it.
     * @param input
     * @return Deadline object from the given file line input
     * @throws JohnException if the deadline can't be read properly
     */
    public static Deadline readDeadline(String input) throws JohnException {
        try {
            String inputWithoutExpense = input;

            int expenseIndex = input.indexOf("${");
            if (expenseIndex >= 0) {
                inputWithoutExpense = input.substring(0, expenseIndex).trim();
            }

            int deadlineIndex = inputWithoutExpense.indexOf("( by:");
            LocalDate deadline = LocalDate.parse(inputWithoutExpense.substring(
                    deadlineIndex + START_DEADLINE,
                    deadlineIndex + START_DEADLINE + LENGTH_DEADLINE
                    ), FORMATTER_DEADLINE);

            String desc = inputWithoutExpense.substring(START_DESC, deadlineIndex).trim();
            if (desc.isEmpty()) {
                throw new JohnException("empty task description");
            }

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
     * Returns an event task from a file after reading it.
     * @param input
     * @return Event object from the given file line input
     * @throws JohnException if the event can't be read properly
     */
    public static Event readEvent(String input) throws JohnException {
        try {
            String inputWithoutExpense = input;

            int expenseIndex = input.indexOf("${");
            if (expenseIndex >= 0) {
                inputWithoutExpense = input.substring(0, expenseIndex).trim();
            }

            int fromIndex = inputWithoutExpense.indexOf("( from: ");
            int toIndex = inputWithoutExpense.indexOf("to:", fromIndex + 1);
            int toEnd = inputWithoutExpense.indexOf(" )", toIndex + 1);

            LocalDate from = LocalDate.parse(inputWithoutExpense.substring(
                fromIndex + START_FROM,
                fromIndex + START_FROM + LENGTH_FROM
            ), FORMATTER_EVENT);

            LocalDate to = LocalDate.parse(inputWithoutExpense.substring(
                toIndex + START_TO,
                toEnd
            ), FORMATTER_EVENT);

            String desc = inputWithoutExpense.substring(START_DESC, fromIndex).trim();
            if (desc.isEmpty()) {
                throw new JohnException("empty task description");
            }

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
