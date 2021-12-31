package Application.Exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("The requested item ID is not found!");
    }
}
