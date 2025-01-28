import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class John {

    private List<Task> taskList = new ArrayList<>();

    public static void main(String[] args) {
        John john = new John();

        System.out.println("------------------------------------------------------------");
        System.out.println("Hello! I'm John, your personalized assistant chatbot");
        System.out.println("How can I help you today?");

        Scanner myObj = new Scanner(System.in);

        while (true) {
            System.out.println("------------------------------------------------------------");
            String userInput = myObj.nextLine();
            System.out.println("------------------------------------------------------------");
            if (userInput.equals("bye")) {
                break;

            } else if (userInput.startsWith("mark ")) {
                int index = Integer.parseInt(userInput.substring(5)) - 1;
                john.markAsDoneFromTaskList(index);

            } else if (userInput.startsWith("unmark ")) {
                int index = Integer.parseInt(userInput.substring(7)) - 1;
                john.unmarkAsDoneFromTaskList(index);

            } else if (userInput.startsWith("delete ")) {
                int index = Integer.parseInt(userInput.substring(7)) - 1;
                john.deleteFromTaskList(index);

            } else if (userInput.startsWith("todo ")) {
                try {
                    Todo todo = createTodo(userInput);
                    john.taskList.add(todo);
                    printTaskAddition(todo);
                } catch (JohnException Je) {
                    System.out.println(Je.getMessage());
                }
            } else if (userInput.startsWith("deadline ")) {
                try {
                    Deadline deadline = createDeadline(userInput);
                    john.taskList.add(deadline);
                    printTaskAddition(deadline);
                } catch (JohnException Je) {
                    System.out.println(Je.getMessage());
                }
            } else if (userInput.startsWith("event ")) {
                try {
                    Event event = createEvent(userInput);
                    john.taskList.add(event);
                    printTaskAddition(event);
                } catch (JohnException Je) {
                    System.out.println(Je.getMessage());
                }
            } else if (userInput.equals("list")) {
                if (john.taskList.isEmpty()) {
                    System.out.println("your list is currently empty!");
                    System.out.println("type in any item to add it to your list!");
                } else {
                    System.out.println("your list currently contains");
                    john.printTaskList(john.taskList);
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

    public static void printTaskAddition(Task task) {
        System.out.println("added");
        System.out.println(task);
        System.out.println("to your list!");
    }

    public void markAsDoneFromTaskList(int index) {
        try {
            System.out.println("marking \"" + this.taskList.get(index).getDescription() + "\" as done!");
            this.taskList.get(index).markAsDone();

        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("please input a proper index less than or equal to " + this.taskList.size());

        } catch (NumberFormatException nfe) {
            System.out.println("please input a proper index in a numerical format");
        }
    }

    public void unmarkAsDoneFromTaskList(int index) {
        try {
            System.out.println("marking \"" + taskList.get(index).getDescription() + "\" as not done!");
            taskList.get(index).unmarkAsDone();

        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("please input a proper index less than or equal to " + taskList.size());

        } catch (NumberFormatException nfe) {
            System.out.println("please input a proper index in a numerical format");
        }
    }

    public void deleteFromTaskList(int index) {
        try {
            this.taskList.remove(index);
        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("please input a proper index less than or equal to " + this.taskList.size());
        } catch (NumberFormatException nfe) {
            System.out.println("please input a proper index in a numerical format");
        }
    }

    public void printTaskList(List<Task> taskList) {
        int index = 1;
        for (Task task : taskList) {
            System.out.println(
                    index++ + ". " + task.toString()
            );
        }
    }

    public static Todo createTodo(String input) throws JohnException {
        String desc = input.substring(5);
        if (desc.isEmpty()) {
            throw new JohnException("empty task description");
        }
        return new Todo(desc);
    }

    public static Deadline createDeadline(String input) throws JohnException {
        try {
            int deadlineIndex = input.indexOf("/");

            if (deadlineIndex == -1) {
                System.out.println("please enter a proper deadline for this task by formatting it as follows:");
                System.out.println("deadline return book /by Sunday");
                throw new JohnException("invalid deadline formatting");
            }

            String desc = input.substring(9, deadlineIndex);

            if (desc.isEmpty()) {
                System.out.println("please input a proper task description");
                throw new JohnException("empty task description");
            }

            String deadline = input.substring(deadlineIndex + 4);

            return new Deadline(desc, deadline);

        } catch (StringIndexOutOfBoundsException sioobe) {
            System.out.println("please enter a proper deadline for this task by formatting it as follows:");
            System.out.println("deadline return book /by Sunday");
            throw new JohnException("invalid deadline formatting");
        }
    }

    public static Event createEvent(String input) throws JohnException {
        try {
            int fromIndex = input.indexOf("/from");
            int toIndex = input.indexOf("/to", fromIndex + 1);
            String desc = input.substring(6, fromIndex);
            String from = input.substring(fromIndex + 6, toIndex - 1);
            String to = input.substring(toIndex + 4);

            return new Event(desc, from, to);

        } catch (StringIndexOutOfBoundsException sioobe) {
            System.out.println("please enter a proper event for this task by formatting it as follows:");
            System.out.println("event wine party /from Sunday 8pm /to Sunday 10pm");
            throw new JohnException("invalid event formatting");
        }
    }
}
