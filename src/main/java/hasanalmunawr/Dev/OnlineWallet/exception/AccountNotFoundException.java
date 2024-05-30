package hasanalmunawr.Dev.OnlineWallet.exception;

public class AccountNotFoundException extends RuntimeException  {
    public AccountNotFoundException(String message) {
        super(message);
    }
    public AccountNotFoundException() {
        super("Account Not Found");
    }
}
