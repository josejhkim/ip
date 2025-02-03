package john.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
            assertEquals("empty task description", e.getMessage());
        }
    }

    @Test
    public void createTask_missingDeadline_exceptionThrown() {
        try {
            InputTaskParser.createTask("deadline Sunday");
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("invalid deadline formatting", e.getMessage());
        }
    }

    @Test
    public void createTask_invalidDeadline_exceptionThrown() {
        try {
            InputTaskParser.createTask("deadline /by mon-2025");
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("invalid deadline formatting", e.getMessage());
        }
    }

    @Test
    public void createTask_missingEventDate_exceptionThrown() {
        try {
            InputTaskParser.createTask("event /from 01-30-2025");
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("invalid event formatting", e.getMessage());
        }
    }

    @Test
    public void createTask_incorrectEventDateOrdering_exceptionThrown() {
        try {
            InputTaskParser.createTask("event /to 01-30-2025 /from 01-29-2025");
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("invalid event formatting", e.getMessage());
        }
    }
}
