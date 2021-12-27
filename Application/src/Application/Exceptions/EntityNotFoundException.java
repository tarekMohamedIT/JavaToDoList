package Application.Exceptions;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException() {
        super("The requested item ID is not found!");
    }
}
