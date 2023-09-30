package Common.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {}

    @Override
    public String toString() {
        return "User with this name does not exist";
    }
}
