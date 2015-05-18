package by.mogilev.exception;

/**
 * Created by akiseleva on 04.05.2015.
 */
public class NotFoundCourseException extends TrainingCenterException {
    private String exceptionMessage = "This course doesn't exist.";

    public String toString() {
        return exceptionMessage;
    }
}
