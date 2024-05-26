package application;

import DTO.Denomination;
import models.ATMMachine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.TreeMap;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws RuntimeException {
        logger.info("Application started");
        ATMMachine atmMachine=new ATMMachine();
        System.out.println("Initial balance in ATM IS "+atmMachine.getBalance());
        int amount=6000;
        List<Denomination> denominationList=atmMachine.withdraw(amount);
        logger.info("Denominations used to withdraw "+amount+" :"+denominationList);
        logger.info("Withdrawal Completed");
        System.out.println("Balance in ATM Machine after Withdrawal of "+amount+" is "+atmMachine.getBalance());
        logger.info("Application ended");
    }
}