package hasanalmunawr.Dev.OnlineWallet.exception;

public class EntityNotFound extends RuntimeException {

    public EntityNotFound(String message) {
        super(message);
    }

    public EntityNotFound() {
        super("Entity not found");
    }
}
