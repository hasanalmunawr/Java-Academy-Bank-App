package hasanalmunawr.Dev.OnlineWallet.exception;

public class OperationNotPermittedException extends RuntimeException {


    public OperationNotPermittedException() {
        super("Operation not permitted");
    }

    public OperationNotPermittedException(String message) {
        super(message);
    }


}
