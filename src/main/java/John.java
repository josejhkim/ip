import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class John {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm John, your personalized assistant chatbot");
        System.out.println("How can I help you today?");
        System.out.println("____________________________________________________________");

        Scanner myObj = new Scanner(System.in);
        String userInput = myObj.nextLine();
        List<Task> taskList = new ArrayList<>();

        while (!userInput.equals("bye")) {
            System.out.println("____________________________________________________________");
            if (userInput.startsWith("mark ")) {
                int index = Integer.parseInt(userInput.substring(5)) - 1;
                if (index >= taskList.size()) {
                    System.out.println("index " + (index + 1) + " is out of bounds for your list of size " + taskList.size());
                } else {
                    System.out.println("marking \"" + taskList.get(index).getDescription() + "\" as done!");
                    taskList.get(index).markAsDone();
                }
            } else if (userInput.startsWith("unmark ")){
                int index = Integer.parseInt(userInput.substring(7)) - 1;
                if (index >= taskList.size()) {
                    System.out.println("index " + (index + 1) + " is out of bounds for your list of size " + taskList.size());
                } else {
                    System.out.println("marking \"" + taskList.get(index).getDescription() + "\" as not done!");
                    taskList.get(index).unmarkAsDone();
                }
            } else if (userInput.equals("list")) {
                if (taskList.isEmpty()) {
                    System.out.println("your list is currently empty!");
                    System.out.println("type in any item to add it to your list!");
                } else {
                    System.out.println("your list currently contains");
                    int index = 1;
                    for (Task task : taskList) {
                        System.out.println(
                                index++ + ". " + task.toString()
                        );
                    }
                }
            } else {
                System.out.println("Adding \"" + userInput + "\" to your list!");
                taskList.add(new Task(userInput));
            }
            System.out.println("____________________________________________________________");
            userInput = myObj.nextLine();
        }

        System.out.println("____________________________________________________________");
        System.out.println("Goodbye and have a nice day!");
    }
}
