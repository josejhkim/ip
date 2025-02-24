package john;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import john.exception.JohnException;
import john.parser.FileTaskParser;
import john.task.Task;

/**
 * Storage class for writing and reading user's task list to and from a file
 */
public class Storage {
    private String filePath;

    /**
     * Initializes a Storage object with the filepath to read and write tasks.
     * @param filePath
     */
    public Storage(String filePath) {
        assert filePath.length() > 0 : "filePath isn't empty";

        this.filePath = filePath;

        File f = new File(filePath);

        if (!f.exists()) {
            try {
                // Attempt to create the file
                if (f.createNewFile()) {
                    System.out.println("File not found. A new file has been created at: " + f.getAbsolutePath());
                } else {
                    System.out.println("File not found and could not be created.");
                }
            } catch (IOException e) {
                System.err.println("An error occurred while creating the file.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Writes the given List of Tasks to the filepath.
     * @param taskList
     */
    public void writeTaskListToFile(List<Task> taskList) {
        try {
            FileWriter fw = new FileWriter(filePath);

            for (Task task: taskList) {
                fw.write(task.toString() + System.lineSeparator());
            }

            fw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reads and creates a new TaskList from the file at the specified location.
     * @return TaskList containing the tasks from the specified file
     * @throws FileNotFoundException
     */
    public TaskList getTaskListFromFile() throws FileNotFoundException {
        File f = new File(filePath);
        Scanner s = new Scanner(f);

        TaskList taskList = new TaskList();

        while (s.hasNext()) {
            String taskString = s.nextLine();

            try {
                Task t = FileTaskParser.readTask(taskString);
                taskList.addTask(t);
            } catch (JohnException je) {
                System.out.println("Unable to parse task for " + taskString);
            }
        }
        s.close();

        return taskList;
    }
}
