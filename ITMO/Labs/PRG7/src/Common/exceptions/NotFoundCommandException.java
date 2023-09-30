package Common.exceptions;

public class NotFoundCommandException extends Exception {
    public NotFoundCommandException() {}

    @Override
    public String toString() {
        return "The command doesn't exist";
    }
}
