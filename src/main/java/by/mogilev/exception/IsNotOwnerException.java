package by.mogilev.exception;

/**
 * Created by akiseleva on 05.05.2015.
 */
public class IsNotOwnerException extends Exception {
    private String exceptionMessage = "You are not a creator of the course, so you can not change it.";

    public String toString() {
        return exceptionMessage;
    }
}
