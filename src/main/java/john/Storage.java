package john;

import john.exception.JohnException;
import john.parser.FileTaskParser;
import john.task.Task;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;

public class Storage {
    private String filePath;

    /**
     * Initialize a Storage object
     * with the filepath to read and write tasks.
     * @param filePath
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Write the given List<Task> to the filepath.
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
     * Read and create a new TaskList from the file at the specified location.
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
        return taskList;
    }
}
