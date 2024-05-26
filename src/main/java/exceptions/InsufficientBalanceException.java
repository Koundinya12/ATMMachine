package exceptions;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException()
    {
        super("Insufficient balance exception");
    }
    public InsufficientBalanceException(String message)
    {
        super(message);
    }
}
