package hasanalmunawr.Dev.JavaAcademyBankApp.exception;

public class EntityNotFound extends RuntimeException {

    public EntityNotFound(String message) {
        super(message);
    }

    public EntityNotFound() {
        super("Entity not found");
    }
}
