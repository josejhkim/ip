import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class InputTaskParser {

    public static Todo createTodo(String input) throws JohnException {
        String desc = input.substring(5);
        if (desc.isEmpty()) {
            throw new JohnException("empty task description");
        }
        return new Todo(desc);
    }

    public static Deadline createDeadline(String input) throws JohnException {
        try {
            int deadlineIndex = input.indexOf("/by");

            if (deadlineIndex == -1) {
                System.out.println("please enter a proper deadline for this task by formatting it as follows:");
                System.out.println("deadline return book /by 2025-01-30");
                throw new JohnException("invalid deadline formatting");
            }

            String desc = input.substring(9, deadlineIndex);

            if (desc.isEmpty()) {
                System.out.println("please input a proper task description");
                throw new JohnException("empty task description");
            }

            LocalDate deadline = LocalDate.parse(input.substring(deadlineIndex + 4));

            return new Deadline(desc, deadline);

        } catch (DateTimeParseException | StringIndexOutOfBoundsException dtpe) {
            System.out.println("please enter a proper deadline for this task by formatting it as follows:");
            System.out.println("deadline return book /by 2025-01-30");
            throw new JohnException("invalid deadline formatting");

        }
    }

    public static Event createEvent(String input) throws JohnException {
        try {
            int fromIndex = input.indexOf("/from");
            int toIndex = input.indexOf("/to", fromIndex + 1);
            String desc = input.substring(6, fromIndex);
            String from = input.substring(fromIndex + 6, toIndex - 1);
            String to = input.substring(toIndex + 4);

            return new Event(desc, from, to);

        } catch (StringIndexOutOfBoundsException sioobe) {
            System.out.println("please enter a proper event for this task by formatting it as follows:");
            System.out.println("event wine party /from Sunday 8pm /to Sunday 10pm");
            throw new JohnException("invalid event formatting");
        }
    }

    public static Task createTask(String input) throws JohnException {
        if (input.startsWith("todo ")) {
            try {
                return createTodo(input);
            } catch (JohnException je) {
                System.out.println(je.getMessage());
            }

        } else if (input.startsWith("deadline ")) {
            try {
                return createDeadline(input);
            } catch (JohnException je) {
                System.out.println(je.getMessage());
            }

        } else if (input.startsWith("event ")) {
            try {
                return createEvent(input);
            } catch (JohnException je) {
                System.out.println(je.getMessage());
            }
        } else {
            throw new JohnException("Can't parse task from storage file");
        }

        return new Task("");
    }
}
