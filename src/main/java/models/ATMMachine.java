package models;

import DTO.Denomination;
import exceptions.IncorrectAmountException;
import exceptions.InsufficientBalanceException;

import java.util.*;

import static application.Main.logger;

public class ATMMachine{
    //Use of TreeMap is to have denominations in decreasing order so as to dispense minimum notes
    public TreeMap<Integer,Integer> denominations;
    int balance;

    //Constructor to Initialize our TreeMap with pre-defined denominations
    public ATMMachine(){
        this.balance=0;
        denominations=new TreeMap<>(Collections.reverseOrder());
        //Assumed denominations are 500,200,100
        denominations.put(500,12);
        denominations.put(200,6);
        denominations.put(100,9);
        getBalance();
    }
    //Helper function to check number of notes we are left with before and after transaction
    public void printDenominations()
    {
        for(Map.Entry<Integer,Integer> entrySet:denominations.entrySet()){
            System.out.println(entrySet.getKey()+" "+entrySet.getValue());
        }
    }

    //Method to check Balance available in the ATM Machine
    public int getBalance()
    {
        int availableBalance=0;
        availableBalance= denominations.entrySet().stream().mapToInt(x->x.getKey()*x.getValue()).sum();
        this.balance=availableBalance;
        return balance;
    }
    public void isWithdrawalPossible(int amount){
        if (!(amount % 100 == 0)) {
            logger.error("Amount not supported for withdrawal,");
            throw new IncorrectAmountException("Incorrect amount Exception, please enter amount in multiples of 100");
        }
        //Throw error if withdrawal amount is greater than available balance
        if (amount > balance) {
            logger.error("Insufficient balance");
            throw new InsufficientBalanceException("Insufficient balance, please try again");
        }
    }
    //Method to withdraw amount from ATM Machine
    public synchronized List<Denomination> withdraw(int amount) throws IncorrectAmountException, InsufficientBalanceException {
        //Throw error if withdrawal amount is not in multiples of 100
        isWithdrawalPossible(amount);
        List<Denomination> denominationList=new ArrayList<>();
        int remainingBalance = amount;
        //Iterating the denominations and updating the denominations
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
        this.balance -= amount;
        return denominationList;
    }
}


