package hasanalmunawr.Dev.JavaAcademyBankApp.exception;

public class ActivationTokenException extends RuntimeException {

    public ActivationTokenException() {
        super("Token Invalid");
    }

    public ActivationTokenException(String message) {
        super(message);
    }
}
