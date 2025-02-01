public class Ui {
    public void printHello() {
        linebreak();
        System.out.println("Hello! I'm John, your personalized assistant chatbot");
        System.out.println("How can I help you today?");
    }

    public void printGoodbye() {
        System.out.println("Goodbye and have a nice day!");
        linebreak();
    }

    public void linebreak() {
        System.out.println("------------------------------------------------------------");
    }

    public void printTaskAddition(Task task) {
        System.out.println("added");
        System.out.println(task);
        System.out.println("to your list!");
    }

    public void printTaskDeletion(Task task) {
        System.out.println("removed");
        System.out.println(task);
        System.out.println("from your list!");
    }

    public void printMarkAsDone(String description) {
        System.out.println("marking \"" + description + "\" as done!");
    }

    public void printUnmarkAsDone(String description) {
        System.out.println("marking \"" + description + "\" as not done!");
    }
}
