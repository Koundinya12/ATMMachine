import core.ATMMachine;
import dto.Denomination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
/**
 * This is the driver class for the program
 */
public class Main {

    public static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws RuntimeException {
        logger.info("Application started");
        //This Object creation initializes our TreeMap with pre-defined denominations
        ATMMachine atmMachine=new ATMMachine();
        int amount=3200;
        List<Denomination> denominationList=atmMachine.withdraw(amount);
        logger.info("Denominations used to withdraw: {} {} ",amount,denominationList);
        logger.info("Withdrawal Completed");
        logger.info("Available balance after withdrawal: {} ",atmMachine.getBalance());
        logger.info("Application ended");
    }
}