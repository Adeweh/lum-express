package africa.semicolon.lumexpress.exceptions;


public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String message) {
        super(message);
    }
}
