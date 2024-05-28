import dto.Denomination;
import core.ATMMachine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ATMMachineTest {
    private ATMMachine atmMachine;

    @BeforeEach
    public void setUp (){
        atmMachine=new ATMMachine();
    }

    @Test
    public void testIntitalBalance(){
        assertEquals(8100,atmMachine.getBalance());
    }

    @Test
    public void testSuccessWithdrawal(){
        List<Denomination> denominationList=atmMachine.withdraw(1100);
        assertEquals(7000,atmMachine.getBalance());
        assertTrue(denominationList.contains(new Denomination(500,2)), "Denominations are incorrect");
        assertTrue(denominationList.contains(new Denomination(100,1)), "Denominations are incorrect");
    }

    @Test
    public void testInsufficientBalance(){
        Exception exception=assertThrows(RuntimeException.class,()->{
            atmMachine.withdraw(9000);
        });
        assertEquals("Insufficient balance, please try again",exception.getMessage());
    }

    @Test
    public void test_ConcurrentWithdrawal() throws InterruptedException, ExecutionException {
        int[] withdrawals={1600,2800,1500};
        ExecutorService executor= Executors.newFixedThreadPool(withdrawals.length);
        List<Callable<List<Denomination>>> callables=new ArrayList<>();
        for(int amount:withdrawals)
        {
            Callable<List<Denomination>> callable=()->atmMachine.withdraw(amount);
            callables.add(callable);
        }
        List<Future<List<Denomination>>> futures= executor.invokeAll(callables);
        for(Future<List<Denomination>> future:futures) {
             future.get();
        }
        executor.shutdown();
        boolean flag=executor.awaitTermination(1, TimeUnit.MINUTES);
        assertTrue(flag, "Executor should terminate within the given time");
        assertEquals(1,atmMachine.denominations.get(500));
        assertEquals(5,atmMachine.denominations.get(200));
        assertEquals(7,atmMachine.denominations.get(100));
        assertEquals( 2200,atmMachine.getBalance());
    }

    @Test
    public void testConcurrentWithdrawalExceedingBalance() throws InterruptedException{
        ExecutorService executor= Executors.newFixedThreadPool(2);
        Runnable r1=()->atmMachine.withdraw(8000);
        Runnable r2=()->atmMachine.withdraw(200);
        executor.submit(r1);
        executor.submit(r2);
        executor.shutdown();
        boolean flag=executor.awaitTermination(1, TimeUnit.MINUTES);
        assertTrue(flag, "Executor should terminate within the given time");
        assertTrue(atmMachine.getBalance()>=0, "Balance should not be less than 0 after concurrent withdrawals");
    }

}
