package john.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class InputTaskParserTest {

    @Test
    public void createTask_invalidCommand_exceptionThrown() {
        try {
            InputTaskParser.createTask("blergh");
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("Please input a proper task command", e.getMessage());
        }
    }

    @Test
    public void createTask_emptyTodoDescription_exceptionThrown() {
        try {
            InputTaskParser.createTask("todo ");
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("Please input a proper task description.", e.getMessage());
        }
    }

    @Test
    public void createTask_missingDeadline_exceptionThrown() {
        try {
            InputTaskParser.createTask("deadline Sunday");
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("Please enter a proper deadline task by formatting it as follows: \n" +
                "deadline <description> /by <YYYY-MM-DD>\n" +
                "The deadline for the by field should be in a YYYY-MM-DD format.\n" +
                "(i.e. 2025-01-20)", e.getMessage());
        }
    }

    @Test
    public void createTask_invalidDeadline_exceptionThrown() {
        try {
            InputTaskParser.createTask("deadline /by mon-2025");
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("Please enter a proper deadline task by formatting it as follows: \n" +
                "deadline <description> /by <YYYY-MM-DD>\n" +
                "The deadline for the by field should be in a YYYY-MM-DD format.\n" +
                "(i.e. 2025-01-20)", e.getMessage());
        }
    }

    @Test
    public void createTask_missingEventDate_exceptionThrown() {
        try {
            InputTaskParser.createTask("event /from 01-30-2025");
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("Please enter a proper event task by formatting it as follows:\n" +
                "event <description> /from <start> /to <end>\n" +
                "The start and the end for the from and the to field can be in any format.", e.getMessage());
        }
    }

    @Test
    public void createTask_incorrectEventDateOrdering_exceptionThrown() {
        try {
            InputTaskParser.createTask("event /to 01-30-2025 /from 01-29-2025");
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("Please enter a proper event task by formatting it as follows:\n" +
                "event <description> /from <start> /to <end>\n" +
                "The start and the end for the from and the to field can be in any format.", e.getMessage());
        }
    }
}
