package by.training.finaltask.exception;

/**
 * Exception is thrown when user input incorrect/invalid data.
 */
public class InvalidFormDataException  extends Exception {

    public InvalidFormDataException(){}

    public InvalidFormDataException(String message) {
        super(message);
    }

    public InvalidFormDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFormDataException(Throwable cause) {
        super(cause);
    }
}
