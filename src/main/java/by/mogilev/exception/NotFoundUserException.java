package by.mogilev.exception;

/**
 * Created by akiseleva on 04.05.2015.
 */
 public class NotFoundUserException extends Exception {
    private String exceptionMessage;
    public NotFoundUserException() {
        exceptionMessage = "User denied. Repeat sing in.";
    }
    public String toString() {
        return exceptionMessage;
    }


}