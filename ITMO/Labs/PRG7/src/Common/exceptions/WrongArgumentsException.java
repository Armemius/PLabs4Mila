package Common.exceptions;

public class WrongArgumentsException extends Exception {
    public WrongArgumentsException() {}

    public WrongArgumentsException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Wrong arguments";
    }
}

