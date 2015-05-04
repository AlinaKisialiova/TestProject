package by.mogilev.exception;

/**
 * Created by akiseleva on 04.05.2015.
 */
 public class NullUserException extends Exception {
    private String exceptionMessage;
    public NullUserException() {
        exceptionMessage = "User denied. Repeat sing in.";
    }


}