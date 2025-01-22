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
            if (userInput.equals("list")) {
                if (taskList.isEmpty()) {
                    System.out.println("your list is currently empty!");
                    System.out.println("type in any item to add it to your list!");
                } else {
                    System.out.println("your list currently contains");
                    int index = 1;
                    taskList.forEach(str -> System.out.println(
                            index + ". " + str
                    ));
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
