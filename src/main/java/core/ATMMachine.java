package core;

import dto.Denomination;
import exceptions.IncorrectAmountException;
import exceptions.InsufficientBalanceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

//This class is responsible for initialising denominations and to perform withdrawals
public class ATMMachine{

    public TreeMap<Integer,Integer> denominations;
    public static final Logger logger = LogManager.getLogger(ATMMachine.class);
    int balance;

    public ATMMachine(){
        this.balance=0;
        denominations=new TreeMap<>(Collections.reverseOrder());
        //Assumed denominations are 500,200,100
        denominations.put(500,12);
        denominations.put(200,6);
        denominations.put(100,9);
        getBalance();
    }
    //Method to check Balance available in the ATM Machine
    public int getBalance()
    {
        this.balance=denominations.entrySet().stream().mapToInt(den->den.getKey()*den.getValue()).sum();
        return this.balance;
    }
    public void validateWithdrawal(int amount){
        if (!(amount % 100 == 0)) {
            logger.error("Amount not supported for withdrawal,");
            throw new IncorrectAmountException("Incorrect amount Exception, please enter amount in multiples of 100");
        }
        if (amount > balance) {
            logger.error("Insufficient balance");
            throw new InsufficientBalanceException("Insufficient balance, please try again");
        }
    }
    //Method to withdraw amount from ATM Machine
    public synchronized List<Denomination> withdraw(int amount) throws IncorrectAmountException, InsufficientBalanceException {
        validateWithdrawal(amount);
        List<Denomination> denominationList=new ArrayList<>();
        int remainingBalance = amount;
        Iterator<Integer> integerIterator = this.denominations.keySet().iterator();
        while (integerIterator.hasNext()) {
            int denomination = integerIterator.next();
            while (remainingBalance < denomination && integerIterator.hasNext()) {
                denomination = integerIterator.next();
            }
            if (remainingBalance < denomination) {
                continue;
            }
            int notes = Math.min(remainingBalance / denomination, denominations.get(denomination));
            if (notes > 0) {
                remainingBalance -= notes * denomination;
                denominationList.add(new Denomination(denomination,notes));
                int remNotes = denominations.get(denomination) - notes;
                if (remNotes == 0) {
                    integerIterator.remove();
                } else {
                    this.denominations.put(denomination, remNotes);
                }
                if (remainingBalance == 0) break;
            }
        }
        return denominationList;
    }
}


