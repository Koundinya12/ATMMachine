package exceptions;

public class IncorrectAmountException extends RuntimeException{
    public IncorrectAmountException()
    {
        super("Amount not in multiples of 100");
    }
    public IncorrectAmountException(String message)
    {
        super(message);
    }
}
