package john;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import john.exception.JohnException;
import john.parser.FileTaskParser;
import john.task.Task;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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

    public List<Task> getTaskListFromFile() throws FileNotFoundException {
        File f = new File(filePath); // create a File for the given file path
        Scanner s = new Scanner(f); // create a Scanner using the File as the source
        List<Task> taskList = new ArrayList<>();
        while (s.hasNext()) {
            String taskString = s.nextLine();
            try {
                Task t = FileTaskParser.readTask(taskString);
                taskList.add(t);
            } catch (JohnException je) {
                System.out.println("Unable to parse task for " + taskString);
            }
        }
        return taskList;
    }
}
