package john;

import org.junit.jupiter.api.Test;
import john.task.Task;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

public class TaskListTest {
    class TaskStub extends Task {
        public TaskStub(String description) {
            super(description);
        }

        @Override
        public void markAsDone() {
            System.out.println("Marked " + this.description + " as done!");
        }
    }

    @Test
    public void isEmpty_newTaskList_returnsTrue() {
            TaskList tl = new TaskList();
            assertEquals(tl.isEmpty(), true);
    }

    @Test
    public void markAsDone_negativeIndex_exceptionThrown() {
        try {
            TaskList tl = new TaskList();
            Task task = new Task("test task");
            tl.addTask(task);

            int index = -1;

            tl.markAsDoneFromTaskList(index - 1);
        } catch (Exception e) {
            assertEquals("please input a proper index less than or equal to 1", e.getMessage());
        }
    }

    @Test
    public void markAsDone_outOfBounds_exceptionThrown() {
        try {
            TaskList tl = new TaskList();
            Task task = new Task("test task");
            tl.addTask(task);

            int index = 4;
            tl.markAsDoneFromTaskList(index - 1);
        } catch (Exception e) {
            assertEquals("please input a proper index less than or equal to 1", e.getMessage());
        }
    }

    @Test
    public void markAsDone_correctIndex_marksCorrectly() {
            PrintStream standardOut = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));

            TaskList tl = new TaskList();
            Task task = new TaskStub("test task");
            tl.addTask(task);

            int index = 1;
            tl.markAsDoneFromTaskList(index - 1);

            assertEquals("Marked test task as done!", outputStreamCaptor.toString()
                .trim());

            System.setOut(standardOut);
    }

    @Test
    public void getDescription_outOfBounds_exceptionThrown() {
        try {
            TaskList tl = new TaskList();
            Task task = new Task("test task");
            tl.addTask(task);
            tl.getDescription(4);
        } catch (Exception e) {
            assertEquals("please input a proper index less than or equal to 1", e.getMessage());
        }
    }
}
