import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class John {
    public static void main(String[] args) {
        System.out.println("------------------------------------------------------------");
        System.out.println("Hello! I'm John, your personalized assistant chatbot");
        System.out.println("How can I help you today?");

        Scanner myObj = new Scanner(System.in);
        List<Task> taskList = new ArrayList<>();

        while (true) {
            System.out.println("------------------------------------------------------------");
            String userInput = myObj.nextLine();
            System.out.println("------------------------------------------------------------");
            if (userInput.equals("bye")) {
                break;
            } else if (userInput.startsWith("mark ")) {
                try {
                    int index = Integer.parseInt(userInput.substring(5)) - 1;
                    System.out.println("marking \"" + taskList.get(index).getDescription() + "\" as done!");
                    taskList.get(index).markAsDone();
                } catch (IndexOutOfBoundsException ioobe) {
                    System.out.println("please input a proper index less than or equal to " + taskList.size());
                } catch (NumberFormatException nfe) {
                    System.out.println("please input a proper index in a numerical format");
                }
            } else if (userInput.startsWith("unmark ")){
                try {
                    int index = Integer.parseInt(userInput.substring(7)) - 1;
                    System.out.println("marking \"" + taskList.get(index).getDescription() + "\" as not done!");
                    taskList.get(index).unmarkAsDone();
                } catch (IndexOutOfBoundsException ioobe) {
                    System.out.println("please input a proper index less than or equal to " + taskList.size());
                } catch (NumberFormatException nfe) {
                    System.out.println("please input a proper index in a numerical format");
                }
            } else if (userInput.startsWith("todo ")) {
                String desc = userInput.substring(5);
                if (desc.isEmpty()) {
                    System.out.println("please input a proper task description");
                    continue;
                }
                Todo task = new Todo(desc);
                taskList.add(task);
                System.out.println("added");
                System.out.println(task);
                System.out.println("to your list!");

            } else if (userInput.startsWith("deadline ")) {
                try {
                    int deadlineIndex = userInput.indexOf("/");
                    if (deadlineIndex == -1) {
                        System.out.println("please enter a proper deadline for this task by formatting it as follows:");
                        System.out.println("deadline return book /by Sunday");
                        continue;
                    }
                    String desc = userInput.substring(9, deadlineIndex);
                    if (desc.isEmpty()) {
                        System.out.println("please input a proper task description");
                        continue;
                    }
                    String deadline = userInput.substring(deadlineIndex + 4);

                    Deadline task = new Deadline(desc, deadline);
                    taskList.add(task);
                    System.out.println("added");
                    System.out.println(task);
                    System.out.println("to your list!");
                } catch (StringIndexOutOfBoundsException sioobe) {
                    System.out.println("please enter a proper deadline for this task by formatting it as follows:");
                    System.out.println("deadline return book /by Sunday");
                }

            } else if (userInput.startsWith("event ")) {
                try {
                    int fromIndex = userInput.indexOf("/");
                    int toIndex = userInput.indexOf("/", fromIndex + 1);
                    String desc = userInput.substring(6, fromIndex);
                    String from = userInput.substring(fromIndex + 6, toIndex - 1);
                    String to = userInput.substring(toIndex + 4);

                    Event task = new Event(desc, from, to);
                    taskList.add(task);
                    System.out.println("added");
                    System.out.println(task);
                    System.out.println("to your list!");
                } catch (StringIndexOutOfBoundsException sioobe) {
                    System.out.println("please enter a proper deadline for this task by formatting it as follows:");
                    System.out.println("deadline return book /by Sunday");
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
                try {
                    throw new JohnCommandException();
                } catch (JohnCommandException e) {
                    System.out.println("please input a proper command!");
                }
            }
        }

        System.out.println("Goodbye and have a nice day!");
        System.out.println("------------------------------------------------------------");
    }
}
