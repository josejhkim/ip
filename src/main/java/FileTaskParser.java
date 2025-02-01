import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FileTaskParser {

    public static Todo readTodo(String input) throws JohnException {
        boolean isDone = input.charAt(4) == ('X');
        String desc = input.substring(7);
        if (desc.isEmpty()) {
            throw new JohnException("empty task description");
        }
        Todo todo = new Todo(desc);
        if (isDone) {
            todo.markAsDone();
        }
        return todo;
    }

    public static Deadline readDeadline(String input) throws JohnException {
        try {
            int deadlineIndex = input.indexOf("(by:");

            if (deadlineIndex == -1) {
                System.out.println("please enter a proper deadline for this task by formatting it as follows:");
                System.out.println("deadline return book /by 2025-01-30");
                throw new JohnException("invalid deadline formatting");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

            LocalDate deadline = LocalDate.parse(input.substring(deadlineIndex + 5, deadlineIndex + 16), formatter);

            String desc = input.substring(7, deadlineIndex);

            if (desc.isEmpty()) {
                System.out.println("please input a proper task description");
                throw new JohnException("empty task description");
            }

            Deadline dl = new Deadline(desc, deadline);

            boolean isDone = input.charAt(4) == ('X');
            if (isDone) {
                dl.markAsDone();
            }

            return dl;

        } catch (DateTimeParseException | StringIndexOutOfBoundsException invalidFormattingException) {
            System.out.println("please enter a proper deadline for this task by formatting it as follows:");
            System.out.println("deadline return book /by 2025-01-30");
            throw new JohnException("invalid deadline formatting");

        }
    }

    public static Event readEvent(String input) throws JohnException {
        try {
            int fromIndex = input.indexOf("(from:");
            int toIndex = input.indexOf("to:", fromIndex + 1);
            String desc = input.substring(7, fromIndex);
            String from = input.substring(fromIndex + 7, toIndex - 1);
            String to = input.substring(toIndex + 4);

            Event event = new Event(desc, from, to);

            boolean isDone = input.charAt(4) == ('X');
            if (isDone) {
                event.markAsDone();
            }

            return event;

        } catch (StringIndexOutOfBoundsException sioobe) {
            System.out.println("please enter a proper event for this task by formatting it as follows:");
            System.out.println("event wine party /from Sunday 8pm /to Sunday 10pm");
            throw new JohnException("invalid event formatting");
        }
    }

    public static Task readTask(String input) throws JohnException {
        if (input.startsWith("[T]")) {
            try {
                return readTodo(input);
            } catch (JohnException je) {
                System.out.println(je.getMessage());
            }

        } else if (input.startsWith("[D]")) {
            try {
                return readDeadline(input);
            } catch (JohnException je) {
                System.out.println(je.getMessage());
            }

        } else if (input.startsWith("[E]")) {
            try {
                return readEvent(input);
            } catch (JohnException je) {
                System.out.println(je.getMessage());
            }
        } else {
            throw new JohnException("Can't parse task from storage file");
        }

        return new Task("");
    }
}
