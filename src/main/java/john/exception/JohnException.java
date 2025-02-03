package john.exception;

/**
 * Exception class for exceptions relating to the chatbot John
 */
public class JohnException extends Exception {
    public JohnException() {
        super();
    }

    public JohnException(String message) {
        super(message);
    }
}
