package africa.semicolon.lumexpress.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super((message));
    }
}
