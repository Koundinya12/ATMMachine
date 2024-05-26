import DTO.Denomination;
import junit.framework.TestCase;
import models.ATMMachine;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.*;

import static org.junit.Assert.*;

public class ATMMachineTest extends TestCase {
    private ATMMachine atmMachine;


    @Before
    public void setUp (){
        atmMachine=new ATMMachine();
    }
    //Test case to perform validation against the intial balance
    @Test
    public void testIntitalBalance(){
        assertEquals(8100,atmMachine.getBalance());
    }
    //Test case to perform validation against successfull withdrawal
    @Test
    public void testSuccessWithdrawal(){
        List<Denomination> denominationList=atmMachine.withdraw(1100);
        assertEquals(7000,atmMachine.getBalance());
        assertTrue("Denominations are incorrect",denominationList.contains(new Denomination(500,2)));
        assertTrue("Denominations are incorrect",denominationList.contains(new Denomination(100,1)));
    }
    //Test case to perform validation against Insufficient balance
    @Test
    public void testInsufficientBalance(){
        Exception exception=assertThrows(RuntimeException.class,()->{
            atmMachine.withdraw(9000);
        });
        assertEquals("Insufficient balance, please try again",exception.getMessage());
    }
    //Test case to perform validation against concurrent withdrawal by 2 customers
    @Test
    public void test_ConcurrentWithdrawal() throws InterruptedException, ExecutionException {
        int[] withdrawals={1600,2800,3700};
        List<Denomination> denominationList=new ArrayList<>();
        ExecutorService executor= Executors.newFixedThreadPool(withdrawals.length);
        for(int x:withdrawals)
        {
            Callable<List<Denomination>> callable=()->atmMachine.withdraw(x);
            Future<List<Denomination>> future=executor.submit(callable);
            denominationList=future.get();
            //Testing denomination for withdrawal of 1600
            if(x==1600)
            {
                assertTrue("Denominations are incorrect",denominationList.contains(new Denomination(500,3)));
                assertTrue("Denominations are incorrect",denominationList.contains(new Denomination(100,1)));
            }
        }
        executor.shutdown();
        boolean flag=executor.awaitTermination(1, TimeUnit.MINUTES);
        assertTrue("Executor should terminate within the given time", flag);
        //assertTrue("Denominations are incorrect",denominationList.contains(new Denomination(500,3)));
        assertEquals( "Balance should be 0 after concurrent withdrawals",0,atmMachine.getBalance());
    }
    //Test case to perform validation against concurrent withdrawal by 2 customers with exceeding Balance
    @Test
    public void testConcurrentWithdrawalExceedingBalance() throws InterruptedException{
        ExecutorService executor= Executors.newFixedThreadPool(2);
        Runnable r1=()->atmMachine.withdraw(8000);
        Runnable r2=()->atmMachine.withdraw(200);
        executor.submit(r1);
        executor.submit(r2);
        executor.shutdown();
        boolean flag=executor.awaitTermination(1, TimeUnit.MINUTES);
        assertTrue("Executor should terminate within the given time", flag);
        assertTrue( "Balance should not be less than 0 after concurrent withdrawals",atmMachine.getBalance()>=0);
    }


}