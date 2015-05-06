package by.mogilev.exception;

/**
 * Created by akiseleva on 04.05.2015.
 */
public class NotFoundCourseException extends Exception {
    private String exceptionMessage = "This course does not exist.";

    public String toString() {
        return exceptionMessage;
    }
}
