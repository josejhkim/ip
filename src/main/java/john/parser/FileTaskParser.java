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
    private static final int START_DESC = "[D][X]".length() + 1;
    private static final int IS_DONE_INDEX = 4;

    private static final int START_DEADLINE = "(by: ".length() + 1;
    private static final int LENGTH_DEADLINE = "01 DEC 2021".length();

    private static final int START_FROM = "(from: ".length() + 1;
    private static final int START_TO = "to: ".length() + 1;

    /**
     * Reads a todo task from the file storing the task list
     * and returns it.
     * @param input
     * @return Todo object from the given file line input
     * @throws JohnException
     */
    public static Todo readTodo(String input) throws JohnException {
        try {
            String desc = input.substring(START_DESC);
            if (desc.isEmpty()) {
                throw new JohnException("empty task description");
            }

            Todo todo = new Todo(desc);

            boolean isDone = input.charAt(IS_DONE_INDEX) == ('X');
            if (isDone) {
                todo.markAsDone();
            }

            todo.setExpenseFromTaskString(input);

            return todo;
        } catch (StringIndexOutOfBoundsException sioobe) {
            throw new JohnException(Task.INVALID_FORMAT_ERROR);
        }
    }

    /**
     * Reads a deadline task from the file storing the task list
     * and returns it.
     * @param input
     * @return Deadline object from the given file line input
     * @throws JohnException
     */
    public static Deadline readDeadline(String input) throws JohnException {
        try {
            int deadlineIndex = input.indexOf("(by:");
            LocalDate deadline = LocalDate.parse(input.substring(
                    deadlineIndex + START_DEADLINE,
                    deadlineIndex + START_DEADLINE + LENGTH_DEADLINE + 1
                    ), DateTimeFormatter.ofPattern("dd MMM yyyy"));

            String desc = input.substring(START_DESC, deadlineIndex);
            if (desc.isEmpty()) {
                throw new JohnException("empty task description");
            }

            Deadline dl = new Deadline(desc, deadline);

            boolean isDone = input.charAt(IS_DONE_INDEX) == ('X');
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
     * Reads an event task from the file storing the task list
     * and returns it.
     * @param input
     * @return Event object from the given file line input
     * @throws JohnException
     */
    public static Event readEvent(String input) throws JohnException {
        try {
            int fromIndex = input.indexOf("(from:");
            int toIndex = input.indexOf("to:", fromIndex + 1);

            String from = input.substring(fromIndex + START_FROM, toIndex - 1);
            String to = input.substring(toIndex + START_TO);

            String desc = input.substring(START_DESC, fromIndex);

            Event event = new Event(desc, from, to);

            boolean isDone = input.charAt(IS_DONE_INDEX) == ('X');
            if (isDone) {
                event.markAsDone();
            }

            event.setExpenseFromTaskString(input);

            return event;
        } catch (StringIndexOutOfBoundsException sioobe) {
            throw new JohnException(Event.EVENT_FORMAT_ERROR);
        }
    }

    /**
     * Reads a task from the file storing the task list
     * and returns the corresponding subclass object
     * @param input
     * @return Task object from the given file line input
     * @throws JohnException
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
