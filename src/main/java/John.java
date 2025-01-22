import java.util.Scanner;

public class John {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm John, your personalized assistant chatbot");
        System.out.println("How can I help you today?");
        System.out.println("____________________________________________________________");

        Scanner myObj = new Scanner(System.in);
        String userInput = myObj.nextLine();
        String[] stringList = new String[100];
        
        while (!userInput.equals("bye")) {
            System.out.println("____________________________________________________________");
            System.out.println("You said: " + userInput);
            System.out.println("____________________________________________________________");
            userInput = myObj.nextLine();
        }

        System.out.println("____________________________________________________________");
        System.out.println("Goodbye and have a nice day!");
    }
}
