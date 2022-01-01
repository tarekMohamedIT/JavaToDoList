package Application.Exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("The requested item ID is not found!");
    }

    public EntityNotFoundException(int id) {
        super(String.format("The requested item ID is not found! [%d]", id));
    }
}
