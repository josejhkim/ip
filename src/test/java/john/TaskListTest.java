package john;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.Test;

import john.exception.JohnException;
import john.task.Task;

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
    public void markAsDone_outOfBounds_exceptionThrown() {
        try {
            TaskList tl = new TaskList();
            Task task = new Task("test task");
            Task task2 = new Task("test task 2");

            tl.addTask(task);
            tl.addTask(task2);

            int index = 4;
            tl.markAsDoneFromTaskList(index - 1);
        } catch (JohnException je) {
            assertEquals("Please input a valid numerical index between 1 and 2", je.getMessage());
        }
    }

    @Test
    public void markAsDone_correctIndex_marksCorrectly() {
        PrintStream standardOut = System.out;
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        try {
            TaskList tl = new TaskList();
            Task task = new Task("test task");
            Task task2 = new Task("test task 2");

            tl.addTask(task);
            tl.addTask(task2);
            int index = 1;
            tl.markAsDoneFromTaskList(index - 1);

            assertEquals(task.getIsDone(), true);
        } catch (JohnException je) {
            // Shouldn't happen
            fail();
        }

        System.setOut(standardOut);
    }

    @Test
    public void getDescription_outOfBounds_exceptionThrown() {
        try {
            TaskList tl = new TaskList();
            Task task = new Task("test task");
            tl.addTask(task);
            tl.getDescription(4);
        } catch (JohnException je) {
            assertEquals("Please input a valid numerical index between 1 and 1", je.getMessage());
        }
    }

    @Test
    public void getDescription_correctIndex_returnsDescription() {
        try {
            TaskList tl = new TaskList();
            Task task = new Task("test task");
            tl.addTask(task);

            String desc = tl.getDescription(0);
            assertEquals(desc, "test task");
        } catch (JohnException je) {
            fail();
        }
    }

    @Test
    public void getFilteredTaskList_emptyList_returnsEmptyList() {
        TaskList tl = new TaskList();
        List<Task> taskList = tl.getFilteredTaskList("task");
        assertEquals(taskList.size(), 0);
    }

    @Test
    public void getFilteredTaskList_noSearchTermMatch_returnsEmptyList() {
        TaskList tl = new TaskList();

        Task task = new Task("test task apple");
        Task task2 = new Task("test task banana");
        Task task3 = new Task("test task orange");
        tl.addTask(task);
        tl.addTask(task2);
        tl.addTask(task3);

        List<Task> taskList1 = tl.getFilteredTaskList("tomato");
        assertEquals(taskList1.size(), 0);
    }

    @Test
    public void getFilteredTaskList_searchTermMatch_returnsCorrectlyFilteredList() {
        TaskList tl = new TaskList();

        Task task = new Task("test task");
        Task task2 = new Task("test 2");
        Task task3 = new Task("test task 3");
        tl.addTask(task);
        tl.addTask(task2);
        tl.addTask(task3);

        List<Task> taskList1 = tl.getFilteredTaskList("task");
        assertEquals(taskList1.size(), 2);
        assertEquals(taskList1.get(0).getDescription(), "test task");
        assertEquals(taskList1.get(1).getDescription(), "test task 3");
    }
}
