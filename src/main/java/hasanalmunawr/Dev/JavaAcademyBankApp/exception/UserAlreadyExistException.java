package hasanalmunawr.Dev.JavaAcademyBankApp.exception;


public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message) {
        super(message);
    }
    public UserAlreadyExistException(){
        super("User Already Exist By Another User");
    }
}
