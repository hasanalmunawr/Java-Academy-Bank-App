package hasanalmunawr.Dev.OnlineWallet.exception;

public class ApiException extends RuntimeException{

    public ApiException() {
        super("An error occurred while processing request");
    }
    public ApiException(String message) {
        super(message);
    }
}
